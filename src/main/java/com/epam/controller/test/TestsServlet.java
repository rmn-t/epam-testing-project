package com.epam.controller.test;

import com.epam.controller.util.IPaginatable;
import com.epam.db.model.Subject;
import com.epam.db.model.Test;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/tests","/en/tests"})
public class TestsServlet extends HttpServlet implements IPaginatable {
    private final Logger logger = LoggerFactory.getLogger(TestsServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sorting = req.getParameter("sort");
        resp.sendRedirect("tests?page=1&sort="+sorting);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordsPerPage = calculateRecordsPerPageNum(req,logger,"perPage");
        int pageId = calculatePage(req,recordsPerPage,"page");
        String sorting = getSortingValue(req,"sort",Consts.getVALID_COLUMNS_FOR_TEST_ORDER_BY(),Consts.TESTS_DEFAULT_SORT);

        List<Subject> subjects = new ArrayList<>();
        try {
            subjects = Consts.SUBJECT_DAO.getAllRecords();
            req.setAttribute("subjects",subjects);
        } catch (DBException e) {
            logger.error("Test servlet do get");
        }

        int subjectId = 0;
        if (req.getParameter("subject") != null) {
            try {
                double test = Double.parseDouble(req.getParameter("subject"));
                if (test >= 0 && test <= subjects.size()) {
                    subjectId = (int) test;
                }
            } catch (NumberFormatException e) {
                logger.info("Tried to supply not valid number format as subject id. ({})",req.getParameter("subject"),e);
            }
        }

        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(Consts.CURRENT_USER);
            String isActive = user.getRoleId() == 2 ? "active" : req.getParameter("testStatus");
            List<Test> tests = Consts.TEST_DAO.getTestsLimitedSorted(pageId,recordsPerPage,sorting,subjectId,isActive);
            int totalTests = Consts.TEST_DAO.getRecordsNumBySubjectId(subjectId,isActive);
            int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);
            req.setAttribute("tests",tests);
            req.setAttribute("lastPage",lastPage);
        } catch (DBException e) {
            logger.error("Test servlet do get");
        }
        req.getRequestDispatcher(Views.TESTS_JSP).forward(req,resp);
    }
}
