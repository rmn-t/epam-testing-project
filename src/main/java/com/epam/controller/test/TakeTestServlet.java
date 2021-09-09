package com.epam.controller.test;

import com.epam.exceptions.DBException;
import com.epam.db.dao.PassedTestsDao;
import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.PassedTestsDaoSql;
import com.epam.db.dao.sql.QuestionDaoSql;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Question;
import com.epam.db.model.Test;
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
import java.util.List;

@WebServlet(urlPatterns = {"/take/test"})
public class TakeTestServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(TakeTestServlet.class);
    private QuestionDao questionDao;
    private PassedTestsDao passedTestsDao;
    private TestDao testDao;

    @Override
    public void init() throws ServletException {
        this.questionDao = new QuestionDaoSql();
        this.passedTestsDao = new PassedTestsDaoSql();
        this.testDao = new TestDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = null;
        Test test = null;
        try {
            questions = questionDao.getQuestionsAndAnswersByTestId(testId);
            test = testDao.getTestById(testId);
        } catch (DBException e) {
            logger.error("Take test servlet get", e);
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
        req.getRequestDispatcher("/" + Views.TAKE_TEST_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * add time to session if it is about to run out
         */
        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = null;
        int testDuration = 0;
        try {
            testDuration = testDao.getTestById(testId).getDuration();
            questions = questionDao.getQuestionsAndAnswersByTestId(testId);
        } catch (DBException e) {
            logger.error("Take test servlet post", e);
        }
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

        int timeSpent = (int) ((finishTime - startTime) > testDuration ? testDuration : finishTime - startTime);
        session.removeAttribute("startTime" + testId);
        session.removeAttribute("timeLeft" + testId);
        int userId = Integer.parseInt("" + session.getAttribute("userId"));
        try {
            passedTestsDao.insertPassedTest(testId, userId,(int) questionsNum,correctAnswers, correctAnswers * 100 / questionsNum, timeSpent);
        } catch (DBException e) {
            logger.error("Take test servlet post", e);
        }
        resp.sendRedirect("/epam/passed/tests?page=1");
    }
}
