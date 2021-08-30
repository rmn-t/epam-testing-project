package com.epam.controller.question;

import com.epam.db.dao.QuestionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/delete/question"})
public class DeleteQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = Integer.parseInt(req.getParameter("id"));
        QuestionDao.deleteQuestionById(questionId);
        HttpSession session = req.getSession(false);
        int testId = Integer.parseInt("" + session.getAttribute("currentTestId"));
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
