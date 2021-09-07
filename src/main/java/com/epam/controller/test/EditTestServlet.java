package com.epam.controller.test;

import com.epam.db.dao.ComplexityDao;
import com.epam.db.dao.SubjectDao;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.ComplexityDaoSql;
import com.epam.db.dao.sql.SubjectDaoSql;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Test;
import com.epam.exceptions.DBException;
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
    private SubjectDao subjectDao;
    private ComplexityDao complexityDao;

    @Override
    public void init() throws ServletException {
        testDao = new TestDaoSql();
        subjectDao = new SubjectDaoSql();
        complexityDao = new ComplexityDaoSql();
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
//        req.getRequestDispatcher(Views.EDIT_TEST_JSP).forward(req,resp);
        logger.info("doGet()");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int subjectId = Integer.parseInt(req.getParameter("subject"));
        int complexityId = Integer.parseInt(req.getParameter("complexity"));
        int duration = Integer.parseInt(req.getParameter("durationMin")) * 60 + Integer.parseInt(req.getParameter("durationSec"));
        try {
            testDao.updateTestById(testId,name,subjectId,complexityId,duration);
        } catch (DBException e) {
            logger.error("Edit servlet post");
        }
        resp.sendRedirect("/epam/test?id="+testId);
    }
}
