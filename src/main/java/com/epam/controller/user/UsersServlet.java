package com.epam.controller.user;

import com.epam.db.dao.UserDao;
import com.epam.db.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageId = Integer.parseInt(req.getParameter("page"));
        String sorting = req.getParameter("sort");
        int recordsPerPage = 10;
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }
        List<User> users = UserDao.getAllUsersLimitedSorted(pageId,recordsPerPage,sorting + " ASC");
        int usersNum = UserDao.getNumOfUsers();
        int lastPage = usersNum / recordsPerPage + ((usersNum % recordsPerPage == 0) ? 0 : 1);
        req.setAttribute("users",users);
        req.setAttribute("lastPage",lastPage);
        req.getRequestDispatcher("/users.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sort");
        resp.sendRedirect("/epam/users?page=1&sort=" + sort);
    }
}
