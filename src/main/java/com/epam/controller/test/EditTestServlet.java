package com.epam.controller.test;

import com.epam.db.dao.TestDao;
import com.epam.db.entities.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editTest")
public class EditTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        Test test = TestDao.getTestById(testId);
        req.setAttribute("test",test);
        req.getRequestDispatcher("/editTest.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String subject = req.getParameter("subject");
        String complexity = req.getParameter("complexity");
        int duration = Integer.parseInt(req.getParameter("duration"));
        TestDao.updateTestById(testId,name,subject,complexity,duration);
        resp.sendRedirect("/epam/test?id="+testId);
    }
}
