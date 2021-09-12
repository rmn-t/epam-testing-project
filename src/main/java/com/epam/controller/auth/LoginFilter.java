package com.epam.controller.auth;

import com.epam.db.model.User;
import com.epam.util.Consts;
import com.epam.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //init
    }

    /**
     * add case insensitive check for username
     *
     * //        if (req.getCookies() == null) {
     * //            Cookie langCookie = new Cookie("lang","en");
     * //            langCookie.setMaxAge(60*60*24);
     * //            langCookie.setPath("/");
     * //            resp.addCookie(langCookie);
     * //        }
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("filter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String reqUri = req.getRequestURI();
        if (session != null && (User)session.getAttribute(Consts.CURRENT_USER) != null){
            if (((User) session.getAttribute(Consts.CURRENT_USER)).getStatus().equals("Banned")) {
                req.setAttribute("loginStatus","User is banned (filter proc)");
                req.getRequestDispatcher("/logout").forward(req,resp);
            } else {
                filterChain.doFilter(req, resp);
            }
        } else if (reqUri.equals("/epam/login") || reqUri.equals("/epam/locale/edit") || reqUri.equals("/epam/" + Views.REGISTER_JSP) || reqUri.equals("/epam/register")) {
            logger.debug(reqUri);
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/epam/login");
        }
    }

    @Override
    public void destroy() {
        //destroy
    }
}
