package com.epam.controller.test;

import com.epam.db.model.Complexity;
import com.epam.db.model.Subject;
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

@WebServlet("/createTest")
public class AddTestServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        req.getRequestDispatcher(Views.CREATE_TEST_JSP).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("testName");
        int subjectId = Integer.parseInt(req.getParameter("subject"));
        int complexityId = Integer.parseInt(req.getParameter("complexity"));
        int durationMin = Integer.parseInt(req.getParameter("durationMin")) * 60;
        int durationSec = Integer.parseInt(req.getParameter("durationSec"));
        int totalDuration = durationMin + durationSec == 0 ? 1 : durationMin + durationSec;
        int id = 0;
        try {
            id = Consts.TEST_DAO.insertNewTest(name,subjectId,complexityId,totalDuration);
        } catch (DBException e) {
            logger.error("Create test servlet post",e);
        }
        resp.sendRedirect("/epam/test?id="+id);
    }
}
