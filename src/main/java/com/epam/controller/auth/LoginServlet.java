package com.epam.controller.auth;

import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.controller.util.CookieUtil;
import com.epam.controller.util.Routes;
import com.epam.controller.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet that is responsible for processing incoming login requests.
 * Upon receiving post request - entered information is processed:
 * 1) if the user wasn't found or incorrect credentials were provided - they will be redirected back to login page
 * 2) if correct credentials were provided but user was banned - they will be redirected back to login page
 * 3) if correct credentials were provided - they will be redirected to the home page which is dashboard with tests
 * PRG mechanism is implemented.
 * Cookies are used only to transfer the message about login failure, they are immediately destroyed, upon receiving
 * GET request since we only want to show the message once.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private final String LOGIN_STATUS = "loginStatus";
    private final String INCORRECT_CREDENTIALS = "incorrectCredentials";
    private final String USER_WAS_BANNED = "userWasBanned";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginStatus = CookieUtil.getCookieValueByName(req.getCookies(), LOGIN_STATUS,"");
        req.setAttribute(LOGIN_STATUS, loginStatus);
        Cookie logStatus = new Cookie(LOGIN_STATUS, INCORRECT_CREDENTIALS);
        logStatus.setMaxAge(0);
        resp.addCookie(logStatus);
        if (req.getSession(false) != null) {
            req.getSession().invalidate();
        }
        req.getRequestDispatcher(Views.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        User user = null;
        try {
            user = Consts.USER_DAO.getUserDetailsByUserName(username);
        } catch (DBException e) {
            logger.error("Failed to obtain user details by username: {}.", username, e);
        }
        Cookie logStatus = new Cookie(LOGIN_STATUS, INCORRECT_CREDENTIALS);
        logStatus.setMaxAge(30);
        logStatus.setPath("login");
        if (user == null || !Consts.USER_DAO.validateCredentials(user, req.getParameter("password"), username)) {
            resp.addCookie(logStatus);
            resp.sendRedirect(Routes.LOGIN);
        } else if ("Banned".equals(user.getStatus())) {
            logStatus.setValue(USER_WAS_BANNED);
            resp.addCookie(logStatus);
            resp.sendRedirect(Routes.LOGIN);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute(Consts.CURRENT_USER, user);
            resp.sendRedirect(Routes.HOME_TESTS);
        }
    }

}
