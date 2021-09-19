package com.epam.controller.user;

import com.epam.controller.util.IPaginatable;
import com.epam.controller.util.Views;
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
 * Servlet is responsible for preparing info and rendering all users page.
 */
@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet implements IPaginatable {
    private final Logger logger = LoggerFactory.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int recordsPerPage = calculateRecordsPerPageNum(req, "perPage");
            int recordsOffset = calculateRecordsOffset(req, recordsPerPage, "page");
            String sorting = getSortingValue(req, "sort", Consts.getVALID_COLUMNS_FOR_USER_ORDER_BY(), Consts.USER_DEFAULT_SORT);
            int statusId = Integer.parseInt(req.getParameter("statusId"));
            int usersNum = Consts.USER_DAO.getRecordsNumByStatusId(statusId);
            int lastPage = usersNum / recordsPerPage + ((usersNum % recordsPerPage == 0) ? 0 : 1);

            if ((recordsOffset < 0 || recordsOffset > usersNum) && recordsOffset != 1) {
                logger.error("User supplied invalid page number.");
                throw new ServletException("Couldn't obtain users information.");
            }

            req.setAttribute("users", Consts.USER_DAO.getRecordsLimitedSortedFiltered(recordsOffset, recordsPerPage, sorting, statusId));
            req.setAttribute("lastPage", lastPage);
            req.setAttribute("statuses", Consts.STATUS_DAO.getAllRecords());
            req.setAttribute("roles", Consts.ROLE_DAO.getAllRecords());
        } catch (DBException | NumberFormatException e) {
            logger.error("Couldn't obtain users information.", e);
            throw new ServletException("Couldn't obtain users information.");
        }
        req.getRequestDispatcher(Views.USERS_JSP).forward(req, resp);
    }

}
