package com.epam.controller.test;

import com.epam.db.model.Complexity;
import com.epam.db.model.Question;
import com.epam.db.model.Subject;
import com.epam.db.model.Test;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
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
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost for /test");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Subject> subjects = new ArrayList<>();
        List<Complexity> complexities = new ArrayList<>();
        try {
            subjects = Consts.SUBJECT_DAO.getAllRecords();
            req.setAttribute("subjects",subjects);
            complexities = Consts.COMPLEXITY_DAO.getAllRecords();
            req.setAttribute("complexities",complexities);
        } catch (DBException e) {
            logger.error("Couldn't find options for test creation. Subjects {}. Complexities {}.",subjects.size(),complexities.size(),e);
        }

        int testId = Integer.parseInt(req.getParameter("id"));
        List<Question> questions = null;
        try {
            questions = Consts.QUESTION_DAO.getQuestionsAndAnswersByTestId(testId);
            logger.debug(String.valueOf(questions));
        } catch (DBException e) {
            logger.error("Test servlet - get.",e);
        }
        Test test = null;
        try {
            test = Consts.TEST_DAO.getTestById(testId);
        } catch (DBException e) {
            logger.error("Test servlet get",e);
        }
        req.setAttribute("questions",questions);
        req.setAttribute("test",test);
        req.getRequestDispatcher(Views.VIEW_TEST_JSP).forward(req,resp);
    }
}
