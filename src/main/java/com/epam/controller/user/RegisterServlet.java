package com.epam.controller.user;

import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.util.CookieUtil;
import com.epam.controller.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginStatus = CookieUtil.getCookieValueByName(req.getCookies(), "regStatus");
        req.setAttribute("regStatus", loginStatus);
        Cookie logStatus = new Cookie("regStatus", "userExists");
        logStatus.setMaxAge(0);
        resp.addCookie(logStatus);
        logger.info("RegisterServlet doGet()");
        req.getRequestDispatcher(Views.REGISTER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        try {
            if (Consts.USER_DAO.getUserDetailsByUserName(userName).getId() != 0) {
                Cookie regStatus = new Cookie("regStatus", "userExists");
                regStatus.setMaxAge(30);
                regStatus.setPath("register");
                resp.addCookie(regStatus);
                resp.sendRedirect("register");
                return;
            }
        } catch (DBException e) {
            logger.info("Username: '{} already exists.'", userName);
        }
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        try {
            Consts.USER_DAO.addNewUser(userName, password, firstName, lastName);
            logger.info(userName);
            HttpSession session = req.getSession();
            session.setAttribute(Consts.CURRENT_USER, Consts.USER_DAO.getUserDetailsByUserName(userName));
            logger.info(Consts.USER_DAO.getUserDetailsByUserName(userName).getStatus());
            logger.info("user created, redirecting to tests");
            resp.sendRedirect("tests?page=1");
        } catch (DBException e) {
            logger.error("Couldn't register user", e);
        }

    }
}
