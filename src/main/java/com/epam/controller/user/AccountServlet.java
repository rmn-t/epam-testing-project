package com.epam.controller.user;

import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.util.Encrypt;
import com.epam.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@WebServlet(urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Views.ACCOUNT_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("currentUser");
        int salt = user.getSalt();
        String newPass = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        if (newPass != null && !user.getPassword().equals(newPass) && !("".equals(newPass))) {
            newPass = Encrypt.getSecurePassword(newPass, salt);
        } else {
            newPass = user.getPassword();
        }
        try {
            Consts.USER_DAO.updateUserById(user.getId(), newPass, user.getRoleId(), user.getStatusId(), firstName, lastName, salt);
            session.setAttribute("currentUser",Consts.USER_DAO.getUserDetailsByUserName(user.getUsername()));
        } catch (DBException e) {
            logger.error("Couldn't update the user information.", e);
        }
        resp.sendRedirect("account");
    }
}
