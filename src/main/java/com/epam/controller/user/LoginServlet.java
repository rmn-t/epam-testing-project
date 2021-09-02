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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(LoginServlet.class);
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.sendRedirect("login.jsp");
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    // 250 millisec

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        User user = null;
        try {
            user = userDao.getUserDetailsByUserName(username);
        } catch (DBException e) {
            logger.error("Login servlet post.",e);
        }
        if (!userDao.validateCredentials(user,req.getParameter("password"),username)) {
            req.setAttribute("loginStatus","Incorrect credentials");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        } else if (user.getStatus().equals("banned")) {
            req.setAttribute("loginStatus","User was banned");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("username",user.getUsername());
            session.setAttribute("userId",user.getId());
            session.setAttribute("userStatus",user.getStatus());
            session.setAttribute("testsSorting","name ASC");
            session.setAttribute("passedTestsSorting","date DESC");
            resp.sendRedirect("tests?page=1");
        }
    }
}
