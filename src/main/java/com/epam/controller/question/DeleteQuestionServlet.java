package com.epam.controller.question;

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
 * Servlet processes all incoming requests for deleting a particular question.
 */
@WebServlet(urlPatterns = {"/delete/question"})
public class DeleteQuestionServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeleteQuestionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int questionId = Integer.parseInt(req.getParameter("id"));
            int testId = Integer.parseInt(req.getParameter("testId"));
            int questionsLeft = Integer.parseInt(req.getParameter("questionsLeft"));
            Consts.QUESTION_DAO.deleteQuestionById(questionId,testId,questionsLeft);
        } catch (DBException e) {
            logger.error("Couldn't delete the question.", e);
            throw new ServletException("Couldn't delete the question.");
        }
        int testId = Integer.parseInt(req.getParameter("testId"));
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
