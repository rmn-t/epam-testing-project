package com.epam.db.dao;

import com.epam.db.DBUtil;
import com.epam.db.entities.User;
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

    public static User getUserDetailsByUserName(String username) {
        User user = new User();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,username,password,salt,status FROM user where username = ?;");
            prepStmt.setString(1,username);
            rs = prepStmt.executeQuery();
            rs.next();
            user = mapUser(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return user;
    }

    public static boolean validateCredentials(User userFromDb,String password,String userName) {
        if (!userName.equals(userFromDb.getUsername()) || userFromDb.getPassword() == null) {
            return false;
        }
        return userFromDb.getPassword().equals(Encrypt.getSecurePassword(password,userFromDb.getSalt()));
    }

    private static User mapUser(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setSalt(rs.getInt("salt"));
            user.setStatus(rs.getString("status"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

}
