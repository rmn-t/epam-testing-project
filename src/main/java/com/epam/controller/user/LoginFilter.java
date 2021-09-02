package com.epam.controller.user;

import com.epam.db.dao.sql.UserDaoSql;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    private Logger logger = LogManager.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //init
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String loginPage = req.getContextPath() + "/login";
        String loginJsp = req.getContextPath() + "/login.jsp";

        logger.info("logger, url : " + req.getServletPath() + "| query : " + req.getQueryString() + " | method : " + req.getMethod());
        boolean loggedIn = session != null && session.getAttribute("username") != null;
        boolean loginRequest = req.getRequestURI().equals(loginPage);
        boolean loginRequest2 = req.getRequestURI().equals(loginJsp);
        
        if (loggedIn){
            if (UserDaoSql.getUserDetailsByUserName("" + session.getAttribute("username")).getStatus().equals("banned")) {
                req.setAttribute("loginStatus","User is banned (filter proc)");
                req.getRequestDispatcher("/logout").forward(req,resp);
            } else {
                filterChain.doFilter(req, resp);
            }
        } else if (loginRequest || loginRequest2) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(loginPage);
        }
    }

    @Override
    public void destroy() {
        //destroy
    }
}