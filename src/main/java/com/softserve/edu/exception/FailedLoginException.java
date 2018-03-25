package com.softserve.edu.exception;

public class FailedLoginException extends Exception {

    public FailedLoginException() {}

    public FailedLoginException(String message) {
        super(message);
    }

}
