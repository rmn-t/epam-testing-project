package com.epam.controller.test;

import com.epam.db.dao.PassedTestsDao;
import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.TestDao;
import com.epam.db.entities.Question;
import com.epam.db.entities.Test;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = QuestionDao.getQuestionsAndAnswersByTestId(testId);
        Test test = TestDao.getTestById(testId);
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
        req.getRequestDispatcher("/takeTest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = QuestionDao.getQuestionsAndAnswersByTestId(testId);
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
        int testDuration = TestDao.getTestById(testId).getDuration();
        int timeSpent = (int) ((finishTime - startTime) > testDuration ? testDuration : finishTime - startTime);
        session.removeAttribute("startTime" + testId);
        session.removeAttribute("timeLeft" + testId);
        int userId = Integer.parseInt("" + session.getAttribute("userId"));
        PassedTestsDao.insertPassedTest(testId,userId, correctAnswers * 100 / questionsNum, timeSpent);
        resp.sendRedirect("/epam/passed/tests?page=1");
    }
}
