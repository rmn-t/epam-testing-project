package com.epam.db.dao;

import com.epam.db.DBUtil;
import com.epam.db.entities.PassedTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassedTestsDao {
    public static void insertPassedTest(int testId, int userId, double grade,int timeSpent) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO passed_tests(user_id,test_id,grade,time_spent) VALUES(?,?,?,?);");
            int k = 1;
            prepStmt.setInt(k++,userId);
            prepStmt.setInt(k++,testId);
            prepStmt.setDouble(k++,grade);
            prepStmt.setInt(k++,timeSpent);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    public static List<PassedTest> getPassedTestsByUserIdOrderedLimited(int id,int offset, int limit, String orderBy) {
        List<PassedTest> passedTests = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,user_id,test_id,grade,time_spent,date FROM passed_tests WHERE user_id = ? ORDER BY " + orderBy + " LIMIT ?,?;");
            int k = 1;
            prepStmt.setInt(k++,id);
            prepStmt.setInt(k++,offset-1);
            prepStmt.setInt(k++,limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                passedTests.add(mapPassedTest(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return passedTests;
    }

    public static int getPassedTestsNumberByUserId(int userId) {
        int res = 0;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT COUNT(*) as total FROM passed_tests WHERE user_id = ?;");
            prepStmt.setInt(1,userId);
            rs = prepStmt.executeQuery();
            rs.next();
            res = rs.getInt("total");
        } catch (SQLException e) {
            //add logger
            e.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
        }
        return res;
    }

    public static PassedTest mapPassedTest(ResultSet rs) {
        PassedTest pt = new PassedTest();
        try {
            pt.setId(rs.getInt("id"));
            pt.setTestId(rs.getInt("test_id"));
            pt.setGrade(rs.getDouble("grade"));
            pt.setTimeSpent(rs.getInt("time_spent"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return pt;
    }

}
