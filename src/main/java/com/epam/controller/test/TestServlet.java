package com.epam.controller.test;

import com.epam.db.DBException;
import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.QuestionDaoSql;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Question;
import com.epam.db.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(TestServlet.class);
    private QuestionDao questionDao;
    private TestDao testDao;

    @Override
    public void init() throws ServletException {
        questionDao = new QuestionDaoSql();
        testDao = new TestDaoSql();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost for /test");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = null;
        try {
            questions = questionDao.getQuestionsAndAnswersByTestId(testId);
        } catch (DBException e) {
            logger.error("Test servlet - get.",e);
        }
        Test test = null;
        try {
            test = testDao.getTestById(testId);
        } catch (DBException e) {
            logger.error("Test servlet get",e);
        }
        req.setAttribute("questions",questions);
        req.setAttribute("test",test);
        req.getRequestDispatcher("/test.jsp").forward(req,resp);
    }
}
