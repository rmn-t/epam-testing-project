package com.epam.controller.test;

import com.epam.db.dao.QuestionDao;
import com.epam.db.dao.TestDao;
import com.epam.db.entities.Question;
import com.epam.db.entities.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String msg = "JDBC Pool connection successfully created";
        Connection con = null;
        try {
            String dataSourceName = getServletContext().getInitParameter("DataSource");
            Context context = (Context) new InitialContext().lookup("java:/comp/env");
            System.out.println(dataSourceName);
            DataSource dataSource = (DataSource) context.lookup("jdbc/mysql");
//            System.out.println(dataSource.getClass().getName());
            con = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
            msg = e.getMessage();
        } catch (SQLException e) {
            msg = e.getMessage();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // do nothing
                }
            }
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Hello World</h1>");
        out.write("<hr/>");
        out.write("<p>" + msg + "</p>");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        System.out.println("test id " + testId);
        List<Question> questions = QuestionDao.getQuestionsAndAnswersByTestId(testId);
        Test test = TestDao.getTestById(testId);
        req.setAttribute("questions",questions);
        req.setAttribute("test",test);
        for (Question q : questions) {
            System.out.println(q.getAnswers());
        }
        req.getRequestDispatcher("/test.jsp").forward(req,resp);
    }
}
