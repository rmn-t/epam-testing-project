package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.ComplexityDao;
import com.epam.db.model.Complexity;
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
 * MySQL implementation of ComplexityDao interface
 */
public class ComplexityDaoMysql implements ComplexityDao {
    private final Logger logger = LoggerFactory.getLogger(ComplexityDaoMysql.class);
    private final DatabaseAccessable databaseAccessable;

    /**
     * Constructor allows to pick which DB connection will be used for internal method execution, injected via interface
     *
     * @param databaseAccessable database utility instance that will be used for DAO operations
     */
    public ComplexityDaoMysql(DatabaseAccessable databaseAccessable) {
        this.databaseAccessable = databaseAccessable;
    }

    @Override
    public List<Complexity> getAllRecords() throws DBException {
        List<Complexity> records = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id,name,scale FROM complexity ORDER BY scale ASC;");
            while (rs.next()) {
                records.add(new Complexity.Builder().setId(rs.getInt("id")).setName(rs.getString("name")).build());
            }
            logger.info("Successfully obtained {} records from complexity table in DB.", records.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain the complexities from database.", e);
            throw new DBException("Couldn't obtain the complexities from database.", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, stmt, con);
        }
        return records;
    }
}
