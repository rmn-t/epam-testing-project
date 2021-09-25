package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.TestDao;
import com.epam.exceptions.DBException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDaoMysqlTest {
    static DatabaseAccessable dbAccessor = DBSetup.DB_UTIL;
    TestDao testDao = DBSetup.TEST_DAO_TEST;

    @Before
    public void beforeTest() throws SQLException, DBException {
        Connection con = dbAccessor.getConnection();
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.addBatch(DBSetup.DEACTIVATE_FOREIGN_KEYS);
        DBSetup.addStatementsToBatch(stmt, DBSetup.COMPLEXITY_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.SUBJECT_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.TESTS_RELATED);
        DBSetup.addStatementsToBatch(stmt, DBSetup.QUESTIONS_RELATED);
        stmt.executeBatch();
        dbAccessor.close(con);
    }

    @Test
    public void Should_return_test_with_id_two_and_subject_name_medicine_in_english() throws DBException {
        com.epam.db.model.Test expectedTest = new com.epam.db.model.Test.Builder().setId(2).setSubject("Medicine").build();
        com.epam.db.model.Test actualTest = testDao.getTestById(2, "en");
        Assert.assertEquals(expectedTest, actualTest);
        Assert.assertEquals(expectedTest.getSubject(), actualTest.getSubject());
    }

    @Test
    public void Should_return_test_with_id_two_and_subject_name_medicine_in_russian() throws DBException {
        com.epam.db.model.Test expectedTest = new com.epam.db.model.Test.Builder().setId(2).setSubject("Медицина").build();
        com.epam.db.model.Test actualTest = testDao.getTestById(2, "ru");
        Assert.assertEquals(expectedTest, actualTest);
        Assert.assertEquals(expectedTest.getSubject(), actualTest.getSubject());
    }

    @Test
    public void Should_delete_test_by_id_1_from_database() throws DBException {
        testDao.deleteTestById(1);
        Assert.assertEquals(0, testDao.getTestById(1, "en").getId());
    }

    @Test
    public void Should_successfully_update_test_name_subjectId_complexityId_duration_isActive() throws DBException {
        testDao.updateTestById(5, "abc", 2, 2, 30, false);
        com.epam.db.model.Test actual = testDao.getTestById(5, "en");
        Assert.assertEquals(5, actual.getId());
        Assert.assertEquals("abc", actual.getName());
        Assert.assertEquals(2, actual.getSubjectId());
        Assert.assertEquals(30, actual.getDuration());
        Assert.assertEquals(2, actual.getComplexityId());
        Assert.assertFalse(actual.getIsActive());
    }

    @Test
    public void Should_successfully_deactivate_test() throws SQLException, DBException {
        testDao.deactivateTestById(dbAccessor.getConnection(), 2);
        Assert.assertFalse(testDao.getTestById(2, "en").getIsActive());
    }

    @Test
    public void Should_return_one_test_by_subjectId_one_and_param_active() throws DBException {
        int actual = testDao.getRecordsNumBySubjectId(1, "active");
        Assert.assertEquals(1, actual);
    }

    @Test
    public void Should_return_one_test_by_subjectId_one_and_param_all() throws DBException {
        int actual = testDao.getRecordsNumBySubjectId(1, "all");
        Assert.assertEquals(2, actual);
    }

    @Test
    public void Should_return_one_test_by_subjectId_one_and_param_inactive() throws DBException {
        int actual = testDao.getRecordsNumBySubjectId(1, "inactive");
        Assert.assertEquals(1, actual);
    }

    @Test
    public void Should_successfully_insert_a_new_test_as_inactive_test() throws DBException {
        testDao.insertNewTest("new", 1, 1, 150);
        com.epam.db.model.Test test = testDao.getTestById(6, "en");
        Assert.assertEquals(6, test.getId());
        Assert.assertEquals("new", test.getName());
        Assert.assertEquals(1, test.getSubjectId());
        Assert.assertEquals(1, test.getComplexityId());
        Assert.assertEquals(150, test.getDuration());
        Assert.assertFalse(test.getIsActive());
    }

    private void additionalTestSetupForSorting() throws DBException {
        testDao.updateTestById(1,"a",3,1,10,true);
        testDao.updateTestById(2,"b",3,2,20,true);
        testDao.updateTestById(3,"c",3,3,30,false);
        testDao.updateTestById(4,"d",3,4,40,true);
        testDao.updateTestById(5,"e",3,3,50,true);
    }

    @Test
    public void Should_return_list_with_four_active_tests_sorted_by_name_a_to_z() throws DBException {
        additionalTestSetupForSorting();
        List<com.epam.db.model.Test> actualTests = testDao.getTestsLimitedSorted(1,10,"name ASC",3,"active","en");
        for (com.epam.db.model.Test test : actualTests) {
            System.out.println(test.getId());
        }
        List<com.epam.db.model.Test> expected = new ArrayList<>();
        expected.add(new com.epam.db.model.Test.Builder().setId(1).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(2).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(4).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(5).build());
        for (com.epam.db.model.Test test : expected) {
            System.out.println(test.getId());
        }
        Assert.assertEquals(expected,actualTests);
    }

    @Test
    public void Should_return_list_with_five_tests_sorted_by_name_a_to_z() throws DBException {
        additionalTestSetupForSorting();
        List<com.epam.db.model.Test> actualTests = testDao.getTestsLimitedSorted(1,10,"name ASC",3,"all","en");
        for (com.epam.db.model.Test test : actualTests) {
            System.out.println(test.getId());
        }
        List<com.epam.db.model.Test> expected = new ArrayList<>();
        expected.add(new com.epam.db.model.Test.Builder().setId(1).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(2).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(3).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(4).build());
        expected.add(new com.epam.db.model.Test.Builder().setId(5).build());
        for (com.epam.db.model.Test test : expected) {
            System.out.println(test.getId());
        }
        Assert.assertEquals(expected,actualTests);
    }

    @Test
    public void Should_return_list_with_one_inactive_tests_sorted_by_name_a_to_z() throws DBException {
        additionalTestSetupForSorting();
        List<com.epam.db.model.Test> actualTests = testDao.getTestsLimitedSorted(1,10,"name ASC",3,"inactive","en");
        for (com.epam.db.model.Test test : actualTests) {
            System.out.println(test.getId());
        }
        List<com.epam.db.model.Test> expected = new ArrayList<>();
        expected.add(new com.epam.db.model.Test.Builder().setId(3).build());
        for (com.epam.db.model.Test test : expected) {
            System.out.println(test.getId());
        }
        Assert.assertEquals(expected,actualTests);
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