package com.epam.controller.auth;

import com.epam.controller.util.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Processes all log out requests, before invaliding the session all session variables are destroyed.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        removeAllSessionAttributes(req, session);
        session.invalidate();
        resp.sendRedirect(Routes.LOGOUT);
    }

    /**
     * Method is used in order to destroy all session attributes before invaliding it, in order to prevent cases
     * of variables still being accessible due to being referenced in other code.
     *
     * @param req     Current servlet request
     * @param session Current session
     */
    public void removeAllSessionAttributes(HttpServletRequest req, HttpSession session) {
        Enumeration<String> attributes = req.getSession().getAttributeNames();
        if (attributes == null) {
            return;
        }
        while (attributes.hasMoreElements()) {
            session.removeAttribute(attributes.nextElement());
        }
    }

}
