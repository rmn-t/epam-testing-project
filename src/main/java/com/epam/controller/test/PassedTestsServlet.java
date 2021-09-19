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
 * Servlet is responsible for processing requests to get the list of passed tests by user.
 */
@WebServlet(urlPatterns = {"/passed/tests"})
public class PassedTestsServlet extends HttpServlet implements IPaginatable {
    private final Logger logger = LoggerFactory.getLogger(PassedTestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int recordsPerPage = calculateRecordsPerPageNum(req, "perPage");
            int recordsOffset = calculateRecordsOffset(req, recordsPerPage, "page");
            String sorting = getSortingValue(req, "sort", Consts.getVALID_COLUMNS_FOR_PASSED_TESTS_ORDER_BY(), Consts.PASSED_TESTS_DEFAULT_SORT);
            int userId = ((User) req.getSession().getAttribute(Consts.CURRENT_USER)).getId();
            int totalTests = Consts.PASSED_TESTS_DAO.getRecordsNumberByUserId(userId);
            int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);

            if ((recordsOffset < 0 || recordsOffset > totalTests) && recordsOffset != 1) {
                logger.error("User supplied invalid page number.");
                throw new ServletException("Couldn't obtain tests information.");
            }

            req.setAttribute("passedTests", Consts.PASSED_TESTS_DAO.getRecordsByUserIdOrderedLimited(userId, recordsOffset, recordsPerPage, sorting));
            req.setAttribute("lastPage", lastPage);
        } catch (DBException | NumberFormatException e) {
            logger.error("Couldn't obtain the list of finished tests.", e);
            throw new ServletException("Couldn't obtain the list of finished tests.");
        }
        req.getRequestDispatcher(Views.PASSED_TESTS_JSP).forward(req, resp);
    }

}
