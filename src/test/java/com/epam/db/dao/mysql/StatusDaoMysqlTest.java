package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.StatusDao;
import com.epam.db.model.Status;
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

public class StatusDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    StatusDao statusDao = DBSetup.STATUS_DAO_TEST;

    @BeforeClass
    public static void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt,DBSetup.STATUS_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

    @Test
    public void Should_have_two_statuses_active_and_inactive() throws DBException {
        List<Status> statusesExpected = new ArrayList<>();
        statusesExpected.add(new Status.Builder().setId(1).setName("Active").build());
        statusesExpected.add(new Status.Builder().setId(2).setName("Inactive").build());
        List<Status> rolesActual = statusDao.getAllRecords();
        Assert.assertEquals(statusesExpected,rolesActual);
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