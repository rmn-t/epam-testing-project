package com.epam.db.dao.sql;

import com.epam.db.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.TestDao;
import com.epam.db.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDaoSql implements TestDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String COMPLEXITY = "complexity";
    private static final String DURATION = "duration_sec";
    private static final String QUESTIONS_NUM = "questionsNum";
    private Logger logger = LoggerFactory.getLogger(TestDaoSql.class);
    private final String[] VALID_COLUMNS_FOR_ORDER_BY = {ID, NAME, SUBJECT, COMPLEXITY, DURATION, QUESTIONS_NUM};

    public TestDaoSql() {
    }

    public Test getTestById(int testId) throws DBException {
        Test test = null;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT test.id as id,test.name as name,subject complexity,duration_sec,count(question.test_id) as questionsNum" +
                    " FROM TEST left join question on test.id = question.test_id group by test.id; where id = ?;");
            prepStmt.setInt(1, testId);
            rs = prepStmt.executeQuery();
            rs.next();
            test = new Test.Builder()
                    .setId(rs.getInt(ID))
                    .setName(rs.getString(NAME))
                    .setSubject(rs.getString(SUBJECT))
                    .setComplexity(rs.getString(COMPLEXITY))
                    .setDuration(rs.getInt(DURATION))
                    .setQuestionsNum(rs.getInt(QUESTIONS_NUM))
                    .build();
            logger.info("Requested test by id {}, result name : {}", testId, test.getName());
        } catch (SQLException e) {
            logger.error("Failed to get test by id {}.", testId, e);
            throw new DBException("Failed to get test by id.", e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return test;
    }

    public void updateTestById(int id, String name, String subject, String complexity, int duration) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE test SET name = ?, subject = ?, complexity = ?, duration_sec = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++, name);
            prepStmt.setString(k++, subject);
            prepStmt.setString(k++, complexity);
            prepStmt.setInt(k++, duration);
            prepStmt.setInt(k++, id);
            prepStmt.executeUpdate();
            logger.info("Successfully updated test by id {}.", id);
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
            logger.info("Successfully deleted test by id {}.", id);
        } catch (SQLException e) {
            logger.error("Failed to delete test by id {}.", id, e);
            throw new DBException("Failed to delete test by id.", e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
    }

    public List<Test> getAllTests() {
        List<Test> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            Context context = (Context) new InitialContext().lookup("java:/comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/mysql");
            con = dataSource.getConnection();
            prepStmt = con.prepareStatement("SELECT id,name,subject,complexity,duration_sec,questionsNum FROM test;");
            rs = prepStmt.executeQuery();
            while (rs.next()) {

            }
        } catch (NamingException | SQLException e) {
            //add logger
            e.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy) throws DBException {
        List<Test> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            orderBy = Arrays.asList(VALID_COLUMNS_FOR_ORDER_BY).contains(orderBy) ? orderBy : VALID_COLUMNS_FOR_ORDER_BY[1];
            prepStmt = con.prepareStatement("SELECT id,name,subject,complexity,duration_sec,questionsNum FROM test ORDER BY " + orderBy + " LIMIT ?, ?;");
            prepStmt.setInt(1, offset - 1);
            prepStmt.setInt(2, limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(new Test.Builder()
                        .setId(rs.getInt(ID))
                        .setName(rs.getString(NAME))
                        .setSubject(rs.getString(SUBJECT))
                        .setComplexity(rs.getString(COMPLEXITY))
                        .setDuration(rs.getInt(DURATION))
                        .setQuestionsNum(rs.getInt(QUESTIONS_NUM))
                        .build());
            }
            logger.info("Successfully obtained {} tests.",results.size());
        } catch (SQLException e) {
            logger.error("Failed to obtain tests (limited,sorted,ordered).",e);
            throw new DBException("Failed to obtain tests (limited,sorted,ordered).",e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public int insertNewTest(String name, String subject, String complexity, int durationSec) throws DBException {
        int res = 0;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO test(name,subject,complexity,duration_sec) values(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            int k = 0;
            prepStmt.setString(++k, name);
            prepStmt.setString(++k, subject);
            prepStmt.setString(++k, complexity);
            prepStmt.setInt(++k, durationSec);
            prepStmt.executeUpdate();
            generatedKeys = prepStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = generatedKeys.getInt(1);
            } else {
                throw new DBException("Creating user failed, no ID obtained.",new Throwable());
            }
            logger.info("Successfully created new test.");
        } catch (SQLException e) {
            logger.error("Failed to insert new test.");
            throw new DBException("Failed to insert new test.",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt, con);
        }
        return res;
    }

    public int getTestsNumber() throws DBException {
        int res;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM test;");
            rs.next();
            res = rs.getInt("total");
            logger.info("Total number of tests in test table is {}.",res);
        } catch (SQLException e) {
            logger.error("Failed to get the total tests number.",e);
            throw new DBException("Failed to get the total tests number.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, stmt, con);
        }
        return res;
    }

}
