package com.epam.controller.question;

import com.epam.controller.util.CookieUtil;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.controller.util.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that processes all the incoming requests for rendering an add new question form.
 */
@WebServlet(urlPatterns = {"/add/question"})
public class AddQuestionServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(AddQuestionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String lang = CookieUtil.getCookieValueByName(req.getCookies(), "lang", "en");
            int testId = Integer.parseInt(req.getParameter("testId"));
            if (Consts.TEST_DAO.getTestById(testId,lang) != null) {
                req.getRequestDispatcher(Views.QUESTION_FORM_JSP).forward(req, resp);
            }
        } catch (NumberFormatException | DBException e) {
            logger.error("Couldn't parse provided test id to add question to.");
            throw new ServletException("Unable to process test id for new question.");
        }
    }
}
