package com.epam.controller.user;

import com.epam.db.DBException;
import com.epam.db.dao.UserDao;
import com.epam.db.dao.sql.UserDaoSql;
import com.epam.db.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    private static Logger logger;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        logger = LogManager.getLogger(UsersServlet.class);
        userDao = new UserDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageId = Integer.parseInt(req.getParameter("page"));
        String sorting = req.getParameter("sort");
        int recordsPerPage = 150;
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }
        List<User> users = null;
        try {
            users = userDao.getAllUsersLimitedSorted(pageId,recordsPerPage,sorting);
            int usersNum = userDao.getNumOfUsers();
            int lastPage = usersNum / recordsPerPage + ((usersNum % recordsPerPage == 0) ? 0 : 1);
            req.setAttribute("users",users);
            req.setAttribute("lastPage",lastPage);
        } catch (DBException e) {
            logger.error("Users servlet get.",e);
        }
        req.getRequestDispatcher("/users.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sort");
        resp.sendRedirect("/epam/users?page=1&sort=" + sort);
    }
}
