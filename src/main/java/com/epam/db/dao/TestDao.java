package com.epam.db.dao;

import com.epam.db.DBUtil;
import com.epam.db.entities.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDao {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SUBJECT = "subject";
    private static final String COMPLEXITY = "complexity";
    private static final String DURATION = "duration_sec";
    private static final String QUESTIONS_NUM = "questionsNum";

    private static final String queryAllTests = "SELECT id,name,subject,complexity,duration_sec,questionsNum FROM test;";

    private TestDao() {
    }

    public static Test getTestById(int testId) {
        Test test = null;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,name,subject,complexity,duration_sec,questionsNum FROM TEST where id = ?;");
            prepStmt.setInt(1,testId);
            rs = prepStmt.executeQuery();
            rs.next();
            test = mapTest(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return test;
    }

    public static void updateTestById(int id,String name, String subject,String complexity,int duration) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE test SET name = ?, subject = ?, complexity = ?, duration_sec = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++,name);
            prepStmt.setString(k++,subject);
            prepStmt.setString(k++,complexity);
            prepStmt.setInt(k++,duration);
            prepStmt.setInt(k++,id);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    /**
     * maybe worth to check if id exists in db before doing delete?
     * @param id
     */
    public static void deleteTestById(int id) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("DELETE FROM test WHERE id = ?;");
            prepStmt.setInt(1,id);
            prepStmt.executeUpdate();
            System.out.println("Deleted test by id");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    public static List<Test> getAllTests() {
        List<Test> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            Context context = (Context) new InitialContext().lookup("java:/comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/mysql");
            con = dataSource.getConnection();
            prepStmt = con.prepareStatement(queryAllTests);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(mapTest(rs));
            }
        } catch (NamingException | SQLException e) {
            //add logger
            e.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public static List<Test> getTestsLimitedSorted(int offset, int limit, String orderBy) {
        List<Test> results = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            /**
             * sanitize orderby
             */
            prepStmt = con.prepareStatement("SELECT id,name,subject,complexity,duration_sec,questionsNum FROM test ORDER BY " + orderBy + " LIMIT ?, ?;");
            prepStmt.setInt(1,offset-1);
            prepStmt.setInt(2,limit);
            System.out.println(prepStmt);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                results.add(mapTest(rs));
            }
        } catch (SQLException e) {
            //add logger
            e.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return results;
    }

    public static int insertNewTest(String name, String subject, String complexity,int durationSec) {
        int res = 0;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO test(name,subject,complexity,duration_sec) values(?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
            int k = 0;
            prepStmt.setString(++k,name);
            prepStmt.setString(++k,subject);
            prepStmt.setString(++k,complexity);
            prepStmt.setInt(++k,durationSec);
            prepStmt.executeUpdate();
            generatedKeys = prepStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                res = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException exception) {
            System.out.println("adding new test failed");
            System.out.println(exception.getMessage());
        } finally {
            DBUtil.closeAllInOrder(generatedKeys,prepStmt,con);
        }
        return res;
    }

    public static int getTestsNumber() {
        int res = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select count(*) as total from test;");
            rs.next();
            res = rs.getInt("total");
        } catch (SQLException e) {
            //add logger
            e.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs, stmt, con);
        }
        return res;
    }

    public static Test mapTest(ResultSet rs) {
        Test test = new Test();
        try {
            test.setId(rs.getInt(ID));
            test.setName(rs.getString(NAME));
            test.setSubject(rs.getString(SUBJECT));
            test.setComplexity(rs.getString(COMPLEXITY));
            test.setDuration(rs.getInt(DURATION));
            test.setQuestionsNum(rs.getInt(QUESTIONS_NUM));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return test;
    }
}
