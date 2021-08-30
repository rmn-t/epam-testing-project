package com.epam.controller.test;

import com.epam.db.entities.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/timer"} )
public class TimerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("parameter1",250);
        Test test = new Test();
        test.setName("abdascdascdsad");
        req.setAttribute("test",test);
        req.getRequestDispatcher("/epam/timer.jsp").forward(req,resp);

    }
}
