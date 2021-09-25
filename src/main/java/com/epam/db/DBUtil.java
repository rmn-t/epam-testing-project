package com.epam.db;

import com.epam.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class provides database utility services
 */
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

    public static void close(AutoCloseable ac) throws DBException {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                logger.error("Couldn't close the item {}",ac.toString(),e);
                throw new DBException("Couldn't close the autocloseable item.",e);
            }
        }
    }

    /**
     * Closes all autocloseable items in order they are supplied as parameters
     * @param autoCloseables one or more autocloseable items
     * @throws DBException in case an error occurred while closing an item
     */
    public static void closeAllInOrder(AutoCloseable ...autoCloseables) throws DBException {
        for (AutoCloseable ac : autoCloseables) {
            close(ac);
        }
    }
}
