package com.epam.controller.question;

import com.epam.controller.util.HTMLSanitizer;
import com.epam.controller.util.Views;
import com.epam.db.model.Answer;
import com.epam.db.model.Question;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet process all get requests in order to display question and it's answers info and post requests
 * that update question info and it's answers.
 */
@WebServlet(urlPatterns = {"/edit/question"})
public class EditQuestionServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(EditQuestionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            int questionId = Integer.parseInt(req.getParameter("id"));
            Question q;
            try {
                q = Consts.QUESTION_DAO.getQuestionById(questionId);
                q.setAnswers(Consts.ANSWER_DAO.getAnswersByQuestionId(questionId));
            } catch (DBException e) {
                logger.error("Failed to obtain the question and it's answers data.", e);
                throw new ServletException("Failed to obtain the question and it's answers data.");
            }
            req.setAttribute("question", q);
        }
        req.getRequestDispatcher(Views.QUESTION_FORM_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] names = req.getParameterValues("name");
        String[] isCorrect = req.getParameterValues("isCorrect");
        String questionText = HTMLSanitizer.cleanLtMt(req.getParameter("questionText"));
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Answer a = new Answer();
            a.setText(HTMLSanitizer.cleanLtMt(names[i]));
            a.setIsCorrect(Boolean.parseBoolean(isCorrect[i]));
            answers.add(a);
        }

        int testId = Integer.parseInt("" + req.getParameter("testId"));
        int questionId;

        try {
            if (req.getParameter("id") != null && !"".equals(req.getParameter("id"))) {
                questionId = Integer.parseInt(req.getParameter("id"));
                Consts.QUESTION_DAO.updateQuestionAndItsAnswers(questionId, questionText, answers);
            } else {
                Consts.QUESTION_DAO.insertQuestionAndItsAnswersByTestId(questionText, testId, answers);
            }
        } catch (DBException e) {
            logger.error("Error while trying to update question and it's answers.", e);
            throw new ServletException("Error while trying to update question and it's answers.");
        }
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
