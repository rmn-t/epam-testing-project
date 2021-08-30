package com.epam.controller.question;

import com.epam.db.dao.AnswerDao;
import com.epam.db.dao.QuestionDao;
import com.epam.db.entities.Answer;
import com.epam.db.entities.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/edit/question"})
public class EditQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditQuestionServlet()");
        HttpSession session = req.getSession(false);
        System.out.println(session.getAttribute("currentTestId"));
        if (req.getParameterMap().containsKey("id")) {
            int questionId = Integer.parseInt(req.getParameter("id"));
            Question q = QuestionDao.getQuestionById(questionId);
            q.setAnswers(AnswerDao.getAnswersByQuestionId(questionId));
            req.setAttribute("question",q);
        }
        req.setAttribute("currentTestId",req.getParameter("currentTestId"));
        req.getRequestDispatcher("/questionForm.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("test edit servlet");
        String[] names = req.getParameterValues("name");
        String[] isCorrect = req.getParameterValues("isCorrect");
        String questionText = req.getParameter("questionText");
        List<Answer> answers = new ArrayList<>();
        HttpSession session = req.getSession(false);
        int testId = Integer.parseInt("" + session.getAttribute("currentTestId"));
        for (int i = 0; i < names.length; i++) {
            Answer a = new Answer();
            a.setText(names[i]);
            a.setIsCorrect(Boolean.parseBoolean(isCorrect[i]));
            answers.add(a);
        }
        int questionId;
        if (req.getParameterMap().containsKey("id") && !"".equals(req.getParameter("id"))) {
            questionId = Integer.parseInt(req.getParameter("id"));
            QuestionDao.updateQuestionAndItsAnswers(questionId, questionText, answers);
            System.out.println("param values names " + Arrays.toString(names));
            System.out.println("param values is correct " + Arrays.toString(isCorrect));
        } else {
            questionId = QuestionDao.insertQuestion(questionText,testId);
            if (questionId != -1) {
                AnswerDao.insertAnswersByQuestionId(questionId,answers);
            }
        }
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
