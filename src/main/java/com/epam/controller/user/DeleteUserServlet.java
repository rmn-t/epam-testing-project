package com.epam.controller.user;

import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet processes all incoming requests for deleting a specific user, from the "users" page of the application.
 * Only admin is allowed to delete users. Get method is not implemented.
 */
@WebServlet(urlPatterns = {"/delete/user"})
public class DeleteUserServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeleteUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("userId"));
        try {
            Consts.USER_DAO.deleteUserById(id);
        } catch (DBException e) {
            logger.error("Couldn't delete the user by id {}.",id);
            throw new ServletException("Couldn't delete the user.");
        }
        resp.sendRedirect(req.getParameter("prevUrl"));
    }
}
