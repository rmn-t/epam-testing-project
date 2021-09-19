package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.Subject;

import java.util.List;

public interface SubjectDao {
    List<Subject> getAllRecords(String lang) throws DBException;

    int getRecordsNum() throws DBException;


}
