package com.epam.db.dao;

import com.epam.db.model.Test;
import com.epam.exceptions.DBException;

import java.sql.Connection;
import java.util.List;

public interface TestDao {

    Test getTestById(int testId, String lang) throws DBException;

    void updateTestById(int id, String name, int subjectId, int complexityId, int duration, boolean isActive) throws DBException;

    void deleteTestById(int id) throws DBException;

    List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy, int subjectId, String isActive, String lang) throws DBException;

    int insertNewTest(String name, int subjectId, int complexityId, int durationSec) throws DBException;

    int getRecordsNumBySubjectId(int subjectId, String isActive) throws DBException;

    void deactivateTestById(Connection con, int id) throws DBException;

}
