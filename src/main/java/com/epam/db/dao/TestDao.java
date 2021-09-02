package com.epam.db.dao;

import com.epam.db.DBException;
import com.epam.db.model.Test;

import java.util.List;

public interface TestDao {
    Test getTestById(int testId) throws DBException;

    void updateTestById(int id, String name, String subject, String complexity, int duration) throws DBException;

    void deleteTestById(int id) throws DBException;

    List<Test> getAllTests();

    List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy) throws DBException;

    int insertNewTest(String name, String subject, String complexity, int durationSec) throws DBException;

    int getTestsNumber() throws DBException;

}
