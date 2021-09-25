package com.epam.db.dao.mysql;

import com.epam.db.accessors.DBTestUtil;
import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.*;

import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {
    public static final DatabaseAccessable DB_UTIL = new DBTestUtil();
    public static final AnswerDao ANSWER_DAO_TEST = new AnswerDaoMysql(DB_UTIL);
    public static final ComplexityDao COMPLEXITY_DAO_TEST = new ComplexityDaoMysql(DB_UTIL);
    public static final PassedTestsDao PASSED_TESTS_DAO_TEST = new PassedTestsDaoMysql(DB_UTIL);
    public static final QuestionDao QUESTION_DAO_TEST = new QuestionDaoMysql(DB_UTIL);
    public static final RoleDao ROLE_DAO_TEST = new RoleDaoMysql(DB_UTIL);
    public static final StatusDao STATUS_DAO_TEST = new StatusDaoMysql(DB_UTIL);
    public static final SubjectDao SUBJECT_DAO_TEST = new SubjectDaoMysql(DB_UTIL);
    public static final TestDao TEST_DAO_TEST = new TestDaoMysql(DB_UTIL);
    public static final UserDao USER_DAO_TEST = new UserDaoMysql(DB_UTIL);

    public static void addStatementsToBatch(Statement stmt, String[] queries) throws SQLException {
        for (String query : queries) {
            stmt.addBatch(query);
        }
//        for (int i = 0; i < queries.length; i++) {
//            stmt.addBatch(queries[i]);
//        }
    }

    public static String DEACTIVATE_FOREIGN_KEYS = "SET FOREIGN_KEY_CHECKS = 0;";
    public static String ACTIVATE_FOREIGN_KEYS = "SET FOREIGN_KEY_CHECKS = 1;";


    public static String DROP_ROLES = "DROP TABLE IF EXISTS role;";
    public static String CREATE_ROLES = "create table role (id int AUTO_INCREMENT PRIMARY KEY,name VARCHAR(20)) DEFAULT CHARSET=utf8;";
    public static String ADD_ROLES = "INSERT INTO role VALUES (id,'admin'),(id,'user');";
    public static String[] ROLE_RELATED = {DROP_ROLES, CREATE_ROLES, ADD_ROLES};

    public static String DROP_COMPLEXITIES = "DROP TABLE IF EXISTS complexity;";
    public static String CREATE_COMPLEXITIES = "create table complexity (\n" +
            "\tid int AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
            "    name VARCHAR(25) NOT NULL,\n" +
            "    scale int NOT NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String ADD_COMPLEXITIES = "INSERT INTO complexity VALUES (id,'Easy',1),(id,'Medium',2),(id,'Hard',3),(id,'Advanced',4);";
    public static String[] COMPLEXITY_RELATED = {DROP_COMPLEXITIES, CREATE_COMPLEXITIES, ADD_COMPLEXITIES};

    public static String DROP_STATUSES = "DROP TABLE IF EXISTS status;";
    public static String CREATE_STATUSES = "create table status (\n" +
            "\tid int AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
            "    name VARCHAR(20) NOT NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String ADD_STATUSES = "INSERT INTO status VALUES (id,'Active'),(id,'Banned');";
    public static String[] STATUS_RELATED = {DROP_STATUSES, CREATE_STATUSES, ADD_STATUSES};

    public static String DROP_SUBJECTS = "DROP TABLE IF EXISTS subject;";
    public static String CREATE_SUBJECTS = "create table subject (id int AUTO_INCREMENT PRIMARY KEY NOT NULL,name_en VARCHAR(50) NOT NULL,name_ru VARCHAR(50) NOT NULL) DEFAULT CHARSET=utf8;";
    public static String ADD_SUBJECTS = "INSERT INTO subject VALUES (id,'Marketing','Маркетинг'),(id,'Medicine','Медицина'),(id,'Finance','Финансы'),(id,'Engineering','Инженерия');";
    public static String[] SUBJECT_RELATED = {DROP_SUBJECTS, CREATE_SUBJECTS, ADD_SUBJECTS};

    public static String DROP_TESTS = "DROP TABLE IF EXISTS test;";
    public static String CREATE_TESTS = "CREATE TABLE test(\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    name VARCHAR(150) NOT NULL,\n" +
            "    subject_id INT DEFAULT 1,\n" +
            "    complexity_id INT DEFAULT 1,\n" +
            "    duration_sec INT NOT NULL,\n" +
            "    is_active BOOLEAN NOT NULL,\n" +
            "\tFOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE SET NULL,\n" +
            "    FOREIGN KEY (complexity_id) REFERENCES complexity(id) ON DELETE SET NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String ADD_TESTS = "INSERT INTO test VALUES " +
            "(id,'Test # 1',1,3,2651,TRUE)," +
            "(id,'Тест # 2',2,1,2804,TRUE)," +
            "(id,'Test # 3',3,2,667,TRUE)," +
            "(id,'Тест # 4',4,3,2432,TRUE)," +
            "(id,'Test # 5',1,1,778,FALSE);";
    public static String[] TESTS_RELATED = {DROP_TESTS, CREATE_TESTS, ADD_TESTS};

    public static String DROP_QUESTION = "DROP TABLE IF EXISTS question;";
    public static String CREATE_QUESTIONS = "CREATE TABLE question(\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    text VARCHAR(400) NOT NULL,\n" +
            "    test_id INT NOT NULL,\n" +
            "    FOREIGN KEY (test_id) REFERENCES test(id) ON DELETE CASCADE\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String ADD_QUESTIONS = "INSERT INTO question VALUES (1,'Question, id #1 for test: Test # 1',1),(2,'Question, id #2 for test: Test # 1',1),(3,'Question, id #3 for test: Test # 1',1),(4,'Question, id #4 for test: Test # 1',1),(5,'Question, id #5 for test: Test # 1',1),(6,'Question, id #6 for test: Тест # 2',2),(7,'Question, id #7 for test: Тест # 2',2),(8,'Question, id #8 for test: Тест # 2',2),(9,'Question, id #9 for test: Тест # 2',2),(10,'Question, id #10 for test: Тест # 2',2),(11,'Question, id #11 for test: Тест # 2',2),(12,'Question, id #12 for test: Тест # 2',2),(13,'Question, id #13 for test: Тест # 2',2),(14,'Question, id #14 for test: Тест # 2',2),(15,'Question, id #15 for test: Тест # 2',2),(16,'Question, id #16 for test: Test # 3',3),(17,'Question, id #17 for test: Test # 3',3),(18,'Question, id #18 for test: Test # 3',3),(19,'Question, id #19 for test: Test # 3',3),(20,'Question, id #20 for test: Test # 3',3),(21,'Question, id #21 for test: Test # 3',3),(22,'Question, id #22 for test: Test # 3',3),(23,'Question, id #23 for test: Test # 3',3),(24,'Question, id #24 for test: Test # 3',3),(25,'Question, id #25 for test: Test # 3',3),(26,'Question, id #26 for test: Тест # 4',4),(27,'Question, id #27 for test: Тест # 4',4),(28,'Question, id #28 for test: Тест # 4',4),(29,'Question, id #29 for test: Тест # 4',4),(30,'Question, id #30 for test: Тест # 4',4),(31,'Question, id #31 for test: Тест # 4',4),(32,'Question, id #32 for test: Тест # 4',4),(33,'Question, id #33 for test: Тест # 4',4),(34,'Question, id #34 for test: Тест # 4',4),(35,'Question, id #35 for test: Тест # 4',4),(36,'Question, id #36 for test: Test # 5',5),(37,'Question, id #37 for test: Test # 5',5),(38,'Question, id #38 for test: Test # 5',5),(39,'Question, id #39 for test: Test # 5',5),(40,'Question, id #40 for test: Test # 5',5),(41,'Question, id #41 for test: Test # 5',5),(42,'Question, id #42 for test: Test # 5',5),(43,'Question, id #43 for test: Test # 5',5),(44,'Question, id #44 for test: Test # 5',5),(45,'Question, id #45 for test: Test # 5',5),(46,'Question, id #46 for test: Test # 5',5),(47,'Question, id #47 for test: Test # 5',5),(48,'Question, id #48 for test: Test # 5',5),(49,'Question, id #49 for test: Test # 5',5),(50,'Question, id #50 for test: Test # 5',5);";
    public static String[] QUESTIONS_RELATED = {DROP_QUESTION, CREATE_QUESTIONS, ADD_QUESTIONS};

}
