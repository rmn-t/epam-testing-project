package com.epam.controller.user;

import com.epam.db.dao.UserDao;
import com.epam.db.dao.sql.UserDaoSql;
import com.epam.exceptions.DBException;
import com.epam.util.Encrypt;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/edit/user"})
public class EditUsersServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(EditUsersServlet.class);
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoSql();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        int salt = Integer.parseInt(req.getParameter("salt"));
        String newPass = req.getParameter("newPass");
        String oldPass = req.getParameter("oldPass");
        if (newPass != null && !oldPass.equals(newPass) && !("".equals(newPass))) {
            newPass = Encrypt.getSecurePassword(newPass,salt);
        }
        int roleId = Integer.parseInt(req.getParameter("roleId"));
        int statusId = Integer.parseInt(req.getParameter("statusId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        try {
            userDao.updateUserById(userId, newPass, roleId, statusId,firstName,lastName,salt);
        } catch (DBException e) {
            logger.error("Edit user servlet post.",e);
        }
        resp.sendRedirect(req.getParameter("prevUrl"));
    }
}
