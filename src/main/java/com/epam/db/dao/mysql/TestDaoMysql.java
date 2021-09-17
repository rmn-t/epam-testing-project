package com.epam.db.dao.mysql;

import com.epam.exceptions.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.TestDao;
import com.epam.db.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDaoMysql implements TestDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String SUBJECT_ID = "subject_id";
    private static final String COMPLEXITY = "complexity";
    private static final String COMPLEXITY_ID = "complexity_id";
    private static final String DURATION = "duration_sec";
    private static final String IS_ACTIVE = "is_active";
    private final Logger logger = LoggerFactory.getLogger(TestDaoMysql.class);

    public TestDaoMysql() {
    }

    public Test getTestById(int testId) throws DBException {
        Test test = null;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("" +
                    "SELECT test.id,test.name,subject.name as subject,subject_id,complexity.name as complexity,complexity_id,duration_sec,is_active," +
                    "count(question.test_id) AS questionsNum " +
                    "FROM test LEFT JOIN question ON test.id = question.test_id " +
                    "LEFT JOIN complexity ON complexity.id = test.complexity_id " +
                    "LEFT JOIN subject ON subject.id = test.subject_id " +
                    "WHERE test.id = ?;"
            );
            prepStmt.setInt(1, testId);
            rs = prepStmt.executeQuery();
            rs.next();
            test = new Test.Builder()
                    .setId(rs.getInt(ID))
                    .setName(rs.getString(NAME))
                    .setSubject(rs.getString(SUBJECT))
                    .setSubjectId(rs.getInt(SUBJECT_ID))
                    .setComplexity(rs.getString(COMPLEXITY))
                    .setComplexityId(rs.getInt(COMPLEXITY_ID))
                    .setIsActive(rs.getBoolean(IS_ACTIVE))
                    .setDuration(rs.getInt(DURATION))
                    .setQuestionsNum(rs.getInt("questionsNum"))
                    .build();
            logger.debug("Requested test by id {}, result name : {}", testId, test.getName());
        } catch (SQLException e) {
            logger.error("Failed to get test by id {}.", testId, e);
            throw new DBException("Failed to get test by id.", e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return test;
    }

    public void updateTestById(int id, String name, int subjectId, int complexityId, int duration, boolean isActive) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE test SET name = ?, subject_id = ?, complexity_id = ?, duration_sec = ?,is_active = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++, name);
            prepStmt.setInt(k++, subjectId);
            prepStmt.setInt(k++, complexityId);
            prepStmt.setInt(k++, duration);
            prepStmt.setBoolean(k++, isActive);
            prepStmt.setInt(k++, id);
            prepStmt.executeUpdate();
            logger.debug("Successfully updated test by id {}.", id);
        } catch (SQLException e) {
            logger.error("Failed to update test by id {}.", id, e);
            throw new DBException("Failed to update test by id.", e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
    }

    public void deleteTestById(int id) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("DELETE FROM test WHERE id = ?;");
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
            logger.debug("Successfully deleted test by id {}.", id);
        } catch (SQLException e) {
            logger.error("Failed to delete test by id {}.", id, e);
            throw new DBException("Failed to delete test by id.", e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
    }

    public List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy, int subjectId, String isActive) throws DBException {
        List<Test> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String subjectIdEquals = subjectId == 0 ? "" : " subject_id = " + subjectId;
        String isActiveValue = mapIsActiveForQuery(isActive);
        String where = isActiveValue.equals("") && subjectIdEquals.equals("") ? "" : " WHERE ";
        String and = subjectIdEquals.equals("") || isActiveValue.equals("") ? "" : " AND ";
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("" +
                    "SELECT test.id,test.name,subject.name as subject,complexity.name as complexity,duration_sec,count(question.test_id) AS questionsNum,is_active " +
                    "FROM test " +
                    "LEFT JOIN question ON test.id = question.test_id " +
                    "LEFT JOIN complexity ON complexity.id = test.complexity_id " +
                    "LEFT JOIN subject ON subject.id = test.subject_id " + where + subjectIdEquals + and + isActiveValue +
                    " GROUP BY test.id ORDER BY " + orderBy + " LIMIT ?, ?;"
            );
            int k = 1;
            prepStmt.setInt(k++, offset - 1);
            prepStmt.setInt(k++, limit);
            logger.info(prepStmt.toString());
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(new Test.Builder()
                        .setId(rs.getInt(ID))
                        .setName(rs.getString(NAME))
                        .setSubject(rs.getString(SUBJECT))
                        .setComplexity(rs.getString(COMPLEXITY))
                        .setDuration(rs.getInt(DURATION))
                        .setIsActive(rs.getBoolean(IS_ACTIVE))
                        .setQuestionsNum(rs.getInt("questionsNum"))
                        .build());
            }
            logger.debug("Successfully obtained {} tests.", results.size());
        } catch (SQLException e) {
            logger.error("Failed to obtain tests (limited,sorted,ordered).", e);
            throw new DBException("Failed to obtain tests (limited,sorted,ordered).", e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    private String mapIsActiveForQuery(String isActiveValue) {
        if (isActiveValue == null) {
            return "";
        }
        if ("active".equals(isActiveValue)) {
            return " is_active = TRUE ";
        }
        if ("inactive".equals(isActiveValue)) {
            return " is_active = FALSE ";
        }
        return "";
    }

    public int insertNewTest(String name, int subjectId, int complexityId, int durationSec) throws DBException {
        int res = 0;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO test(name,subject_id,complexity_id,duration_sec,is_active) values(?,?,?,?,FALSE);", Statement.RETURN_GENERATED_KEYS);
            int k = 0;
            prepStmt.setString(++k, name);
            prepStmt.setInt(++k, subjectId);
            prepStmt.setInt(++k, complexityId);
            prepStmt.setInt(++k, durationSec);
            prepStmt.executeUpdate();
            generatedKeys = prepStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = generatedKeys.getInt(1);
            } else {
                throw new DBException("Creating user failed, no ID obtained.", new Throwable());
            }
            logger.debug("Successfully created new test.");
        } catch (SQLException e) {
            logger.error("Failed to insert new test.");
            throw new DBException("Failed to insert new test.", e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
        return res;
    }

    public int getRecordsNumBySubjectId(int subjectId, String isActiveValue) throws DBException {
        int res;
        String subjectIdEquals = subjectId == 0 ? "" : " subject_id = " + subjectId;
        String isActiveTrue = mapIsActiveForQuery(isActiveValue);
        String where = isActiveTrue.equals("") && subjectIdEquals.equals("") ? "" : " WHERE ";
        String and = subjectIdEquals.equals("") || isActiveTrue.equals("") ? "" : " AND ";
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT COUNT(*) AS total FROM test " + where + subjectIdEquals + and + isActiveTrue + ";");
            rs = prepStmt.executeQuery();
            rs.next();
            res = rs.getInt("total");
            logger.debug("Total number of tests in test table is {}.", res);
        } catch (SQLException e) {
            logger.error("Failed to get the total tests number.", e);
            throw new DBException("Failed to get the total tests number.", e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return res;
    }

}
