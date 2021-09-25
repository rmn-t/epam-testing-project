package com.epam.db.dao.mysql;

import java.sql.SQLException;
import java.sql.Statement;

public class SqlQueries {
    public static void addStatementsToBatch(Statement stmt, String[] queries) throws SQLException {
        for (String query : queries) {
            stmt.addBatch(query);
        }
    }

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
}
