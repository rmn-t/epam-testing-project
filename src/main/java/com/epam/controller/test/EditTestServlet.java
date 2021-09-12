package com.epam.controller.test;

import com.epam.db.model.Test;
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

@WebServlet("/editTest")
public class EditTestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(EditTestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        Test test = null;
        try {
            test = Consts.TEST_DAO.getTestById(testId);
        } catch (DBException e) {
            logger.error("Edit servlet get");
        }
        req.setAttribute("test",test);
//        req.getRequestDispatcher(Views.EDIT_TEST_JSP).forward(req,resp);
        logger.info("doGet()");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int subjectId = Integer.parseInt(req.getParameter("subject"));
        int complexityId = Integer.parseInt(req.getParameter("complexity"));
        boolean isActive = Boolean.parseBoolean(req.getParameter("isActive"));
        int duration = Integer.parseInt(req.getParameter("durationMin")) * 60 + Integer.parseInt(req.getParameter("durationSec"));
        try {
            Consts.TEST_DAO.updateTestById(testId,name,subjectId,complexityId,duration,isActive);
        } catch (DBException e) {
            logger.error("Edit servlet post");
        }
        resp.sendRedirect("/epam/test?id="+testId);
    }
}
