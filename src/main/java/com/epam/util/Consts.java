package com.epam.util;

import com.epam.db.accessors.DBProdUtil;
import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.*;
import com.epam.db.dao.mysql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * A constants class, contains various types of the values. Currently it is mostly used to process sorting requests on
 * various pages.
 */
public class Consts {
    public final Logger logger = LoggerFactory.getLogger(Consts.class);

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SUBJECT = "subject";
    public static final String COMPLEXITY = "scale";
    public static final String DURATION = "duration_sec";
    public static final String COUNT = "questionsNum";
    public static final String TESTS_DEFAULT_SORT = "name ASC";

    private static final String[] VALID_COLUMNS_FOR_TEST_ORDER_BY = {
        ID + " ASC", NAME + " ASC", SUBJECT  + " ASC", COMPLEXITY + " ASC", DURATION + " ASC", COUNT + " ASC",
        ID + " DESC", NAME + " DESC", SUBJECT  + " DESC", COMPLEXITY + " DESC", DURATION + " DESC", COUNT + " DESC"
    };

    public static List<String> getVALID_COLUMNS_FOR_TEST_ORDER_BY() {
        return Arrays.asList(VALID_COLUMNS_FOR_TEST_ORDER_BY);
    }

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String USER_DEFAULT_SORT = "username ASC";

    private static final String[] VALID_COLUMNS_FOR_USER_ORDER_BY = {
        ID + " ASC", USERNAME + " ASC", FIRST_NAME + " ASC", LAST_NAME  + " ASC",
        ID + " DESC", USERNAME + " DESC", FIRST_NAME + " DESC", LAST_NAME  + " DESC"
    };

    public static List<String> getVALID_COLUMNS_FOR_USER_ORDER_BY() {
        return Arrays.asList(VALID_COLUMNS_FOR_USER_ORDER_BY);
    }

    public static final String QUESTION_NUM = "question_num";
    public static final String GRADE = "grade";
    public static final String DATE = "date";
    public static final String TEST_NAME = "testName";
    public static final String PASSED_TESTS_DEFAULT_SORT = "testName ASC";

    private static final String[] VALID_COLUMNS_FOR_PASSED_TESTS_ORDER_BY = {
        QUESTION_NUM + " ASC", DATE + " ASC", GRADE + " ASC", TEST_NAME  + " ASC",
        QUESTION_NUM + " DESC", DATE + " DESC", GRADE + " DESC", TEST_NAME  + " DESC",
    };

    public static List<String> getVALID_COLUMNS_FOR_PASSED_TESTS_ORDER_BY() {
        return Arrays.asList(VALID_COLUMNS_FOR_PASSED_TESTS_ORDER_BY);
    }

    private static final Integer[] VALID_PER_PAGE_VALUES = { 10, 25 ,50 };

    public static List<Integer> getValidPerPageValues() {
        return Arrays.asList(VALID_PER_PAGE_VALUES);
    }

    private static final DatabaseAccessable prodDbAccessor = new DBProdUtil();

    public static final AnswerDao ANSWER_DAO = new AnswerDaoMysql(prodDbAccessor);
    public static final ComplexityDao COMPLEXITY_DAO = new ComplexityDaoMysql(prodDbAccessor);
    public static final PassedTestsDao PASSED_TESTS_DAO = new PassedTestsDaoMysql(prodDbAccessor);
    public static final RoleDao ROLE_DAO = new RoleDaoMysql(prodDbAccessor);
    public static final StatusDao STATUS_DAO = new StatusDaoMysql(prodDbAccessor);
    public static final SubjectDao SUBJECT_DAO = new SubjectDaoMysql(prodDbAccessor);
    public static final TestDao TEST_DAO = new TestDaoMysql(prodDbAccessor);
    public static final UserDao USER_DAO = new UserDaoMysql(prodDbAccessor);
    public static final QuestionDao QUESTION_DAO = new QuestionDaoMysql(prodDbAccessor,TEST_DAO,ANSWER_DAO);

    public static final String CURRENT_USER = "currentUser";


}
