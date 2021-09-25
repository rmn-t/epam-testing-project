package com.epam.db.dao.mysql;

import com.epam.db.accessors.DBTestUtil;
import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.model.Complexity;
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

public class ComplexityDaoMysqlTest {
    static DatabaseAccessable dbAccessor = new DBTestUtil();
    ComplexityDaoMysql complexityDaoMysql = new ComplexityDaoMysql(dbAccessor);

    @BeforeClass
    public static void beforeTest() throws SQLException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(SqlQueries.DROP_COMPLEXITIES);
        stmt.addBatch(SqlQueries.CREATE_COMPLEXITIES);
        stmt.addBatch(SqlQueries.ADD_COMPLEXITIES);
        stmt.executeBatch();
    }

    @Test
    public void ShouldReturnAlistWithFourComplexitiesEasyMediumHardAdvanced() throws DBException {
        List<Complexity> complexitiesExpected = new ArrayList<>();
        int id = 1;
        complexitiesExpected.add(new Complexity.Builder().setId(id++).setName("Easy").build());
        complexitiesExpected.add(new Complexity.Builder().setId(id++).setName("Medium").build());
        complexitiesExpected.add(new Complexity.Builder().setId(id++).setName("Hard").build());
        complexitiesExpected.add(new Complexity.Builder().setId(id).setName("Advanced").build());
        List<Complexity> rolesActual = complexityDaoMysql.getAllRecords();
        Assert.assertEquals(complexitiesExpected,rolesActual);
    }


}