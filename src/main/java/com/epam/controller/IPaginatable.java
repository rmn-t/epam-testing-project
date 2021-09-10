package com.epam.controller;

import com.epam.util.Consts;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPaginatable {
    default int calculateRecordsPerPageNum(HttpServletRequest req, Logger logger, String perPageName) {
        int recordsPerPage = 10;
        if (req.getParameter(perPageName) != null) {
            try {
                int test = Integer.parseInt(req.getParameter(perPageName));
                if (Consts.getValidPerPageValues().contains(Integer.parseInt(req.getParameter(perPageName)))) {
                    recordsPerPage = test;
                }
            } catch (NumberFormatException e) {
                logger.info("Couldn't convert records page.");
            }
        }
        return recordsPerPage;
    }

    default int calculatePage(HttpServletRequest req, int recordsPerPage, String pageParameterName) {
        int pageNum = 1;
        if (req.getParameter(pageParameterName) != null) {
            pageNum = Integer.parseInt(req.getParameter(pageParameterName));
        }
        if (pageNum > 1) {
            pageNum = (pageNum -1) * recordsPerPage + 1;
        }
        return pageNum;
    }

    default String getSortingValue(HttpServletRequest req, String sortParameterName, List<String> validSorts,String defaultSortCol) {
        String sorting = req.getParameter(sortParameterName);
        if (sorting == null || !validSorts.contains(sorting)) {
            sorting = defaultSortCol;
        }
        return sorting;
    }

}
