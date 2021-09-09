package com.epam.controller.user;

import com.epam.db.dao.RoleDao;
import com.epam.db.dao.StatusDao;
import com.epam.db.dao.UserDao;
import com.epam.db.dao.sql.RoleDaoSql;
import com.epam.db.dao.sql.StatusDaoSql;
import com.epam.db.dao.sql.UserDaoSql;
import com.epam.db.model.Role;
import com.epam.db.model.Status;
import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Consts;
import com.epam.util.Views;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    private static Logger logger;
    private UserDao userDao;
    private StatusDao statusDao;
    private RoleDao roleDao;

    @Override
    public void init() throws ServletException {
        logger = LogManager.getLogger(UsersServlet.class);
        userDao = new UserDaoSql();
        statusDao = new StatusDaoSql();
        roleDao = new RoleDaoSql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordsPerPage = 10;
        if (req.getParameter("perPage") != null) {
            try {
                int test = Integer.parseInt(req.getParameter("perPage"));
                if (Consts.getValidPerPageValues().contains(Integer.parseInt(req.getParameter("perPage")))) {
                    recordsPerPage = test;
                }
            } catch (NumberFormatException e) {
                logger.info("Couldn't convert records page.");
            }
        }

        int pageId = 1;
        if (req.getParameter("page") != null) {
            pageId = Integer.parseInt(req.getParameter("page"));
        }
        if (pageId > 1) {
            pageId = (pageId -1) * recordsPerPage + 1;
        }

        String sorting = req.getParameter("sort");
        if (sorting == null || !Consts.getVALID_COLUMNS_FOR_USER_ORDER_BY().contains(sorting)) {
            sorting = Consts.USER_DEFAULT_SORT; // default sort
        }

        List<Status> statuses = new ArrayList<>();
        try {
            statuses = statusDao.getAllRecords();
            req.setAttribute("statuses",statuses);
        } catch (DBException e) {
            logger.error("Test servlet do get");
        }

        List<Role> roles = new ArrayList<>();
        try {
            roles = roleDao.getAllRecords();
            req.setAttribute("roles",roles);
        } catch (DBException e) {
            logger.error("Couldn't obtain roles list.");
        }

        int statusId = 0;
        if (req.getParameter("statusId") != null) {
            try {
                int test = Integer.parseInt(req.getParameter("statusId"));
                if (test >= 0 && test <= statuses.size()) {
                    statusId = test;
                }
            } catch (NumberFormatException e) {
                logger.info("Tried to supply not valid number format as status id.",e);
            }
        }

        try {
            List<User> users = userDao.getRecordsLimitedSortedFiltered(pageId,recordsPerPage,sorting,statusId);
            int usersNum = userDao.getRecordsNumByStatusId(statusId);
            int lastPage = usersNum / recordsPerPage + ((usersNum % recordsPerPage == 0) ? 0 : 1);
            req.setAttribute("users",users);
            req.setAttribute("lastPage",lastPage);
        } catch (DBException e) {
            logger.error("Users servlet get.",e);
        }
        req.getRequestDispatcher(Views.USERS_JSP).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("post()");
    }
}
