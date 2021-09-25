package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.ComplexityDao;
import com.epam.db.model.Complexity;
import com.epam.exceptions.DBException;
import org.junit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ComplexityDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    ComplexityDao complexityDao = new ComplexityDaoMysql(dbAccessor);

    @BeforeClass
    public static void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt,DBSetup.COMPLEXITY_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

    @Test
    public void Should_return_list_with_four_complexities_easy_medium_hard_advanced() throws DBException {
        List<Complexity> complexitiesExpected = new ArrayList<>();
        int id = 1;
        complexitiesExpected.add(new Complexity.Builder().setId(id++).setName("Easy").build());
        complexitiesExpected.add(new Complexity.Builder().setId(id++).setName("Medium").build());
        complexitiesExpected.add(new Complexity.Builder().setId(id++).setName("Hard").build());
        complexitiesExpected.add(new Complexity.Builder().setId(id).setName("Advanced").build());
        List<Complexity> rolesActual = complexityDao.getAllRecords();
        Assert.assertEquals(complexitiesExpected,rolesActual);
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