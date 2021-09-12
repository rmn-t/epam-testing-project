package com.epam.controller.test;

import com.epam.controller.util.IPaginatable;
import com.epam.db.model.PassedTest;
import com.epam.db.model.User;
import com.epam.exceptions.DBException;
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

@WebServlet(urlPatterns = {"/passed/tests"})
public class PassedTestsServlet extends HttpServlet implements IPaginatable {
    private final Logger logger = LoggerFactory.getLogger(PassedTestsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordsPerPage = calculateRecordsPerPageNum(req,logger,"perPage");
        int pageNum = calculatePage(req,recordsPerPage,"page");
        String sorting = getSortingValue(req,"sort",Consts.getVALID_COLUMNS_FOR_PASSED_TESTS_ORDER_BY(),Consts.PASSED_TESTS_DEFAULT_SORT);
        User user = (User) req.getSession().getAttribute(Consts.CURRENT_USER);
        int userId = user.getId();
        try {
            List<PassedTest> passedTests = Consts.PASSED_TESTS_DAO.getRecordsByUserIdOrderedLimited(userId, pageNum, recordsPerPage, sorting);
            int totalTests = Consts.PASSED_TESTS_DAO.getRecordsNumberByUserId(userId);
            int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);
            req.setAttribute("passedTests", passedTests);
            req.setAttribute("lastPage", lastPage);
        } catch (DBException e) {
            logger.error("Passed tests servlet get", e);
        }
        req.getRequestDispatcher("/" + Views.PASSED_TESTS_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("passedTests do post");
    }
}
