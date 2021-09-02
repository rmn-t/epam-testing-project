package com.epam.db;

public class DBException extends Exception {
    public DBException(String message,Throwable cause) {
        super(message,cause);
    }
}
