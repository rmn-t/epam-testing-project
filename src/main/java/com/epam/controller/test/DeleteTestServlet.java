package com.epam.controller.test;

import com.epam.db.dao.TestDao;
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

@WebServlet(urlPatterns = {"/delete/test"})
public class DeleteTestServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(DeleteTestServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Consts.TEST_DAO.deleteTestById(id);
        } catch (DBException e) {
            logger.error("Delete servlet post",e);
        }
        resp.sendRedirect("/epam/tests?page=1");
    }
}
