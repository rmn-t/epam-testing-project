package com.epam.db.dao.mysql;

import com.epam.exceptions.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.SubjectDao;
import com.epam.db.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoMysql implements SubjectDao {
    private final Logger logger = LoggerFactory.getLogger(SubjectDaoMysql.class);

    @Override
    public List<Subject> getAllRecords(String lang) throws DBException {
        List<Subject> subjects = new ArrayList<>();
        String nameCol = "name_" + lang;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id," + nameCol + " FROM subject ORDER BY " + nameCol + " ASC;");
            while (rs.next()) {
                subjects.add(new Subject.Builder().setId(rs.getInt("id")).setName(rs.getString(nameCol)).build());
            }
            logger.debug("Obtained {} subjects from database.",subjects.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain subjects from database.",e);
            throw new DBException("Couldn't obtain subjects from database.",e);
        } finally {
            DBUtil.closeAllInOrder(rs,stmt,con);
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
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM subject");
            rs.next();
            res = rs.getInt("total");
            logger.debug("Total number of records in subject table is {}.",res);
        } catch (SQLException e) {
            logger.error("Failed to get the total subjects number.",e);
            throw new DBException("Failed to get the total subjects number.",e);
        } finally {
            DBUtil.closeAllInOrder(rs, stmt, con);
        }
        return res;
    }
}
