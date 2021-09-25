package com.epam.db.dao.mysql;

import com.epam.db.accessors.DatabaseAccessable;
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

/**
 * MySQL implementation of RoleDao interface
 */
public class RoleDaoMysql implements RoleDao {
    private final Logger logger = LoggerFactory.getLogger(RoleDaoMysql.class);
    private final DatabaseAccessable databaseAccessable;

    /**
     * Constructor allows to pick which DB connection will be used for internal method execution, injected via interface
     *
     * @param databaseAccessable database utility instance that will be used for DAO operations
     */
    public RoleDaoMysql(DatabaseAccessable databaseAccessable) {
        this.databaseAccessable = databaseAccessable;
    }

    @Override
    public List<Role> getAllRecords() throws DBException {
        List<Role> roles = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = databaseAccessable.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id,name FROM role ORDER BY name;");
            while (rs.next()) {
                roles.add(new Role.Builder().setName(rs.getString("name")).setId(rs.getInt("id")).build());
            }
            logger.debug("Successfully obtained {} roles from the DB.", roles.size());
        } catch (SQLException e) {
            logger.error("Couldn't obtain the list of roles from DB.", e);
            throw new DBException("Couldn't obtain the list of roles from DB.", e);
        } finally {
            databaseAccessable.closeAllInOrder(rs, stmt, con);
        }
        return roles;
    }
}
