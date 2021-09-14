package com.epam.controller.auth;

import com.epam.db.model.User;
import com.epam.util.Consts;
import com.epam.controller.util.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter processes requests for the entire app.
 * If the user is banned they get logged out of the system.
 * If the user tries to access registration, login, or change language the request is transferred to the required resource.
 * If the logged in user tries to access resource which requires to be logged in - they are successfully transferred to it,
 * otherwise they are transferred to login page.
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //init
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String reqPath = req.getServletPath();
        if (session != null && session.getAttribute(Consts.CURRENT_USER) != null) {
            if (((User) session.getAttribute(Consts.CURRENT_USER)).getStatus().equals("Banned")) {
                req.getRequestDispatcher(Routes.LOGOUT).forward(req, resp);
            } else {
                filterChain.doFilter(req, resp);
            }
        } else if (Routes.SLASH_LOGIN.equals(reqPath) || Routes.SLASH_LOCALE_EDIT.equals(reqPath) || Routes.SLASH_REGISTER.equals(reqPath)) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(Routes.LOGIN);
        }
    }

    @Override
    public void destroy() {
        //destroy
    }
}
