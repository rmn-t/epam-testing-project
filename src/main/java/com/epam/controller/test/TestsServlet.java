package com.epam.controller.test;

import com.epam.controller.util.IPaginatable;
import com.epam.controller.util.Views;
import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet responsible the info for the tests and processing sort,filtering,pagination requests with various params.
 */
@WebServlet(urlPatterns = {"/tests"})
public class TestsServlet extends HttpServlet implements IPaginatable {
    private final Logger logger = LoggerFactory.getLogger(TestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int recordsPerPage = calculateRecordsPerPageNum(req, "perPage");
            int recordsOffset = calculateRecordsOffset(req, recordsPerPage, "page");
            String sorting = getSortingValue(req, "sort", Consts.getVALID_COLUMNS_FOR_TEST_ORDER_BY(), Consts.TESTS_DEFAULT_SORT);
            int subjectId = Integer.parseInt(req.getParameter("subject"));
            User user = (User) req.getSession().getAttribute(Consts.CURRENT_USER);
            String isActive = user.getRoleId() == 2 ? "active" : req.getParameter("testStatus");
            int totalTests = Consts.TEST_DAO.getRecordsNumBySubjectId(subjectId, isActive);

            req.setAttribute("tests", Consts.TEST_DAO.getTestsLimitedSorted(recordsOffset, recordsPerPage, sorting, subjectId, isActive));
            req.setAttribute("lastPage", totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1));
            req.setAttribute("subjects", Consts.SUBJECT_DAO.getAllRecords());
        } catch (DBException | NumberFormatException e) {
            logger.error("Couldn't obtain the list of tests.", e);
            throw new ServletException("Couldn't obtain the list of tests.");
        }
        req.getRequestDispatcher(Views.TESTS_JSP).forward(req, resp);
    }
}
