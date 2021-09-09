package com.epam.db.dao;

import com.epam.db.model.Role;
import com.epam.exceptions.DBException;

import java.util.List;

public interface RoleDao {
    List<Role> getAllRecords() throws DBException;

}
