package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.PassedTest;

import java.util.List;

public interface PassedTestsDao {

    void insertPassedTest(int testId, int userId, double grade, int timeSpent) throws DBException;

    List<PassedTest> getPassedTestsByUserIdOrderedLimited(int userId, int offset, int limit, String orderBy) throws DBException;

    int getPassedTestsNumberByUserId(int userId) throws DBException;
}
