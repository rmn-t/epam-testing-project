package com.epam.db.dao;

import com.epam.db.DBException;
import com.epam.db.model.Subject;

import java.util.List;

public interface SubjectDao {
    List<Subject> getAllSubjects() throws DBException;


}
