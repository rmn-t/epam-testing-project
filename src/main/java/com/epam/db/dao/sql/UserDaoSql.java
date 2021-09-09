package com.epam.db.dao.sql;

import com.epam.db.DBUtil;
import com.epam.db.dao.UserDao;
import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import com.epam.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
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
    private final int DEFAULT_STATUS_ID = 1;
    private final int DEFAULT_ROLE_ID = 2;
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
            prepStmt = con.prepareStatement("" +
                    "SELECT user.id as id,username,role.name as role,password,salt,status.name as status,first_name,last_name,status_id,role_id FROM user " +
                    "INNER JOIN status ON user.status_id = status.id " +
                    "INNER JOIN role ON user.role_id = role.id " +
                    "where username = ?;");
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
                    .setStatusId(rs.getInt("status_id"))
                    .setRoleId(rs.getInt("role_id"))
                    .setFirstName(rs.getString(FIRST_NAME))
                    .setLastName(rs.getString(LAST_NAME))
                    .build();
            logger.debug("Successfully obtained user by username, id is {}.",user.getId());
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
            prepStmt.setInt(k++,DEFAULT_ROLE_ID);
            prepStmt.setInt(k++,DEFAULT_STATUS_ID);
            id = prepStmt.executeUpdate();
            logger.debug("Inserted new user with username {}.",userName);
        } catch (SQLException e) {
            logger.error("Couldn't insert a new user with username: {}.",userName,e);
            throw new DBException("Couldn't insert a new user",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
        return id;
    }

    public void updateUserById(int id, String password,int roleId, int statusId,String firstName,String lastName,int salt) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("UPDATE user SET password = ?, role_id = ?, status_id = ?, salt = ?, first_name = ?, last_name = ? WHERE id = ?;");
            int k = 1;
            prepStmt.setString(k++,password);
            prepStmt.setInt(k++,roleId);
            prepStmt.setInt(k++,statusId);
            prepStmt.setInt(k++,salt);
            prepStmt.setString(k++,firstName);
            prepStmt.setString(k++,lastName);
            prepStmt.setInt(k++,id);
            prepStmt.executeUpdate();
            logger.debug("Successfully updated user info by id {}.",id);
        } catch (SQLException e) {
            logger.error("Failed to update user by id {}.",id,e);
            throw new DBException("Failed to update user by id.",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    @Override
    public void deleteUserById(int id) throws DBException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("DELETE FROM user WHERE id = ?;");
            prepStmt.setInt(1,id);
            prepStmt.executeUpdate();
            logger.info("User by id {} was deleted.",id);
        } catch (SQLException e) {
            logger.error("Couldn't delete user by ID is {}.",id,e);
            throw new DBException("Couldn't delete user by id.",e);
        } finally {
            DBUtil.closeAllInOrder(prepStmt,con);
        }
    }

    public List<User> getRecordsLimitedSortedFiltered(int offset, int limit, String orderBy, int statusId) throws DBException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String whereStatusId = statusId == 0 ? "" : " WHERE status_id = " + statusId;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("" +
                "SELECT user.id as id,username,salt,first_name,last_name,role.name as role,status.name as status,role_id,status_id,password FROM user " +
                    "INNER JOIN status ON user.status_id = status.id " +
                    "INNER JOIN role ON user.role_id = role.id " + whereStatusId +
                    " ORDER BY " + orderBy + " LIMIT ?, ?;");
            int k = 1;
            prepStmt.setInt(k++,offset-1);
            prepStmt.setInt(k++,limit);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                users.add(new User.Builder()
                        .setId(rs.getInt(ID))
                        .setUsername(rs.getString(USERNAME))
                        .setFirstName(rs.getString(FIRST_NAME))
                        .setLastName(rs.getString(LAST_NAME))
                        .setRole(rs.getString(ROLE))
                        .setStatus(rs.getString(STATUS))
                        .setRoleId(rs.getInt("role_id"))
                        .setStatusId(rs.getInt("status_id"))
                        .setPassword(rs.getString(PASSWORD))
                        .setSalt(rs.getInt("salt"))
                        .build());
            }
            logger.debug("Successfully obtained {} users limited and sorted.",users.size());
        } catch (SQLException e) {
            logger.error("Failed to obtain users limited and sorted order by {}, limit {},{}.",orderBy,offset,limit,e);
            throw new DBException("Failed to obtain users limited and sorted.",e);
        } finally {
            DBUtil.closeAllInOrder(rs,prepStmt,con);
        }
        return users;
    }

    public int getRecordsNumByStatusId(int statusId) throws DBException {
        int res;
        String whereStatusId = statusId == 0 ? "" : " WHERE status_id = " + statusId;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            prepStmt = con.prepareStatement("SELECT COUNT(*) AS total FROM user " + whereStatusId + ";");
            rs = prepStmt.executeQuery();
            rs.next();
            res = rs.getInt("total");
            logger.info("Total number of users in user table is {}.",res);
        } catch (SQLException e) {
            logger.error("Failed to get the total users number.",e);
            throw new DBException("Failed to get the total users number.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, prepStmt, con);
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
