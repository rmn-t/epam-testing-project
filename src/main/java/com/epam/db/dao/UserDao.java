package com.epam.db.dao;

import com.epam.exceptions.DBException;
import com.epam.db.model.User;

import java.util.List;

public interface UserDao {
    User getUserDetailsByUserName(String username) throws DBException;

    int addNewUser(String userName,String password,String firstName,String lastName) throws DBException;

    void updateUserById(int id, String password,String role, String status,int salt) throws DBException;

    List<User> getAllUsersLimitedSorted(int offset, int limit, String orderBy) throws DBException;

    int getNumOfUsers() throws DBException;

    boolean validateCredentials(User userFromDb,String password,String userName);
}
