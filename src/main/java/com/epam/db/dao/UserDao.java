package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.User;

import java.util.List;

public interface UserDao {
    User getUserDetailsByUserName(String username) throws DBException;

    int addNewUser(String userName,String password,String firstName,String lastName) throws DBException;

    void updateUserById(int id, String password,int roleId, int statusId,String firstName,String lastName,int salt) throws DBException;

    void deleteUserById(int id) throws DBException;

    List<User> getRecordsLimitedSortedFiltered(int offset, int limit, String orderBy,int statusId) throws DBException;

    int getRecordsNumByStatusId(int subjectId) throws DBException;

    boolean validateCredentials(User userFromDb, String password, String userName);
}
