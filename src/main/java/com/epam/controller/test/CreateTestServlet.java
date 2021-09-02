package com.epam.controller.test;

import com.epam.db.DBException;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.TestDaoSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createTest")
public class CreateTestServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(CreateTestServlet.class);
    private TestDao testDao;

    @Override
    public void init() throws ServletException {
        testDao = new TestDaoSql();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("testName");
        String subject = req.getParameter("subject");
        String complexity = req.getParameter("complexity");
        int duration = Integer.parseInt(req.getParameter("duration"));
        int id = 0;
        try {
            id = testDao.insertNewTest(name,subject,complexity,duration);
        } catch (DBException e) {
            logger.error("Create test servlet post",e);
        }
        resp.sendRedirect("/epam/test?id="+id);
    }
}
