package com.epam.controller.test;

import com.epam.db.dao.sql.QuestionDaoSql;
import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Question;
import com.epam.db.model.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost for /test");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = QuestionDaoSql.getQuestionsAndAnswersByTestId(testId);
        Test test = TestDaoSql.getTestById(testId);
        req.setAttribute("questions",questions);
        req.setAttribute("test",test);
        req.getRequestDispatcher("/test.jsp").forward(req,resp);
    }
}
