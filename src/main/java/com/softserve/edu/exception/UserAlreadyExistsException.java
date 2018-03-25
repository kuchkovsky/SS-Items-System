package com.softserve.edu.exception;

public class UserAlreadyExistsException extends ServiceException {

    public UserAlreadyExistsException() {}

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
