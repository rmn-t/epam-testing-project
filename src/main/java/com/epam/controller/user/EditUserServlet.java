package com.epam.controller.user;

import com.epam.exceptions.DBException;
import com.epam.db.dao.UserDao;
import com.epam.db.dao.sql.UserDaoSql;
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
public class EditUserServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(EditUserServlet.class);
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoSql();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        int page = Integer.parseInt(req.getParameter("page"));
        String sort = req.getParameter("sort");
        int salt = Integer.parseInt(req.getParameter("salt"));
        String newPass = req.getParameter("newPass");
        String oldPass = req.getParameter("oldPass");
        if (newPass != null && !oldPass.equals(newPass) && !("".equals(newPass))) {
            newPass = Encrypt.getSecurePassword(newPass,salt);
        }
        String role = req.getParameter("role");
        String status = req.getParameter("status");
        try {
            userDao.updateUserById(userId, newPass, role, status,salt);
        } catch (DBException e) {
            logger.error("Edit user servlet post.",e);
        }
        resp.sendRedirect("/epam/users?page=" + page + "&sort=" + sort);
    }
}
