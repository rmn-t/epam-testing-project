package com.epam.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static DataSource dataSource;

    static {
        try {
            initDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DBUtil() {    }

    private static void initDataSource() throws NamingException {
        Context context = (Context) new InitialContext().lookup("java:/comp/env");
        dataSource = (DataSource) context.lookup("jdbc/mysql");
    }

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
