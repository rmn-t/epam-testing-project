package com.epam.controller.user;

import com.epam.db.DBException;
import com.epam.db.dao.UserDao;
import com.epam.db.dao.sql.UserDaoSql;
import com.epam.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = new UserDaoSql();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        if (req.getCookies() == null) {
            Cookie langCookie = new Cookie("lang","en");
            langCookie.setMaxAge(60*60*24);
            langCookie.setPath("/");
            resp.addCookie(langCookie);
        }
        logger.debug("logger, url : " + req.getServletPath() + "| query : " + req.getQueryString() + " | method : " + req.getMethod());
        logger.debug(req.getRequestURI());
        if (session != null && session.getAttribute("username") != null){
            try {
                if (userDao.getUserDetailsByUserName("" + session.getAttribute("username")).getStatus().equals("banned")) {
                    req.setAttribute("loginStatus","User is banned (filter proc)");
                    req.getRequestDispatcher("/logout").forward(req,resp);
                } else {
                    filterChain.doFilter(req, resp);
                }
            } catch (DBException e) {
                logger.error("Login filter.",e);
            }
        } else if (req.getRequestURI().equals("/epam/login") || req.getRequestURI().equals("/epam/locale/edit") || req.getRequestURI().equals("/epam/" + Views.REGISTER_JSP) || req.getRequestURI().equals("/epam/register")) {
            logger.debug(req.getRequestURI());
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("/epam/login");
//                    resp.sendRedirect("views/users/login.jsp");
//            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    public void destroy() {
        //destroy
    }
}
