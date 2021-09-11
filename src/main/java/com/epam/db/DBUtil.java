package com.epam.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static DataSource dataSource;

    static {
        try {
            Context context = (Context) new InitialContext().lookup("java:/comp/env");
            dataSource = (DataSource) context.lookup("jdbc/mysql");
        } catch (NamingException e) {
            logger.error("Couldn't create connection to the DB.",e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DBUtil() {    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeAllInOrder(AutoCloseable ...autoCloseables) {
        for (AutoCloseable ac : autoCloseables) {
            close(ac);
        }
    }
}
