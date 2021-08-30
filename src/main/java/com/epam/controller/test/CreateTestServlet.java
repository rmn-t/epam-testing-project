package com.epam.controller.test;

import com.epam.db.dao.TestDao;

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
        String name = req.getParameter("testName");
        String subject = req.getParameter("subject");
        String complexity = req.getParameter("complexity");
        int duration = Integer.parseInt(req.getParameter("duration"));
        int id = TestDao.insertNewTest(name,subject,complexity,duration);
        resp.sendRedirect("/epam/test?id="+id);
    }
}
