package com.epam.db.dao;

import com.epam.db.model.Complexity;
import com.epam.exceptions.DBException;

import java.util.List;

public interface ComplexityDao {

    List<Complexity> getAllRecords() throws DBException;

}
