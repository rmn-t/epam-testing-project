package com.epam.controller.util;

import com.epam.util.Consts;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Utility interface which holds the basic methods that are used for client side pagination. Created to simplify the process for multiple servlets.
 */
public interface IPaginatable {
    /**
     * Parses the records per page parameter from request and returns the value.
     *
     * @param req         Http Servlet Request
     * @param perPageName name of the parameter that holds records per page number
     * @return validates the value received from request parameter, if it's valid (exists among valid options defined in constants class) then returns value,
     * otherwise returns default value which is set to 10.
     */
    default int calculateRecordsPerPageNum(HttpServletRequest req, String perPageName) {
        int recordsPerPage = 10;
        if (req.getParameter(perPageName) != null) {
            int test = Integer.parseInt(req.getParameter(perPageName));
            if (Consts.getValidPerPageValues().contains(Integer.parseInt(req.getParameter(perPageName)))) {
                recordsPerPage = test;
            }
        }
        return recordsPerPage;
    }

    /**
     * Calculates the offset starting from which the data will be pulled from database in order to display in on certain page.
     *
     * @param req               Http Servlet Request
     * @param recordsPerPage    value of records per page
     * @param pageParameterName name of the parameter that hold the page num
     * @return return offset for the first record that will be displayed on the page
     */
    default int calculateRecordsOffset(HttpServletRequest req, int recordsPerPage, String pageParameterName) {
        int pageNum = 1;
        if (req.getParameter(pageParameterName) != null) {
            pageNum = Integer.parseInt(req.getParameter(pageParameterName));
        }
        if (pageNum > 1) {
            pageNum = (pageNum - 1) * recordsPerPage + 1;
        }
        return pageNum;
    }

    /**
     * Parses the sorting value from request
     *
     * @param req               Http Servlet Request
     * @param sortParameterName name of the parameter that holds sorting value
     * @param validSorts        list of the sorts that are valid for configuration
     * @param defaultSortParam  default sorting value in case there were no valid sorting value obtained from request parameter
     * @return returns either sorting value from parameter (if it passes validation), otherwise returns default passed in sorting
     */
    default String getSortingValue(HttpServletRequest req, String sortParameterName, List<String> validSorts, String defaultSortParam) {
        String sorting = req.getParameter(sortParameterName);
        if (sorting == null || !validSorts.contains(sorting)) {
            sorting = defaultSortParam;
        }
        return sorting;
    }

}
