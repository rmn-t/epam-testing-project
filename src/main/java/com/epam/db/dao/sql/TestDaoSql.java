package com.epam.db.dao.sql;

import com.epam.exceptions.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.TestDao;
import com.epam.db.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDaoSql implements TestDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String COMPLEXITY = "complexity";
    private static final String DURATION = "duration_sec";
    private final Logger logger = LoggerFactory.getLogger(TestDaoSql.class);

    public TestDaoSql() {
    }

    public Test getTestById(int testId) throws DBException {
        Test test = null;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("" +
                    "SELECT test.id,test.name,subject.name as subject,complexity.name as complexity,duration_sec,count(question.test_id) AS questionsNum " +
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
                    .setComplexity(rs.getString(COMPLEXITY))
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

    public void updateTestById(int id, String name, int subjectId, int complexityId, int duration) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE test SET name = ?, subject_id = ?, complexity_id = ?, duration_sec = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++, name);
            prepStmt.setInt(k++, subjectId);
            prepStmt.setInt(k++, complexityId);
            prepStmt.setInt(k++, duration);
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

    public List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy, int subjectId) throws DBException {
        List<Test> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String whereSubjectId = subjectId == 0 ? "" : " WHERE subject_id = " + subjectId;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("" +
                    "SELECT test.id,test.name,subject.name as subject,complexity.name as complexity,duration_sec,count(question.test_id) AS questionsNum " +
                    "FROM test " +
                    "LEFT JOIN question ON test.id = question.test_id " +
                    "LEFT JOIN complexity ON complexity.id = test.complexity_id " +
                    "LEFT JOIN subject ON subject.id = test.subject_id " + whereSubjectId +
                    " GROUP BY test.id ORDER BY " + orderBy + " LIMIT ?, ?;"
            );
            int k = 1;
            prepStmt.setInt(k++, offset - 1);
            prepStmt.setInt(k++, limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(new Test.Builder()
                        .setId(rs.getInt(ID))
                        .setName(rs.getString(NAME))
                        .setSubject(rs.getString(SUBJECT))
                        .setComplexity(rs.getString(COMPLEXITY))
                        .setDuration(rs.getInt(DURATION))
                        .setQuestionsNum(rs.getInt("questionsNum"))
                        .build());
            }
            logger.debug("Successfully obtained {} tests.",results.size());
        } catch (SQLException e) {
            logger.error("Failed to obtain tests (limited,sorted,ordered).",e);
            throw new DBException("Failed to obtain tests (limited,sorted,ordered).",e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public int insertNewTest(String name, int subjectId, int complexityId, int durationSec) throws DBException {
        int res = 0;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO test(name,subject_id,complexity_id,duration_sec) values(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
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
                throw new DBException("Creating user failed, no ID obtained.",new Throwable());
            }
            logger.debug("Successfully created new test.");
        } catch (SQLException e) {
            logger.error("Failed to insert new test.");
            throw new DBException("Failed to insert new test.",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
        return res;
    }

    public int getRecordsNumBySubjectId(int subjectId) throws DBException {
        int res;
        String whereSubjectId = subjectId == 0 ? "" : " WHERE subject_id = " + subjectId;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT COUNT(*) AS total FROM test " + whereSubjectId + ";");
            rs = prepStmt.executeQuery();
            rs.next();
            res = rs.getInt("total");
            logger.debug("Total number of tests in test table is {}.",res);
        } catch (SQLException e) {
            logger.error("Failed to get the total tests number.",e);
            throw new DBException("Failed to get the total tests number.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return res;
    }

}
