package com.epam.db.dao.sql;

import com.epam.db.DBUtil;
import com.epam.db.model.User;
import com.epam.util.Encrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoSql {

    public UserDaoSql() {
    }

    /**
    public boolean checkCredentials(String username, String password) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT * FROM user WHERE username=?");
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
    */

    public static User getUserDetailsByUserName(String username) {
        User user = new User();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,username,role,password,salt,status FROM user where username = ?;");
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

    public static void updateUserById(int id, String password,String role, String status,int salt) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE user SET password = ?, role = ?, status = ?, salt = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++,password);
            prepStmt.setString(k++,role);
            prepStmt.setString(k++,status);
            prepStmt.setInt(k++,salt);
            prepStmt.setInt(k++,id);
            prepStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    public static List<User> getAllUsersLimitedSorted(int offset, int limit, String orderBy) {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,username,salt,role,status,password FROM user ORDER BY " + orderBy + " LIMIT ?, ?;");
            int k = 1;
            prepStmt.setInt(k++,offset);
            prepStmt.setInt(k++,limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
//                User u = new User();
//                u.setId(rs.getInt("id"));
//                u.setUsername(rs.getString("username"));
//                u.setRole(rs.getString("role"));
//                u.setStatus(rs.getString("status"));
//                u.setPassword(rs.getString("password"));
                users.add(mapUser(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return users;
    }

    public static int getNumOfUsers() {
        int res = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM user;");
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
            user.setRole(rs.getString("role"));
            user.setSalt(rs.getInt("salt"));
            user.setStatus(rs.getString("status"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

}
