package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.RoleDao;
import com.epam.db.model.Role;
import com.epam.exceptions.DBException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    RoleDao roleDao = DBSetup.ROLE_DAO_TEST;

    @BeforeClass
    public static void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt,DBSetup.ROLE_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

    @Test
    public void Should_return_two_roles_admin_and_user() throws DBException {
        List<Role> rolesExpected = new ArrayList<>();
        rolesExpected.add(new Role.Builder().setId(1).setName("admin").build());
        rolesExpected.add(new Role.Builder().setId(2).setName("user").build());
        List<Role> rolesActual = roleDao.getAllRecords();
        Assert.assertEquals(rolesExpected,rolesActual);
    }

    @AfterClass
    public static void afterTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.ACTIVATE_FOREIGN_KEYS);
        stmt.executeBatch();
        dbAccessor.close(con);
    }
}