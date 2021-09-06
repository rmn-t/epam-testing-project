package com.epam.db.dao.sql;

import com.epam.db.DBException;
import com.epam.db.DBUtil;
import com.epam.db.dao.SubjectDao;
import com.epam.db.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoSql implements SubjectDao {
    private final Logger logger = LoggerFactory.getLogger(SubjectDaoSql.class);

    @Override
    public List<Subject> getAllSubjects() throws DBException {
        List<Subject> subjects = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT (id,name) FROM subject;");
            while (rs.next()) {
                subjects.add(new Subject.Builder().setId(rs.getInt("id")).setName(rs.getString("name")).build());
            }
            logger.info("Obtained {} subjects from database.",subjects.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain subjects from database.",e);
            throw new DBException("Couldn't obtain subjects from database.",e);
        } finally {
            DBUtil.closeAllInOrder(rs,stmt,con);
        }
        return subjects;
    }
}
