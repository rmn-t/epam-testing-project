package com.epam.db.dao.sql;

import com.epam.db.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.UserDao;
import com.epam.db.model.User;
import com.epam.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoSql implements UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDaoSql.class);

    public UserDaoSql() {
    }

    public User getUserDetailsByUserName(String username) throws DBException {
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
            user = new User.Builder()
                    .setId(rs.getInt("id"))
                    .setUsername(rs.getString("username"))
                    .setRole(rs.getString("role"))
                    .setStatus(rs.getString("status"))
                    .setPassword(rs.getString("password"))
                    .setSalt(rs.getInt("salt"))
                    .build();
            logger.info("Successfully obtained user by username, id is {}.",user.getId());
        } catch (SQLException e) {
            logger.error("Failed to obtain user by username : {}.",user,e);
            throw new DBException("Failed to obtain user by username",e);
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return user;
    }

    public void updateUserById(int id, String password,String role, String status,int salt) throws DBException {
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
            logger.info("Successfully updated user info by id {}.",id);
        } catch (SQLException e) {
            logger.error("Failed to update user by id {}.",id,e);
            throw new DBException("Failed to update user by id.",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    public List<User> getAllUsersLimitedSorted(int offset, int limit, String orderBy) throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,username,salt,role,status,password FROM user ORDER BY " + orderBy + " LIMIT ?, ?;");
            int k = 1;
            prepStmt.setInt(k++,offset-1);
            prepStmt.setInt(k++,limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                users.add(new User.Builder()
                        .setId(rs.getInt("id"))
                        .setUsername(rs.getString("username"))
                        .setRole(rs.getString("role"))
                        .setStatus(rs.getString("status"))
                        .setPassword(rs.getString("password"))
                        .build());
            }
            logger.info("Successfully obtained {} users limited and sorted.",users.size());
        } catch (SQLException e) {
            logger.error("Failed to obtain users limited and sorted order by {}, limit {},{}.",orderBy,offset,limit,e);
            throw new DBException("Failed to obtain users limited and sorted.",e);
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return users;
    }

    public int getNumOfUsers() throws DBException {
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
            logger.info("Successfully obtained total number of users : {}.",res);
        } catch (SQLException e) {
            logger.error("Failed to obtain the total number of users.",e);
            throw new DBException("Failed to obtain the total number of users.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, stmt, con);
        }
        return res;
    }

    public boolean validateCredentials(User userFromDb,String password,String userName) {
        if (!userName.equals(userFromDb.getUsername()) || userFromDb.getPassword() == null) {
            logger.info("Failed to validate credentials, either user name is incorrect or password is null.");
            return false;
        }
        logger.info(String.valueOf(userFromDb.getPassword().equals(Encrypt.getSecurePassword(password,userFromDb.getSalt()))));
        logger.info(Encrypt.getSecurePassword(password,userFromDb.getSalt()));
        logger.info(userFromDb.getPassword());
        return userFromDb.getPassword().equals(Encrypt.getSecurePassword(password,userFromDb.getSalt()));
    }

}
