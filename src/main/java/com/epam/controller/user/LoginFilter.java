package com.epam.controller.user;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Login filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String loginPage = request.getContextPath() + "/login";
        String loginJsp = request.getContextPath() + "/login.jsp";

        boolean loggedIn = session != null && session.getAttribute("username") != null;
        boolean loginRequest = request.getRequestURI().equals(loginPage);
        boolean loginRequest2 = request.getRequestURI().equals(loginJsp);

        if (loggedIn || loginRequest || loginRequest2) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(loginPage);
        }
    }

    @Override
    public void destroy() {
        System.out.println("Login filter destroy");
    }
}
