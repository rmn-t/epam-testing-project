package com.epam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet( urlPatterns = { "/locale/edit" } )
public class LocaleUpdateServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LocaleUpdateServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         *         String abc = req.getHeader("referer");
         *         String[] zz = abc.split("/epam/");
         *         logger.info(zz[1]);
         */
        logger.debug(req.getParameter("lang"));
        logger.debug(req.getPathInfo());
        Cookie langCookie = new Cookie("lang",req.getParameter("lang"));
        langCookie.setMaxAge(60*60*24);
        langCookie.setPath("/");
        resp.addCookie(langCookie);
        req.setAttribute("lang",req.getParameter("lang"));
//        resp.sendRedirect("/epam/login");
        resp.sendRedirect(req.getParameter("prevUrl"));
    }
}
