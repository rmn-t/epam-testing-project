package com.epam.controller.test;

import com.epam.controller.util.CookieUtil;
import com.epam.controller.util.Routes;
import com.epam.controller.util.Views;
import com.epam.db.model.Question;
import com.epam.db.model.Test;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet is responsible for rendering test form upon user starting test and also processing test results upon user
 * finishing the test.
 */
@WebServlet(urlPatterns = {"/take/test"})
public class TakeTestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TakeTestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String lang = CookieUtil.getCookieValueByName(req.getCookies(), "lang", "en");
            int testId = Integer.parseInt(req.getParameter("id"));
            List<Question> questions = Consts.QUESTION_DAO.getQuestionsAndAnswersByTestId(testId);
            Test test = Consts.TEST_DAO.getTestById(testId, lang);
            if (!test.getIsActive() || test.getQuestionsNum() == 0) {
                req.setAttribute("error", "You are not allowed to pass this test.");
                req.getRequestDispatcher("/error").forward(req, resp);
                return;
            }
            req.setAttribute("questions", questions);
            req.setAttribute("test", test);
            HttpSession session = req.getSession(false);
            Long startTime = (Long) session.getAttribute("startTime" + testId);
            if (startTime == null) {
                startTime = System.currentTimeMillis() / 1000L;
                session.setAttribute("startTime" + testId, startTime);
            }
            long currentTime = System.currentTimeMillis() / 1000L;
            session.setAttribute("timeLeft" + testId, test.getDuration() - (currentTime - startTime));
            req.getRequestDispatcher(Views.TAKE_TEST_JSP).forward(req, resp);
        } catch (DBException e) {
            logger.error("An error occurred while trying to start the test.", e);
            throw new ServletException("An error occurred while trying to start the test.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String lang = CookieUtil.getCookieValueByName(req.getCookies(), "lang", "en");
            int testId = Integer.parseInt(req.getParameter("id"));
            int testDuration = Consts.TEST_DAO.getTestById(testId, lang).getDuration();
            List<Question> questions = Consts.QUESTION_DAO.getQuestionsAndAnswersByTestId(testId);
            double questionsNum = questions.size();

            int correctAnswers = 0;
            for (Question q : questions) {
                if (q.validateAnswers(req.getParameterValues("" + q.getId()))) {
                    correctAnswers++;
                }
            }

            HttpSession session = req.getSession(false);
            Long startTime = (Long) session.getAttribute("startTime" + testId);
            long finishTime = System.currentTimeMillis() / 1000L;
            double timeSpent = ((finishTime - startTime) > testDuration ? 100 : (finishTime - startTime) * 100d / testDuration);
            session.removeAttribute("startTime" + testId);
            session.removeAttribute("timeLeft" + testId);

            int userId = ((User) session.getAttribute(Consts.CURRENT_USER)).getId();
            Consts.PASSED_TESTS_DAO.insertNew(testId, userId, (int) questionsNum, correctAnswers, correctAnswers * 100 / questionsNum, timeSpent);
            resp.sendRedirect(Routes.PASSED_TESTS);
        } catch (DBException e) {
            logger.error("Couldn't process the results of the test.", e);
            throw new ServletException("Couldn't process the results of the test.");
        }
    }
}
