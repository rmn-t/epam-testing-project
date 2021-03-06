package com.epam.db.accessors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * An implementation of DatabaseAccessable interface that is used for creating connection for testing purposes
 */
public class DBTestUtil implements DatabaseAccessable {
    private final Logger logger = LoggerFactory.getLogger(DBProdUtil.class);
    private final String DB_NAME = "epam_project_test";
    private final String USER = "root";
    private final String PASS = "testqq";
    private String conUrl = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&useEncoding=true&characterEncoding=UTF-8&user=" + USER + "&password=" + PASS;

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(conUrl);
    }
}
