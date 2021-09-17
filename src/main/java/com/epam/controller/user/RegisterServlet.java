package com.epam.controller.user;

import com.epam.controller.util.HTMLSanitizer;
import com.epam.controller.util.Routes;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.controller.util.CookieUtil;
import com.epam.controller.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet is responsible for processing all incoming registration requests. If user is successfully registered they
 * are transferred to home page, otherwise they will be redirected back to register page with a custom generated message added.
 * PRG mechanism is implemented. Cookies are used purely for transferring a one time message from server to end user.
 */
@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);
    private final String REG_STATUS = "regStatus";
    private final String USER_EXISTS = "userExists";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginStatus = CookieUtil.getCookieValueByName(req.getCookies(), REG_STATUS);
        req.setAttribute(REG_STATUS, loginStatus);
        Cookie logStatus = new Cookie(REG_STATUS, USER_EXISTS);
        logStatus.setMaxAge(0);
        resp.addCookie(logStatus);
        req.getRequestDispatcher(Views.REGISTER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        try {
            if (Consts.USER_DAO.getUserDetailsByUserName(userName).getId() != 0) {
                Cookie userExistsCookie = new Cookie(REG_STATUS, USER_EXISTS);
                userExistsCookie.setMaxAge(30);
                userExistsCookie.setPath(Routes.REGISTER);
                resp.addCookie(userExistsCookie);
                resp.sendRedirect(Routes.REGISTER);
                return;
            }
            String password = HTMLSanitizer.cleanLtMt(req.getParameter("password"));
            String firstName = HTMLSanitizer.cleanLtMt(req.getParameter("firstName"));
            String lastName = HTMLSanitizer.cleanLtMt(req.getParameter("lastName"));
            Consts.USER_DAO.addNewUser(userName, password, firstName, lastName);
            HttpSession session = req.getSession();
            session.setAttribute(Consts.CURRENT_USER, Consts.USER_DAO.getUserDetailsByUserName(userName));
            resp.sendRedirect(Routes.HOME_TESTS);
        } catch (DBException e) {
            logger.error("Couldn't register new user, by username: '{}.'", userName, e);
            throw new ServletException("Couldn't register new user.");
        }
    }
}
