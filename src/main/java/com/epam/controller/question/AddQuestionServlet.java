package com.epam.controller.question;

import com.epam.db.dao.TestDao;
import com.epam.db.dao.sql.TestDaoSql;
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

@WebServlet(urlPatterns = {"/add/question"} )
public class AddQuestionServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TestDao testDao;

    @Override
    public void init() throws ServletException {
        testDao = new TestDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int testId = Integer.parseInt(req.getParameter("testId"));
            if (testDao.getTestById(testId) != null) {
                req.getRequestDispatcher("/"+Views.QUESTION_FORM_JSP).forward(req,resp);
            }
        } catch (NumberFormatException | DBException e) {
            logger.error("Couldn't parse test id.");
            resp.sendRedirect("/epam/tests");
        }
    }
}