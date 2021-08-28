package com.epam.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createTest")
public class CreateTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("testName"));
        System.out.println(req.getParameter("subject"));
        System.out.println(req.getParameter("complexity"));
        resp.sendRedirect("/epam/createTest.html");
    }
}
