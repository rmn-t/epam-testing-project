package com.epam.db.accessors;

import com.epam.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An interface that is used for creating different types of connection to database
 */
public interface DatabaseAccessable {
    Logger logger = LoggerFactory.getLogger(DatabaseAccessable.class);

    Connection getConnection() throws SQLException;

    default void close(AutoCloseable ac) throws DBException {
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
    default void closeAllInOrder(AutoCloseable ...autoCloseables) throws DBException {
        for (AutoCloseable ac : autoCloseables) {
            close(ac);
        }
    }

}
