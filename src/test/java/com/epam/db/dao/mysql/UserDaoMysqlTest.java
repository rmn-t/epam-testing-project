package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.UserDao;
import com.epam.db.model.User;
import com.epam.exceptions.DBException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    UserDao userDao = DBSetup.USER_DAO_TEST;

    @Before
    public void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt, DBSetup.ROLE_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.STATUS_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.USER_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }
    
    @Test
    public void Should_correctly_return_user_by_username() throws DBException {
        User actualUser = userDao.getUserDetailsByUserName("amy");
        Assert.assertEquals(3,actualUser.getId());
        Assert.assertEquals("amy",actualUser.getUsername());
        Assert.assertEquals(671497443,actualUser.getSalt());
        Assert.assertEquals("Amy",actualUser.getFirstName());
        Assert.assertEquals("Stone",actualUser.getLastName());
        Assert.assertEquals("user",actualUser.getRole());
        Assert.assertEquals(2,actualUser.getRoleId());
        Assert.assertEquals("Banned",actualUser.getStatus());
        Assert.assertEquals(2,actualUser.getStatusId());
        Assert.assertEquals("c6bcb8fe9da0ff8fabc5bd8b192cd65b73d9eb270aaad8348a68fb9606a3aab09d5e5b296e99226a869f61b3d3281b294ff367f920bd259a47dabf0c9fc78151",actualUser.getPassword());
        System.out.println(actualUser);
    }
    
    @Test
    public void Should_correctly_delete_user_by_id() throws DBException {
        userDao.deleteUserById(3);
        User actualUser = userDao.getUserDetailsByUserName("amy");
        Assert.assertEquals(0,actualUser.getId());
        Assert.assertNull(actualUser.getUsername());
    }
    
    @Test
    public void Should_correctly_insert_a_new_user_with_user_role_and_active_status() throws DBException {
        userDao.addNewUser("bob","abc","Bob","Brown");
        User actualUser = userDao.getUserDetailsByUserName("bob");
        Assert.assertEquals(4,actualUser.getId());
        Assert.assertEquals("bob",actualUser.getUsername());
        Assert.assertEquals("bob",actualUser.getUsername());
        Assert.assertEquals("Bob",actualUser.getFirstName());
        Assert.assertEquals("Brown",actualUser.getLastName());
        Assert.assertEquals("user",actualUser.getRole());
        Assert.assertEquals(2,actualUser.getRoleId());
        Assert.assertEquals("Active",actualUser.getStatus());
        Assert.assertEquals(1,actualUser.getStatusId());
    }

    
    

    @After
    public void afterTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.ACTIVATE_FOREIGN_KEYS);
        stmt.executeBatch();
        dbAccessor.close(con);
    }
}