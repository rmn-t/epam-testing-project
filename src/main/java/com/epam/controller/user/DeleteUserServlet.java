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

@WebServlet(urlPatterns = {"/delete/user"})
public class DeleteUserServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeleteUserServlet.class);

    /**
     * redirect if no url supplied or what to do if no id
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("userId"));
        try {
            Consts.USER_DAO.deleteUserById(id);
        } catch (DBException e) {
            logger.error("Couldn't delete the user by id {}.",id);
        }
        resp.sendRedirect(req.getParameter("prevUrl"));
    }
}
