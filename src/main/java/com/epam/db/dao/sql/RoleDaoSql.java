package com.epam.db.dao.sql;

import com.epam.db.DBUtil;
import com.epam.db.dao.RoleDao;
import com.epam.db.model.Role;
import com.epam.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoSql implements RoleDao {
    private final Logger logger = LoggerFactory.getLogger(RoleDaoSql.class);

    @Override
    public List<Role> getAllRecords() throws DBException {
        List<Role> roles = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id,name FROM role ORDER BY name;");
            while (rs.next()) {
                roles.add(new Role.Builder().setName(rs.getString("name")).setId(rs.getInt("id")).build());
            }
            logger.debug("Successfully obtained {} roles from the DB.",roles.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain the list of roles from DB.",e);
            throw new DBException("Couldn't obtain the list of roles from DB.",e);
        } finally {
            DBUtil.closeAllInOrder(rs,stmt,con);
        }
        return roles;
    }
}
