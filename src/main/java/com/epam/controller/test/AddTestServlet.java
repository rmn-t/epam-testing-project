package com.epam.controller.test;

import com.epam.db.dao.ComplexityDao;
import com.epam.db.dao.sql.ComplexityDaoSql;
import com.epam.db.model.Complexity;
import com.epam.exceptions.DBException;
import com.epam.db.dao.SubjectDao;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.SubjectDaoSql;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Subject;
import com.epam.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createTest")
public class AddTestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        List<Subject> subjects = new ArrayList<>();
        List<Complexity> complexities = new ArrayList<>();
        try {
            subjects = subjectDao.getAllRecords();
            req.setAttribute("subjects",subjects);
            complexities = complexityDao.getAllRecords();
            req.setAttribute("complexities",complexities);
        } catch (DBException e) {
            logger.error("Couldn't find options for test creation. Subjects {}. Complexities {}.",subjects.size(),complexities.size(),e);
        }
        req.getRequestDispatcher(Views.CREATE_TEST_JSP).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("testName");
        int subjectId = Integer.parseInt(req.getParameter("subject"));
        int complexityId = Integer.parseInt(req.getParameter("complexity"));
        int duration = Integer.parseInt(req.getParameter("duration"));
        int id = 0;
        try {
            id = testDao.insertNewTest(name,subjectId,complexityId,duration);
        } catch (DBException e) {
            logger.error("Create test servlet post",e);
        }
        resp.sendRedirect("/epam/test?id="+id);
    }
}
