package com.epam.controller.user;

import com.epam.controller.CookieUtil;
import com.epam.exceptions.DBException;
import com.epam.db.dao.UserDao;
import com.epam.db.dao.sql.UserDaoSql;
import com.epam.db.model.User;
import com.epam.util.Views;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
//        Cookie cookie = req.getCookies();]
        String loginStatus = CookieUtil.getCookieValueByName(req.getCookies(),"loginStatus");
        req.setAttribute("loginStatus",loginStatus);
        Cookie logStatus = new Cookie("loginStatus","incorrectCredentials");
        logStatus.setMaxAge(0);
        resp.addCookie(logStatus);
        logger.info("doGet()");
        if (req.getSession(false) != null) {
//            req.getRequestDispatcher("logout").forward(req,resp);
            req.getSession().invalidate();
        }
        req.getRequestDispatcher("/views/users/login.jsp").forward(req,resp);

//        resp.sendRedirect("views/users/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPos()");
        String username = req.getParameter("username");
        User user = null;
        try {
            user = userDao.getUserDetailsByUserName(username);
        } catch (DBException e) {
            logger.error("Login servlet post.",e);
        }

        Cookie logStatus = new Cookie("loginStatus","incorrectCredentials");
        logStatus.setMaxAge(30);
        logStatus.setPath("login");
        if (user == null || !userDao.validateCredentials(user,req.getParameter("password"),username)) {
            req.setAttribute("loginStatus","incorrectCredentials");
            req.getRequestDispatcher(Views.LOGIN_JSP).include(req,resp);
//            req.getRequestDispatcher("login").include(req,resp);
//            resp.sendRedirect("login");
            resp.addCookie(logStatus);
            resp.sendRedirect(Views.LOGIN_JSP);
        } else if ("banned".equals(user.getStatus())) {
            req.setAttribute("loginStatus","userWasBanned");
//            resp.sendRedirect("login");
//            req.getRequestDispatcher(Views.LOGIN_JSP).include(req,resp);
            logStatus.setValue("userWasBanned");
            resp.addCookie(logStatus);
            resp.sendRedirect(Views.LOGIN_JSP);
//            req.getRequestDispatcher("login").include(req,resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("username",user.getUsername());
            session.setAttribute("userId",user.getId());
            session.setAttribute("userStatus",user.getStatus());
            session.setAttribute("userRole",user.getRole());
            session.setAttribute("passedTestsSorting","date DESC");
            resp.sendRedirect("tests?page=1&sort=name ASC&subject=0");
        }
    }
}
