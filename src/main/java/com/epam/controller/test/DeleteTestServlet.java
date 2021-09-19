package com.epam.controller.test;

import com.epam.controller.util.Routes;
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
 * Servlet is responsible for processing all incoming delete test requests.
 */
@WebServlet(urlPatterns = {"/delete/test"})
public class DeleteTestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeleteTestServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Consts.TEST_DAO.deleteTestById(id);
        } catch (DBException e) {
            logger.error("Couldn't delete test.",e);
            throw new ServletException("Couldn't delete test.");
        }
        resp.sendRedirect(Routes.SLASH_HOME_TESTS);
    }
}
