package com.epam.controller.user;

import com.epam.controller.util.HTMLSanitizer;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.util.Encrypt;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet is responsible for processing incoming requests for editing user details, which come from "users" page of the application.
 * Only admin is allowed to change info for all users. Get method is not implemented.
 */
@WebServlet(urlPatterns = {"/edit/user"})
public class EditUsersServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EditUsersServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            int salt = Integer.parseInt(req.getParameter("salt"));
            String newPass = HTMLSanitizer.cleanLtMt(req.getParameter("newPass"));
            String oldPass = req.getParameter("oldPass");
            if (!oldPass.equals(newPass) && !("".equals(newPass))) {
                newPass = Encrypt.getSecurePassword(newPass,salt);
            } else {
                newPass = oldPass;
            }
            int roleId = Integer.parseInt(req.getParameter("roleId"));
            int statusId = Integer.parseInt(req.getParameter("statusId"));
            String firstName = HTMLSanitizer.cleanLtMt(req.getParameter("firstName"));
            String lastName = HTMLSanitizer.cleanLtMt(req.getParameter("lastName"));
            Consts.USER_DAO.updateUserById(userId, newPass, roleId, statusId,firstName,lastName,salt);
        } catch (DBException e) {
            logger.error("Couldn't update user information.",e);
            throw new ServletException("Couldn't update user information.");
        }
        resp.sendRedirect(req.getParameter("prevUrl"));
    }
}
