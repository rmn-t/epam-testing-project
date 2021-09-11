package com.epam.controller.user;

import com.epam.controller.IPaginatable;
import com.epam.db.model.Role;
import com.epam.db.model.Status;
import com.epam.db.model.User;
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

@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet implements IPaginatable {
    private final Logger logger = LoggerFactory.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordsPerPage = calculateRecordsPerPageNum(req,logger,"perPage");
        int pageId = calculatePage(req,recordsPerPage,"page");
        String sorting = getSortingValue(req,"sort",Consts.getVALID_COLUMNS_FOR_USER_ORDER_BY(),Consts.USER_DEFAULT_SORT);

        List<Status> statuses = new ArrayList<>();
        try {
            statuses = Consts.STATUS_DAO.getAllRecords();
            req.setAttribute("statuses",statuses);
        } catch (DBException e) {
            logger.error("Test servlet do get");
        }

        List<Role> roles = new ArrayList<>();
        try {
            roles = Consts.ROLE_DAO.getAllRecords();
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
            List<User> users = Consts.USER_DAO.getRecordsLimitedSortedFiltered(pageId,recordsPerPage,sorting,statusId);
            int usersNum = Consts.USER_DAO.getRecordsNumByStatusId(statusId);
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
