package com.epam.controller.test;

import com.epam.controller.util.CookieUtil;
import com.epam.controller.util.Views;
import com.epam.db.model.Complexity;
import com.epam.db.model.Question;
import com.epam.db.model.Subject;
import com.epam.db.model.Test;
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
import java.util.List;

/**
 * Servlet is responsible for rendering test information, it's questions and answers.
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String lang = CookieUtil.getCookieValueByName(req.getCookies(), "lang", "en");
            List<Subject> subjects = Consts.SUBJECT_DAO.getAllRecords(lang);
            req.setAttribute("subjects", subjects);
            List<Complexity> complexities = Consts.COMPLEXITY_DAO.getAllRecords();
            req.setAttribute("complexities", complexities);

            int testId = Integer.parseInt(req.getParameter("id"));
            Test test = Consts.TEST_DAO.getTestById(testId,lang);
            List<Question> questions = Consts.QUESTION_DAO.getQuestionsAndAnswersByTestId(testId);

            req.setAttribute("questions", questions);
            req.setAttribute("test", test);
        } catch (DBException | NumberFormatException e) {
            logger.error("Couldn't obtain the information for provided test id.", e);
            throw new ServletException("Couldn't obtain the information for provided test id.");
        }
        req.getRequestDispatcher(Views.VIEW_TEST_JSP).forward(req, resp);
    }
}
