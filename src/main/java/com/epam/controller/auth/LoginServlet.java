package com.epam.controller.auth;

import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.util.CookieUtil;
import com.epam.util.Views;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginStatus = CookieUtil.getCookieValueByName(req.getCookies(), "loginStatus");
        req.setAttribute("loginStatus", loginStatus);
        Cookie logStatus = new Cookie("loginStatus", "incorrectCredentials");
        logStatus.setMaxAge(0);
        resp.addCookie(logStatus);
        if (req.getSession(false) != null) {
            req.getSession().invalidate();
        }
        req.getRequestDispatcher("/" + Views.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        User user = null;
        try {
            user = Consts.USER_DAO.getUserDetailsByUserName(username);
        } catch (DBException e) {
            logger.error("Login servlet post.", e);
        }
        Cookie logStatus = new Cookie("loginStatus", "incorrectCredentials");
        logStatus.setMaxAge(30);
        logStatus.setPath("login");
        if (user == null || !Consts.USER_DAO.validateCredentials(user, req.getParameter("password"), username)) {
            req.setAttribute("loginStatus", "incorrectCredentials");
            req.getRequestDispatcher(Views.LOGIN_JSP).include(req, resp);
            resp.addCookie(logStatus);
            resp.sendRedirect(Views.LOGIN_JSP);
        } else if ("Banned".equals(user.getStatus())) {
            req.setAttribute("loginStatus", "userWasBanned");
            logStatus.setValue("userWasBanned");
            resp.addCookie(logStatus);
            resp.sendRedirect(Views.LOGIN_JSP);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute(Consts.CURRENT_USER, user);
            resp.sendRedirect("tests?page=1&sort=name ASC&subject=0&perPage=10");
        }
    }

}
