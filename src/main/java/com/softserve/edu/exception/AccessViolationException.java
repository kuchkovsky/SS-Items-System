package com.softserve.edu.exception;

public class AccessViolationException extends ServiceException {

    public AccessViolationException() {}

    public AccessViolationException(String message) {
        super(message);
    }

}
