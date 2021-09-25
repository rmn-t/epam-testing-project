package com.epam.db.accessors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBProdUtil implements DatabaseAccessable {
    private final Logger logger = LoggerFactory.getLogger(DBProdUtil.class);
    private static DataSource dataSource;

    public DBProdUtil() {
        try {
            Context context = (Context) new InitialContext().lookup("java:/comp/env");
            dataSource = (DataSource) context.lookup("jdbc/mysql");
        } catch (NamingException e) {
            logger.error("Couldn't create connection to the DB.",e);
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
