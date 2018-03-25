package com.softserve.edu.exception;

public class PasswordsDontMatchException extends ServiceException {

    public PasswordsDontMatchException() {}

    public PasswordsDontMatchException(String message) {
        super(message);
    }

}
