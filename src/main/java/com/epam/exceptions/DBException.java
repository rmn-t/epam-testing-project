package com.epam.exceptions;

public class DBException extends Exception {
    public DBException(String message,Throwable cause) {
        super(message,cause);
    }
}
