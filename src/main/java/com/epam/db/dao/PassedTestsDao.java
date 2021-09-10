package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.PassedTest;

import java.util.List;

public interface PassedTestsDao {

    void insertNew(int testId, int userId, int questionNum, int correctAnswers, double grade, int timeSpent) throws DBException;

    List<PassedTest> getRecordsByUserIdOrderedLimited(int userId, int offset, int limit, String orderBy) throws DBException;

    int getRecordsNumberByUserId(int userId) throws DBException;
}
