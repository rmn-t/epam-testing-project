package com.epam.controller.question;

import com.epam.db.dao.AnswerDao;
import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.sql.AnswerDaoSql;
import com.epam.db.dao.sql.QuestionDaoSql;
import com.epam.db.model.Answer;
import com.epam.db.model.Question;
import com.epam.exceptions.DBException;
import com.epam.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/edit/question"})
public class EditQuestionServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(EditQuestionServlet.class);
    private QuestionDao questionDao = new QuestionDaoSql();
    private AnswerDao answerDao = new AnswerDaoSql();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("id")) {
            int questionId = Integer.parseInt(req.getParameter("id"));
            Question q = null;
            try {
                q = questionDao.getQuestionById(questionId);
                q.setAnswers(answerDao.getAnswersByQuestionId(questionId));
            } catch (DBException e) {
                logger.error("Edit servlet exception - get",e);
            }
            req.setAttribute("question",q);
        }
        req.setAttribute("currentTestId",req.getParameter("currentTestId"));
        req.getRequestDispatcher("/"+Views.QUESTION_FORM_JSP).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getParameter("id"));
        String[] names = req.getParameterValues("name");
        String[] isCorrect = req.getParameterValues("isCorrect");
        String questionText = req.getParameter("questionText");
        List<Answer> answers = new ArrayList<>();
//        HttpSession session = req.getSession(false);
        int testId = Integer.parseInt("" + req.getParameter("testId"));
        for (int i = 0; i < names.length; i++) {
            Answer a = new Answer();
            a.setText(names[i]);
            a.setIsCorrect(Boolean.parseBoolean(isCorrect[i]));
            answers.add(a);
        }
        int questionId;
        if (req.getParameterMap().containsKey("id") && !"".equals(req.getParameter("id"))) {
            logger.info(req.getParameter("id"));
            questionId = Integer.parseInt(req.getParameter("id"));
            try {
                questionDao.updateQuestionAndItsAnswers(questionId, questionText, answers);
            } catch (DBException e) {
                logger.error("Edit servlet exception - post",e);
            }
            System.out.println("param values names " + Arrays.toString(names));
            System.out.println("param values is correct " + Arrays.toString(isCorrect));
        } else {
            try {
                questionId = questionDao.insertQuestionByTestId(questionText,testId);
                logger.info(String.valueOf(questionId));
                if (questionId != -1) {
                    answerDao.insertAnswersByQuestionId(questionId,answers);
                }
            } catch (DBException e) {
                logger.error("Edit servlet exception - post",e);
            }
        }
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
