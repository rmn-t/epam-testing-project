package com.epam.controller.user;

import com.epam.db.dao.UserDao;
import com.epam.db.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }

    // 250 millisec

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        User user = UserDao.getUserDetailsByUserName(username);
        if (UserDao.validateCredentials(user,req.getParameter("password"),username)) {
            HttpSession session = req.getSession();
            session.setAttribute("username",user.getUsername());
            session.setAttribute("userId",user.getId());
            session.setAttribute("userStatus",user.getStatus());
            session.setAttribute("testsSorting","name ASC");
            session.setAttribute("passedTestsSorting","date DESC");
            resp.sendRedirect("tests?page=1");
        } else {
            resp.sendRedirect("login");
        }
    }
}
