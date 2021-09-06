package com.epam.controller.test;

import com.epam.db.DBException;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Test;
import com.epam.util.Consts;
import com.epam.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/tests","/en/tests"})
public class TestsServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(TestsServlet.class);
    private TestDao testDao;

    @Override
    public void init() throws ServletException {
        testDao = new TestDaoSql();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sorting = req.getParameter("sort");
        resp.sendRedirect("tests?page=1&sort="+sorting);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageId = 1;
        if (req.getParameter("page") != null) {
            pageId = Integer.parseInt(req.getParameter("page"));
        }
        int recordsPerPage = 10;
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }

        String sorting = req.getParameter("sort");
        logger.info("____ {} ____",sorting);
        if (sorting == null || !Consts.getVALID_COLUMNS_FOR_TEST_ORDER_BY().contains(sorting)) {
            sorting = "name ASC";
        }

        List<Test> tests = null;
        try {
            tests = testDao.getTestsLimitedSorted(pageId,recordsPerPage,sorting);
            int totalTests = testDao.getTestsNumber();
            int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);
            req.setAttribute("tests",tests);
            req.setAttribute("lastPage",lastPage);
        } catch (DBException e) {
            logger.error("Test servlet do get");
        }
        req.getRequestDispatcher(Views.TESTS_JSP).forward(req,resp);
    }
}
