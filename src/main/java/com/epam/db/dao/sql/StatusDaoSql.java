package com.epam.db.dao.sql;

import com.epam.db.DBUtil;
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

public class StatusDaoSql implements StatusDao {
    private final Logger logger = LoggerFactory.getLogger(StatusDaoSql.class);

    @Override
    public List<Status> getAllRecords() throws DBException {
        List<Status> records = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id,name FROM status;");
            while (rs.next()) {
                records.add(new Status.Builder().setId(rs.getInt("id")).setName(rs.getString("name")).build());
            }
            logger.info("Successfully obtained {} statuses from the db.",records.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain the list of user statuses.",e);
            throw new DBException("Couldn't obtain the list of user statuses.",e);
        } finally {
            DBUtil.closeAllInOrder(rs,stmt,con);
        }
        return records;
    }
}
