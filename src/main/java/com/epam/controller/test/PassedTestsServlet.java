package com.epam.controller.test;

import com.epam.exceptions.DBException;
import com.epam.db.dao.PassedTestsDao;
import com.epam.db.dao.sql.PassedTestsDaoSql;
import com.epam.db.model.PassedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/passed/tests"})
public class PassedTestsServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(PassedTestsServlet.class);
    private PassedTestsDao passedTestsDao;

    @Override
    public void init() throws ServletException {
        passedTestsDao = new PassedTestsDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageId = Integer.parseInt(req.getParameter("page"));
        int recordsPerPage = 10;
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }
        HttpSession session = req.getSession(false);
        int userId = Integer.parseInt("" + session.getAttribute("userId"));
        List<PassedTest> passedTests = null;
        try {
            passedTests = passedTestsDao.getPassedTestsByUserIdOrderedLimited(userId,pageId,recordsPerPage,session.getAttribute("passedTestsSorting").toString());
            int totalTests = passedTestsDao.getPassedTestsNumberByUserId(userId);
            int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);
            req.setAttribute("passedTests",passedTests);
            req.setAttribute("lastPage",lastPage);
        } catch (DBException e) {
            logger.error("Passed tests servlet get",e);
        }
        req.getRequestDispatcher("/passedTests.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("passedTests do post");
    }
}
