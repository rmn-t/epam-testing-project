package com.epam.db.dao.mysql;

import com.epam.db.accessors.DBTestUtil;
import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.model.Role;
import com.epam.exceptions.DBException;
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
    static DatabaseAccessable dbAccessor = new DBTestUtil();
    RoleDaoMysql roleDaoMysql = new RoleDaoMysql(dbAccessor);

    @BeforeClass
    public static void beforeTest() throws SQLException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(SqlQueries.DROP_ROLES);
        stmt.addBatch(SqlQueries.CREATE_ROLES);
        stmt.addBatch(SqlQueries.ADD_ROLES);
        stmt.executeBatch();
    }

    @Test
    public void DBTableRolesShouldHaveTwoRecordsAdminAndUser() throws DBException {
        List<Role> rolesExpected = new ArrayList<>();
        rolesExpected.add(new Role.Builder().setId(1).setName("admin").build());
        rolesExpected.add(new Role.Builder().setId(2).setName("user").build());
        List<Role> rolesActual = roleDaoMysql.getAllRecords();
        Assert.assertEquals(rolesExpected,rolesActual);
    }
}