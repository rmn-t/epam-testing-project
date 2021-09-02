package com.epam.controller.question;

import com.epam.db.DBException;
import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.sql.QuestionDaoSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/delete/question"})
public class DeleteQuestionServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(DeleteQuestionServlet.class);
    private QuestionDao questionDao;

    @Override
    public void init() throws ServletException {
        questionDao = new QuestionDaoSql();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = Integer.parseInt(req.getParameter("id"));
        try {
            questionDao.deleteQuestionById(questionId);
        } catch (DBException e) {
            logger.error("Delete question servlet - post",e);
        }
        HttpSession session = req.getSession(false);
        int testId = Integer.parseInt("" + session.getAttribute("currentTestId"));
        resp.sendRedirect("/epam/test?id=" + testId);
    }
}
