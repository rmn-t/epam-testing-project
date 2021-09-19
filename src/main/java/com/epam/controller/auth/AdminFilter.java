package com.epam.controller.auth;

import com.epam.controller.util.Routes;
import com.epam.db.model.User;
import com.epam.util.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter protects the routes which should only be accessible by the user with role "admin".
 * If user has other role their request will be processed by @see com.epam.controller.auth.LoginFilter.
 */
@WebFilter(filterName = "adminFilter")
public class AdminFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //init
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session == null) {
            chain.doFilter(req, resp);
        } else {
            User user = (User) session.getAttribute(Consts.CURRENT_USER);
            if ("admin".equals(user.getRole())) {
                chain.doFilter(req, resp);
            } else {
                resp.sendRedirect(Routes.HOME_TESTS);
            }
        }
    }

    @Override
    public void destroy() {
        //destroy
    }
}
