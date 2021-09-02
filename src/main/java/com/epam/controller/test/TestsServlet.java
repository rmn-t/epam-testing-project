package com.epam.controller.test;

import com.epam.db.dao.sql.TestDaoSql;
import com.epam.db.model.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/tests"})
public class TestsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.setAttribute("testsSorting",req.getParameter("testsSorting"));
        /**
         * PRG over here BOOOOY
         */
        resp.sendRedirect("tests?page=1");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageId = Integer.parseInt(req.getParameter("page"));
        int recordsPerPage = 10;
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }
        HttpSession session = req.getSession(false);
        List<Test> tests = TestDaoSql.getTestsLimitedSorted(pageId,recordsPerPage,session.getAttribute("testsSorting").toString());
        int totalTests = TestDaoSql.getTestsNumber();
        int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);
        req.setAttribute("tests",tests);
        req.setAttribute("lastPage",lastPage);
        req.getRequestDispatcher("/tests.jsp").forward(req,resp);
    }
}
