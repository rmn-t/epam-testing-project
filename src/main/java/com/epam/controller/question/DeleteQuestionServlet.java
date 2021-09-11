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

@WebServlet(urlPatterns = {"/delete/question"})
public class DeleteQuestionServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeleteQuestionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = Integer.parseInt(req.getParameter("id"));
        try {
            Consts.QUESTION_DAO.deleteQuestionById(questionId);
        } catch (DBException e) {
            logger.error("Delete question servlet - post",e);
        }
        int testId = Integer.parseInt(req.getParameter("testId"));
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
