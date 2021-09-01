package com.epam.controller.user;

import com.epam.db.dao.UserDao;
import com.epam.db.entities.User;
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
    private static Logger logger = null;

    @Override
    public void init() throws ServletException {
        logger = LogManager.getLogger(LoginServlet.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.sendRedirect("login.jsp");
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    // 250 millisec

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("logging debug msg");
        logger.info("logging info msg");
        logger.warn("logging warn msg");
        logger.error("logging error msg");
        logger.fatal("logging fatal msg");
        String username = req.getParameter("username");
        User user = UserDao.getUserDetailsByUserName(username);
        if (!UserDao.validateCredentials(user,req.getParameter("password"),username)) {
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
