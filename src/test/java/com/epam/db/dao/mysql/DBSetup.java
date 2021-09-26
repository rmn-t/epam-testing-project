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
    public static final RoleDao ROLE_DAO_TEST = new RoleDaoMysql(DB_UTIL);
    public static final StatusDao STATUS_DAO_TEST = new StatusDaoMysql(DB_UTIL);
    public static final SubjectDao SUBJECT_DAO_TEST = new SubjectDaoMysql(DB_UTIL);
    public static final TestDao TEST_DAO_TEST = new TestDaoMysql(DB_UTIL);
    public static final UserDao USER_DAO_TEST = new UserDaoMysql(DB_UTIL);
    public static final QuestionDao QUESTION_DAO_TEST = new QuestionDaoMysql(DB_UTIL, TEST_DAO_TEST, ANSWER_DAO_TEST);

    public static void addStatementsToBatch(Statement stmt, String[] queries) throws SQLException {
        for (String query : queries) {
            stmt.addBatch(query);
        }
    }

    public static String DEACTIVATE_FOREIGN_KEYS = "SET FOREIGN_KEY_CHECKS = 0;";
    public static String ACTIVATE_FOREIGN_KEYS = "SET FOREIGN_KEY_CHECKS = 1;";


    public static String DROP_ROLE = "DROP TABLE IF EXISTS role;";
    public static String CREATE_ROLE = "create table role (id int AUTO_INCREMENT PRIMARY KEY,name VARCHAR(20)) DEFAULT CHARSET=utf8;";
    public static String INSERT_ROLE = "INSERT INTO role VALUES (id,'admin'),(id,'user');";
    public static String[] ROLE_RELATED = {DROP_ROLE, CREATE_ROLE, INSERT_ROLE};

    public static String DROP_COMPLEXITY = "DROP TABLE IF EXISTS complexity;";
    public static String CREATE_COMPLEXITY = "create table complexity (\n" +
            "\tid int AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
            "    name VARCHAR(25) NOT NULL,\n" +
            "    scale int NOT NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String INSERT_COMPLEXITY = "INSERT INTO complexity VALUES (id,'Easy',1),(id,'Medium',2),(id,'Hard',3),(id,'Advanced',4);";
    public static String[] COMPLEXITY_RELATED = {DROP_COMPLEXITY, CREATE_COMPLEXITY, INSERT_COMPLEXITY};

    public static String DROP_STATUS = "DROP TABLE IF EXISTS status;";
    public static String CREATE_STATUS = "create table status (\n" +
            "\tid int AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
            "    name VARCHAR(20) NOT NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String INSERT_STATUS = "INSERT INTO status VALUES (id,'Active'),(id,'Banned');";
    public static String[] STATUS_RELATED = {DROP_STATUS, CREATE_STATUS, INSERT_STATUS};

    public static String DROP_SUBJECT = "DROP TABLE IF EXISTS subject;";
    public static String CREATE_SUBJECT = "create table subject (id int AUTO_INCREMENT PRIMARY KEY NOT NULL,name_en VARCHAR(50) NOT NULL,name_ru VARCHAR(50) NOT NULL) DEFAULT CHARSET=utf8;";
    public static String INSERT_SUBJECT = "INSERT INTO subject VALUES (id,'Marketing','Маркетинг'),(id,'Medicine','Медицина'),(id,'Finance','Финансы'),(id,'Engineering','Инженерия');";
    public static String[] SUBJECT_RELATED = {DROP_SUBJECT, CREATE_SUBJECT, INSERT_SUBJECT};

    public static String DROP_TEST = "DROP TABLE IF EXISTS test;";
    public static String CREATE_TEST = "CREATE TABLE test(\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    name VARCHAR(150) NOT NULL,\n" +
            "    subject_id INT DEFAULT 1,\n" +
            "    complexity_id INT DEFAULT 1,\n" +
            "    duration_sec INT NOT NULL,\n" +
            "    is_active BOOLEAN NOT NULL,\n" +
            "\tFOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE SET NULL,\n" +
            "    FOREIGN KEY (complexity_id) REFERENCES complexity(id) ON DELETE SET NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String INSERT_TEST = "INSERT INTO test VALUES " +
            "(id,'Test # 1',1,3,2651,TRUE)," +
            "(id,'Тест # 2',2,1,2804,TRUE)," +
            "(id,'Test # 3',3,2,667,TRUE)," +
            "(id,'Тест # 4',4,3,2432,TRUE)," +
            "(id,'Test # 5',1,1,778,FALSE);";
    public static String[] TEST_RELATED = {DROP_TEST, CREATE_TEST, INSERT_TEST};

    public static String DROP_QUESTION = "DROP TABLE IF EXISTS question;";
    public static String CREATE_QUESTION = "CREATE TABLE question(\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    text VARCHAR(400) NOT NULL,\n" +
            "    test_id INT NOT NULL,\n" +
            "    FOREIGN KEY (test_id) REFERENCES test(id) ON DELETE CASCADE\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String INSERT_QUESTION = "INSERT INTO question VALUES " +
            "(1,'Question, id #1 for test: Test # 1',1),(2,'Question, id #2 for test: Test # 1',1),(3,'Question, id #3 for test: Test # 1',1),(4,'Question, id #4 for test: Тест # 2',2),(5,'Question, id #5 for test: Тест # 2',2),(6,'Question, id #6 for test: Тест # 2',2),(7,'Question, id #7 for test: Тест # 2',2),(8,'Question, id #8 for test: Test # 3',3),(9,'Question, id #9 for test: Test # 3',3),(10,'Question, id #10 for test: Test # 3',3),(11,'Question, id #11 for test: Test # 3',3),(12,'Question, id #12 for test: Test # 3',3),(13,'Question, id #13 for test: Тест # 4',4),(14,'Question, id #14 for test: Тест # 4',4),(15,'Question, id #15 for test: Тест # 4',4),(16,'Question, id #16 for test: Тест # 4',4),(17,'Question, id #17 for test: Тест # 4',4),(18,'Question, id #18 for test: Тест # 4',4),(19,'Question, id #19 for test: Test # 5',5),(20,'Question, id #20 for test: Test # 5',5),(21,'Question, id #21 for test: Test # 5',5),(22,'Question, id #22 for test: Test # 5',5),(23,'Question, id #23 for test: Test # 5',5),(24,'Question, id #24 for test: Test # 5',5),(25,'Question, id #25 for test: Test # 5',5);";
    public static String[] QUESTION_RELATED = {DROP_QUESTION, CREATE_QUESTION, INSERT_QUESTION};

    public static String DROP_ANSWER = "DROP TABLE IF EXISTS answer;";
    public static String CREATE_ANSWER = "CREATE TABLE answer (\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    question_id INT NOT NULL,\n" +
            "    text VARCHAR(400) NOT NULL,\n" +
            "    isCorrect BOOLEAN NOT NULL,\n" +
            "    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE\n" +
            ") DEFAULT CHARSET=utf8;\n";
    public static String INSERT_ANSWER = "INSERT INTO answer VALUES " +
            "(1,1,'CorAns, id #1 for: Question, id #1 for test: Test # 1',TRUE),(2,2,'CorAns, id #2 for: Question, id #2 for test: Test # 1',TRUE),(3,3,'CorAns, id #3 for: Question, id #3 for test: Test # 1',TRUE),(4,4,'CorAns, id #4 for: Question, id #4 for test: Тест # 2',TRUE),(5,5,'CorAns, id #5 for: Question, id #5 for test: Тест # 2',TRUE),(6,6,'CorAns, id #6 for: Question, id #6 for test: Тест # 2',TRUE),(7,7,'CorAns, id #7 for: Question, id #7 for test: Тест # 2',TRUE),(8,8,'CorAns, id #8 for: Question, id #8 for test: Test # 3',TRUE),(9,9,'CorAns, id #9 for: Question, id #9 for test: Test # 3',TRUE),(10,10,'CorAns, id #10 for: Question, id #10 for test: Test # 3',TRUE),(11,11,'CorAns, id #11 for: Question, id #11 for test: Test # 3',TRUE),(12,12,'CorAns, id #12 for: Question, id #12 for test: Test # 3',TRUE),(13,13,'CorAns, id #13 for: Question, id #13 for test: Тест # 4',TRUE),(14,14,'CorAns, id #14 for: Question, id #14 for test: Тест # 4',TRUE),(15,15,'CorAns, id #15 for: Question, id #15 for test: Тест # 4',TRUE),(16,16,'CorAns, id #16 for: Question, id #16 for test: Тест # 4',TRUE),(17,17,'CorAns, id #17 for: Question, id #17 for test: Тест # 4',TRUE),(18,18,'CorAns, id #18 for: Question, id #18 for test: Тест # 4',TRUE),(19,19,'CorAns, id #19 for: Question, id #19 for test: Test # 5',TRUE),(20,20,'CorAns, id #20 for: Question, id #20 for test: Test # 5',TRUE),(21,21,'CorAns, id #21 for: Question, id #21 for test: Test # 5',TRUE),(22,22,'CorAns, id #22 for: Question, id #22 for test: Test # 5',TRUE),(23,23,'CorAns, id #23 for: Question, id #23 for test: Test # 5',TRUE),(24,24,'CorAns, id #24 for: Question, id #24 for test: Test # 5',TRUE),(25,25,'CorAns, id #25 for: Question, id #25 for test: Test # 5',TRUE),(26,5,'CorAns, id #26 for: Question, id #5 for test: Тест # 2',TRUE),(27,18,'CorAns, id #27 for: Question, id #18 for test: Тест # 4',TRUE),(28,6,'IncorAns, id #28 for: Question, id #6 for test: Тест # 2',FALSE),(29,25,'CorAns, id #29 for: Question, id #25 for test: Test # 5',TRUE),(30,21,'CorAns, id #30 for: Question, id #21 for test: Test # 5',TRUE),(31,21,'IncorAns, id #31 for: Question, id #21 for test: Test # 5',FALSE),(32,9,'IncorAns, id #32 for: Question, id #9 for test: Test # 3',FALSE),(33,13,'IncorAns, id #33 for: Question, id #13 for test: Тест # 4',FALSE),(34,13,'CorAns, id #34 for: Question, id #13 for test: Тест # 4',TRUE),(35,18,'CorAns, id #35 for: Question, id #18 for test: Тест # 4',TRUE),(36,16,'CorAns, id #36 for: Question, id #16 for test: Тест # 4',TRUE),(37,5,'IncorAns, id #37 for: Question, id #5 for test: Тест # 2',FALSE),(38,24,'CorAns, id #38 for: Question, id #24 for test: Test # 5',TRUE),(39,5,'CorAns, id #39 for: Question, id #5 for test: Тест # 2',TRUE),(40,1,'IncorAns, id #40 for: Question, id #1 for test: Test # 1',FALSE),(41,4,'IncorAns, id #41 for: Question, id #4 for test: Тест # 2',FALSE),(42,19,'CorAns, id #42 for: Question, id #19 for test: Test # 5',TRUE),(43,13,'CorAns, id #43 for: Question, id #13 for test: Тест # 4',TRUE),(44,15,'IncorAns, id #44 for: Question, id #15 for test: Тест # 4',FALSE),(45,4,'IncorAns, id #45 for: Question, id #4 for test: Тест # 2',FALSE),(46,11,'IncorAns, id #46 for: Question, id #11 for test: Test # 3',FALSE),(47,18,'CorAns, id #47 for: Question, id #18 for test: Тест # 4',TRUE),(48,8,'CorAns, id #48 for: Question, id #8 for test: Test # 3',TRUE),(49,3,'CorAns, id #49 for: Question, id #3 for test: Test # 1',TRUE),(50,25,'IncorAns, id #50 for: Question, id #25 for test: Test # 5',FALSE),(51,19,'CorAns, id #51 for: Question, id #19 for test: Test # 5',TRUE),(52,12,'CorAns, id #52 for: Question, id #12 for test: Test # 3',TRUE),(53,16,'IncorAns, id #53 for: Question, id #16 for test: Тест # 4',FALSE),(54,19,'CorAns, id #54 for: Question, id #19 for test: Test # 5',TRUE),(55,19,'CorAns, id #55 for: Question, id #19 for test: Test # 5',TRUE),(56,25,'IncorAns, id #56 for: Question, id #25 for test: Test # 5',FALSE),(57,12,'CorAns, id #57 for: Question, id #12 for test: Test # 3',TRUE),(58,6,'IncorAns, id #58 for: Question, id #6 for test: Тест # 2',FALSE),(59,17,'CorAns, id #59 for: Question, id #17 for test: Тест # 4',TRUE),(60,24,'IncorAns, id #60 for: Question, id #24 for test: Test # 5',FALSE),(61,1,'CorAns, id #61 for: Question, id #1 for test: Test # 1',TRUE),(62,22,'CorAns, id #62 for: Question, id #22 for test: Test # 5',TRUE),(63,16,'IncorAns, id #63 for: Question, id #16 for test: Тест # 4',FALSE),(64,21,'CorAns, id #64 for: Question, id #21 for test: Test # 5',TRUE),(65,15,'IncorAns, id #65 for: Question, id #15 for test: Тест # 4',FALSE),(66,17,'CorAns, id #66 for: Question, id #17 for test: Тест # 4',TRUE),(67,22,'CorAns, id #67 for: Question, id #22 for test: Test # 5',TRUE),(68,6,'CorAns, id #68 for: Question, id #6 for test: Тест # 2',TRUE),(69,24,'IncorAns, id #69 for: Question, id #24 for test: Test # 5',FALSE),(70,13,'IncorAns, id #70 for: Question, id #13 for test: Тест # 4',FALSE),(71,16,'IncorAns, id #71 for: Question, id #16 for test: Тест # 4',FALSE),(72,16,'IncorAns, id #72 for: Question, id #16 for test: Тест # 4',FALSE),(73,6,'CorAns, id #73 for: Question, id #6 for test: Тест # 2',TRUE),(74,14,'IncorAns, id #74 for: Question, id #14 for test: Тест # 4',FALSE),(75,17,'CorAns, id #75 for: Question, id #17 for test: Тест # 4',TRUE);";
    public static String[] ANSWER_RELATED = {DROP_ANSWER, CREATE_ANSWER, INSERT_ANSWER};

    public static String DROP_PASSED_TESTS = "DROP TABLE IF EXISTS question;";
    public static String CREATE_PASSED_TESTS = "CREATE TABLE passed_tests (\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    user_id INT NOT NULL,\n" +
            "    test_id INT NOT NULL,\n" +
            "    question_num INT NOT NULL,\n" +
            "    correct_answers INT NOT NULL,\n" +
            "    grade FLOAT NOT NULL,\n" +
            "    time_spent float NOT NULL,\n" +
            "    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,\n" +
            "    FOREIGN KEY (test_id) REFERENCES test(id) ON DELETE CASCADE\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String ADD_PASSED_TESTS = "INSERT INTO passed_tests(id,user_id,test_id,question_num,correct_answers,grade,time_spent) VALUES(1,2,4,10,2,20,57.85),(2,2,3,10,6,60,36.99),(3,2,4,10,6,60,28.02),(4,2,1,5,1,20,10.11),(5,2,5,15,14,93.3333333333333,85.8),(6,3,4,10,2,20,16.92),(7,3,5,15,11,73.3333333333333,20.23),(8,3,3,10,5,50,47.38),(9,3,2,10,9,90,11),(10,3,5,15,15,100,75.88),(11,3,4,10,3,30,90.82),(12,3,3,10,2,20,32.55),(13,3,1,5,0,0,96.97);";
    public static String[] PASSED_TESTS_RELATED = {DROP_PASSED_TESTS, CREATE_PASSED_TESTS, ADD_PASSED_TESTS};

    public static String DROP_USER = "DROP TABLE IF EXISTS user;";
    public static String CREATE_USER = "create table user (\n" +
            "\tid INT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    username VARCHAR(25) UNIQUE NOT NULL,\n" +
            "    password VARCHAR(128) NOT NULL,\n" +
            "    salt int NOT NULL,\n" +
            "    first_name VARCHAR(25) NOT NULL,\n" +
            "    last_name VARCHAR(25) NOT NULL,\n" +
            "    role_id INT DEFAULT 2,\n" +
            "    status_id INT DEFAULT 1,\n" +
            "    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE SET NULL,\n" +
            "    FOREIGN KEY (status_id) REFERENCES status(id) ON DELETE SET NULL\n" +
            ") DEFAULT CHARSET=utf8;";
    public static String ADD_USER = "INSERT INTO user VALUES (id,'admin','b41f5655c94d6ff7c5a87e90c8af201476b53fc85ff12fb4d6ce0c7b9e87c463c35903f39996054088c42629b01ab463c813e45e9f3031a110a9227d7bb6d167',2062937534,'admin','admin',1,1),(id,'jack','20aff34704e5b6e21bfafe98b94d80f867ebeac0b5493080ab816717e9cfb760ab1123315bbabeb6cefa0fbdaa8c6d81430dafd352e7d4e7a461cdf682caa17b',350706328,'Jack','Black',2,1),(id,'amy','c6bcb8fe9da0ff8fabc5bd8b192cd65b73d9eb270aaad8348a68fb9606a3aab09d5e5b296e99226a869f61b3d3281b294ff367f920bd259a47dabf0c9fc78151',671497443,'Amy','Stone',2,2);";
    public static String[] USER_RELATED = {DROP_USER, CREATE_USER, ADD_USER};


}
