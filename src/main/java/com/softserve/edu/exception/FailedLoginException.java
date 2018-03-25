package com.softserve.edu.exception;

public class FailedLoginException extends ServiceException {

    public FailedLoginException() {}

    public FailedLoginException(String message) {
        super(message);
    }

}
