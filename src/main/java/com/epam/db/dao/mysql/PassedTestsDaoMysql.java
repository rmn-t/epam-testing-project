package com.epam.db.dao.mysql;

import com.epam.db.DBUtil;
import com.epam.db.dao.PassedTestsDao;
import com.epam.db.model.PassedTest;
import com.epam.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassedTestsDaoMysql implements PassedTestsDao {
    private Logger logger = LoggerFactory.getLogger(PassedTestsDaoMysql.class);

    public void insertNew(int testId, int userId, int questionNum, int correctAnswers, double grade, int timeSpent) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO passed_tests(user_id,test_id,question_num,correct_answers,grade,time_spent) VALUES(?,?,?,?,?,?);");
            int k = 1;
            prepStmt.setInt(k++, userId);
            prepStmt.setInt(k++, testId);
            prepStmt.setInt(k++, questionNum);
            prepStmt.setInt(k++, correctAnswers);
            prepStmt.setDouble(k++, grade);
            prepStmt.setInt(k++, timeSpent);
            prepStmt.executeUpdate();
            logger.debug("Successfully inserted a new passed test for test_id {} | user_id : {} | with grade: {} and time spent: {} ", testId, userId, grade, timeSpent);
        } catch (SQLException e) {
            logger.error("Couldn't insert a new passed test for test {}.", testId, e);
            throw new DBException("Couldn't insert a new passed test for test {}.", e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
    }

    public List<PassedTest> getRecordsByUserIdOrderedLimited(int userId, int offset, int limit, String orderBy) throws DBException {
        List<PassedTest> passedTests = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("" +
                    "SELECT passed_tests.id as id,user_id,test_id, test.name as testName,question_num,grade,correct_answers,time_spent,date FROM passed_tests " +
                    "INNER JOIN test ON passed_tests.test_id = test.id " +
                    "WHERE user_id = ? ORDER BY " + orderBy + " LIMIT ?,?;");
            int k = 1;
            prepStmt.setInt(k++, userId);
            prepStmt.setInt(k++, offset - 1);
            prepStmt.setInt(k++, limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                passedTests.add(new PassedTest.Builder()
                        .setId(rs.getInt("id"))
                        .setUserId(rs.getInt("user_id"))
                        .setTestId(rs.getInt("test_id"))
                        .setTestName(rs.getString("testName"))
                        .setQuestionNum(rs.getInt("question_num"))
                        .setCorrectAnswers(rs.getInt("correct_answers"))
                        .setGrade(rs.getDouble("grade"))
                        .setTimeSpent(rs.getInt("time_spent"))
                        .setDate(rs.getString("date"))
                        .build());
            }
            logger.debug("Successfully obtained passed tests. Query : SELECT id,user_id,test_id,grade,time_spent,date FROM passed_tests WHERE user_id = {} ORDER BY {} LIMIT {},{};",userId,orderBy,offset-1,limit);
        } catch (SQLException e) {
            logger.error("Couldn't obtain passed tests by user.",e);
            throw new DBException("Couldn't obtain passed tests by user.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return passedTests;
    }

    public int getRecordsNumberByUserId(int userId) throws DBException {
        int res;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT COUNT(*) as total FROM passed_tests WHERE user_id = ?;");
            prepStmt.setInt(1, userId);
            rs = prepStmt.executeQuery();
            rs.next();
            res = rs.getInt("total");
            logger.debug("Total number of obtained passed tests by user_id {} is {}.",userId,res);
        } catch (SQLException e) {
            logger.error("Couldn't obtain the number of passed tests by user.",e);
            throw new DBException("Couldn't obtain the number of passed tests by user.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return res;
    }

}
