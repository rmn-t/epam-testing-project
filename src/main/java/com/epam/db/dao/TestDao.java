package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.Test;

import java.util.List;

public interface TestDao {

    Test getTestById(int testId) throws DBException;

    void updateTestById(int id, String name, int subjectId, int complexityId, int duration) throws DBException;

    void deleteTestById(int id) throws DBException;

    List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy, int subjectId) throws DBException;

    int insertNewTest(String name, int subjectId, int complexityId, int durationSec) throws DBException;

    int getTestsNumber() throws DBException;

    int getTotalTestsNumForPagination(int subjectId) throws DBException;

}
