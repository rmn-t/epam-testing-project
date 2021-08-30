package com.epam.controller.test;

import com.epam.db.dao.TestDao;
import com.epam.db.entities.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet("/tests")
@WebServlet(urlPatterns = {"/tests"})
public class TestsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        StringBuffer we = req.getRequestURL();
//        req.setAttribute("path",we);
//        System.out.println(we);
        HttpSession session = req.getSession(false);
        session.setAttribute("testsSorting",req.getParameter("testsSorting"));
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageId = Integer.parseInt(req.getParameter("page"));
        int recordsPerPage = 10;
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }
        HttpSession session = req.getSession(false);
        System.out.println(session.getAttribute("testsSorting").toString());
        List<Test> tests = TestDao.getTestsLimitedSorted(pageId,recordsPerPage,session.getAttribute("testsSorting").toString());
        System.out.println(TestDao.getTestsNumber());
        int totalTests = TestDao.getTestsNumber();
        int lastPage = totalTests / recordsPerPage + ((totalTests % recordsPerPage == 0) ? 0 : 1);
        System.out.println(lastPage);
        req.setAttribute("tests",tests);
        req.setAttribute("lastPage",lastPage);
        req.getRequestDispatcher("/tests.jsp").forward(req,resp);
    }
}
