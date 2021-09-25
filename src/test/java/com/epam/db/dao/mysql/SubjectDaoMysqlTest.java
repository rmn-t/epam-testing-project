package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.SubjectDao;
import com.epam.db.model.Subject;
import com.epam.exceptions.DBException;
import org.junit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    SubjectDao subjectDao = DBSetup.SUBJECT_DAO_TEST;

    @BeforeClass
    public static void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt,DBSetup.SUBJECT_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

    @Test
    public void Should_have_four_subjects_marketing_medicine_finance_engineering_sorted_a_to_z_in_english() throws DBException {
        List<Subject> subjectsExpected = new ArrayList<>();
        Subject s1 = new Subject.Builder().setId(1).setName("Marketing").build();
        Subject s2 = new Subject.Builder().setId(2).setName("Medicine").build();
        Subject s3 = new Subject.Builder().setId(3).setName("Finance").build();
        Subject s4 = new Subject.Builder().setId(4).setName("Engineering").build();
        subjectsExpected.add(s4);
        subjectsExpected.add(s3);
        subjectsExpected.add(s1);
        subjectsExpected.add(s2);
        List<Subject> subjectsActual = subjectDao.getAllRecords("en");
        Assert.assertEquals(subjectsExpected,subjectsActual);
    }

    @Test
    public void Should_have_four_subjects_marketing_medicine_finance_engineering_sorted_a_to_z_in_russian() throws DBException {
        List<Subject> subjectsExpected = new ArrayList<>();
        Subject s1 = new Subject.Builder().setId(1).setName("Маркетинг").build();
        Subject s2 = new Subject.Builder().setId(2).setName("Медицина").build();
        Subject s3 = new Subject.Builder().setId(3).setName("Финансы").build();
        Subject s4 = new Subject.Builder().setId(4).setName("Инженерия").build();
        subjectsExpected.add(s4);
        subjectsExpected.add(s1);
        subjectsExpected.add(s2);
        subjectsExpected.add(s3);
        List<Subject> subjectsActual = subjectDao.getAllRecords("ru");
        Assert.assertEquals(subjectsExpected,subjectsActual);
    }

    @Test
    public void Should_have_four_total_subjects() throws DBException {
        int expectedCount = 4;
        int actualCount = subjectDao.getRecordsNum();
        Assert.assertEquals(expectedCount,actualCount);
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