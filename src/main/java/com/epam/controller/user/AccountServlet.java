package com.epam.controller.user;

import com.epam.controller.util.CookieUtil;
import com.epam.controller.util.HTMLSanitizer;
import com.epam.controller.util.Views;
import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet is responsible for rendering user account (personal info) and processing incoming requests for changing account info.
 * PRG mechanism is implemented for submitting info.
 * Cookie is used purely for transferring a one time message to user.
 */
@WebServlet(urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(AccountServlet.class);
    private final String UPDATE_STATUS = "updStatus";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(UPDATE_STATUS, CookieUtil.getCookieValueByName(req.getCookies(), UPDATE_STATUS));
        Cookie updStatusCookie = new Cookie(UPDATE_STATUS, "");
        updStatusCookie.setMaxAge(0);
        resp.addCookie(updStatusCookie);
        req.getRequestDispatcher(Views.ACCOUNT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute(Consts.CURRENT_USER);
            int salt = user.getSalt();
            String newPass = HTMLSanitizer.cleanLtMt(req.getParameter("password"));
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            if (!user.getPassword().equals(newPass) && !("".equals(newPass))) {
                newPass = Encrypt.getSecurePassword(newPass, salt);
            } else {
                newPass = user.getPassword();
            }

            Consts.USER_DAO.updateUserById(user.getId(), newPass, user.getRoleId(), user.getStatusId(), firstName, lastName, salt);
            session.setAttribute(Consts.CURRENT_USER,Consts.USER_DAO.getUserDetailsByUserName(user.getUsername()));

        } catch (DBException e) {
            logger.error("Couldn't update user information.", e);
            throw new ServletException("Couldn't update user information.");
        }
        Cookie updStatusCookie = new Cookie(UPDATE_STATUS, "successfullyUpdated");
        updStatusCookie.setMaxAge(30);
        updStatusCookie.setPath("account");
        resp.addCookie(updStatusCookie);
        resp.sendRedirect("account");
    }
}
