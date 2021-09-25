package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.StatusDao;
import com.epam.db.model.Status;
import com.epam.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL implementation of StatusDao interface
 */
public class StatusDaoMysql implements StatusDao {
    private final Logger logger = LoggerFactory.getLogger(StatusDaoMysql.class);
    private final DatabaseAccessable databaseAccessable;

    /**
     * Constructor allows to pick which DB connection will be used for internal method execution, injected via interface
     *
     * @param databaseAccessable database utility instance that will be used for DAO operations
     */
    public StatusDaoMysql(DatabaseAccessable databaseAccessable) {
        this.databaseAccessable = databaseAccessable;
    }

    @Override
    public List<Status> getAllRecords() throws DBException {
        List<Status> records = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id,name FROM status;");
            while (rs.next()) {
                records.add(new Status.Builder().setId(rs.getInt("id")).setName(rs.getString("name")).build());
            }
            logger.debug("Successfully obtained {} statuses from the db.", records.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain the list of user statuses.", e);
            throw new DBException("Couldn't obtain the list of user statuses.", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, stmt, con);
        }
        return records;
    }
}
