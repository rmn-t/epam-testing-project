package com.epam.controller.test;

import com.epam.db.DBException;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editTest")
public class EditTestServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(EditTestServlet.class);
    private TestDao testDao;

    @Override
    public void init() throws ServletException {
        testDao = new TestDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        Test test = null;
        try {
            test = testDao.getTestById(testId);
        } catch (DBException e) {
            logger.error("Edit servlet get");
        }
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
        try {
            testDao.updateTestById(testId,name,subject,complexity,duration);
        } catch (DBException e) {
            logger.error("Edit servlet post");
        }
        resp.sendRedirect("/epam/test?id="+testId);
    }
}
