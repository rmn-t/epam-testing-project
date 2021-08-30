package com.epam.db.dao;

import com.epam.db.DBUtil;
import com.epam.util.Encrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private String query = "SELECT * FROM user WHERE username=?";

    public UserDao() {
    }

    public boolean checkCredentials(String username, String password) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1,username);
            rs = prepStmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
            if (!rs.getString("password").equals(Encrypt.getSecurePassword(password,rs.getInt("salt")))) {
                return false;
            }
        } catch (SQLException e) {
            //add logger
            e.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return true;
    }

}
