package com.epam.db.dao.sql;

import com.epam.exceptions.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.UserDao;
import com.epam.db.model.User;
import com.epam.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoSql implements UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDaoSql.class);
    private final String ID = "id";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String SALT = "salt";
    private final String ROLE = "role";
    private final String STATUS = "status";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String DEFAULT_STATUS = "active";
    private final String DEFAULT_ROLE = "user";
    private final String[] VALID_COLUMNS_FOR_ORDER_BY = {ID, USERNAME, PASSWORD, SALT, ROLE, STATUS};

    public UserDaoSql() {
    }

    public User getUserDetailsByUserName(String username) throws DBException {
        User user = new User();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT id,username,role,password,salt,status,first_name,last_name FROM user where username = ?;");
            prepStmt.setString(1,username);
            rs = prepStmt.executeQuery();
            rs.next();
            user = new User.Builder()
                    .setId(rs.getInt(ID))
                    .setUsername(rs.getString(USERNAME))
                    .setRole(rs.getString(ROLE))
                    .setStatus(rs.getString(STATUS))
                    .setPassword(rs.getString(PASSWORD))
                    .setSalt(rs.getInt(SALT))
                    .setFirstName(rs.getString(FIRST_NAME))
                    .setLastName(rs.getString(LAST_NAME))
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

    public int addNewUser(String userName,String password,String firstName,String lastName) throws DBException {
        int id = 0;
        Connection con = null;
        PreparedStatement prepStmt = null;
        int salt = Encrypt.generateSalt();
        String securePassword = Encrypt.getSecurePassword(password,salt);
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("INSERT INTO user VALUES(id,?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prepStmt.setString(k++,userName);
            prepStmt.setString(k++,securePassword);
            prepStmt.setInt(k++,salt);
            prepStmt.setString(k++,firstName);
            prepStmt.setString(k++,lastName);
            prepStmt.setString(k++,DEFAULT_ROLE);
            prepStmt.setString(k++,DEFAULT_STATUS);
            id = prepStmt.executeUpdate();
            logger.info("Inserted new user with username {}.",userName);
        } catch (SQLException e) {
            logger.error("Couldn't insert a new user with username: {}.",userName,e);
            throw new DBException("Couldn't insert a new user",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
        return id;
    }

    public void updateUserById(int id, String password,String role, String status,String firstName,String lastName,int salt) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE user SET password = ?, role = ?, status = ?, salt = ?, first_name = ?, last_name = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++,password);
            prepStmt.setString(k++,role);
            prepStmt.setString(k++,status);
            prepStmt.setInt(k++,salt);
            prepStmt.setString(k++,firstName);
            prepStmt.setString(k++,lastName);
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
            orderBy = Arrays.asList(VALID_COLUMNS_FOR_ORDER_BY).contains(orderBy) ? orderBy : VALID_COLUMNS_FOR_ORDER_BY[1] + " ASC";
            prepStmt = con.prepareStatement("SELECT id,username,salt,role,status,password FROM user ORDER BY " + orderBy + " LIMIT ?, ?;");
            logger.debug("SELECT id,username,salt,role,status,password FROM user ORDER BY " + orderBy + " LIMIT ?, ?;");
            int k = 1;
            prepStmt.setInt(k++,offset-1);
            prepStmt.setInt(k++,limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                users.add(new User.Builder()
                        .setId(rs.getInt(ID))
                        .setUsername(rs.getString(USERNAME))
                        .setRole(rs.getString(ROLE))
                        .setStatus(rs.getString(STATUS))
                        .setPassword(rs.getString(PASSWORD))
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
        if (userFromDb == null || !userName.equals(userFromDb.getUsername())) {
            logger.info("Failed to validate credentials, either user name is incorrect or password is null.");
            return false;
        }
        return userFromDb.getPassword().equals(Encrypt.getSecurePassword(password,userFromDb.getSalt()));
    }

}
