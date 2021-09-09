package com.epam.db.dao;

import com.epam.db.model.Status;
import com.epam.exceptions.DBException;

import java.util.List;

public interface StatusDao {

    List<Status> getAllRecords() throws DBException;
}
