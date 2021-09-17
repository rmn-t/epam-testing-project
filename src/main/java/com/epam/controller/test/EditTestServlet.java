package com.epam.controller.test;

import com.epam.controller.util.HTMLSanitizer;
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
 * Servlet is responsible for processing edit test requests and also create new tests requests.
 */
@WebServlet("/editTest")
public class EditTestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(EditTestServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int testId = Integer.parseInt(req.getParameter("id"));
            String name = HTMLSanitizer.cleanLtMt(req.getParameter("name"));
            int subjectId = Integer.parseInt(req.getParameter("subject"));
            int complexityId = Integer.parseInt(req.getParameter("complexity"));
            boolean isActive = Boolean.parseBoolean(req.getParameter("isActive"));
            int duration = Integer.parseInt(req.getParameter("durationMin")) * 60 + Integer.parseInt(req.getParameter("durationSec"));
            if (duration == 0) {
                duration = 60;
            }
            if (testId == 0) {
                testId = Consts.TEST_DAO.insertNewTest(name, subjectId, complexityId, duration);
            } else {
                Consts.TEST_DAO.updateTestById(testId, name, subjectId, complexityId, duration, isActive);
            }
            resp.sendRedirect("/epam/test?id=" + testId);
        } catch (DBException e) {
            logger.error("Couldn't update the test information.",e);
            throw new ServletException("Couldn't update the test information.");
        }

    }
}
