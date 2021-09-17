package com.epam.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet is responsible for processing requests to change locale. Locale is stored in cookies.
 */
@WebServlet(urlPatterns = {"/locale/edit"})
public class LocaleUpdateServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LocaleUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie langCookie = new Cookie("lang", req.getParameter("lang"));
        langCookie.setMaxAge(60 * 60 * 24);
        langCookie.setPath("/");
        resp.addCookie(langCookie);
        resp.sendRedirect(req.getParameter("prevUrl"));
    }
}
