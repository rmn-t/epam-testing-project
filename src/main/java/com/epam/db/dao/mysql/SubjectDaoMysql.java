package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
import com.epam.db.dao.SubjectDao;
import com.epam.db.model.Subject;
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
 * MySQL implementation of SubjectDao interface
 */
public class SubjectDaoMysql implements SubjectDao {
    private final Logger logger = LoggerFactory.getLogger(SubjectDaoMysql.class);
    private final DatabaseAccessable databaseAccessable;

    /**
     * Constructor allows to pick which DB connection will be used for internal method execution, injected via interface
     *
     * @param databaseAccessable database utility instance that will be used for DAO operations
     */
    public SubjectDaoMysql(DatabaseAccessable databaseAccessable) {
        this.databaseAccessable = databaseAccessable;
    }

    @Override
    public List<Subject> getAllRecords(String lang) throws DBException {
        List<Subject> subjects = new ArrayList<>();
        String nameCol = "name_" + lang;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id," + nameCol + " FROM subject ORDER BY " + nameCol + " ASC;");
            while (rs.next()) {
                subjects.add(new Subject.Builder().setId(rs.getInt("id")).setName(rs.getString(nameCol)).build());
            }
            logger.debug("Obtained {} subjects from database.", subjects.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain subjects from database.", e);
            throw new DBException("Couldn't obtain subjects from database.", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, stmt, con);
        }
        return subjects;
    }

    @Override
    public int getRecordsNum() throws DBException {
        int res = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM subject");
            rs.next();
            res = rs.getInt("total");
            logger.debug("Total number of records in subject table is {}.", res);
        } catch (SQLException e) {
            logger.error("Failed to get the total subjects number.", e);
            throw new DBException("Failed to get the total subjects number.", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, stmt, con);
        }
        return res;
    }
}
