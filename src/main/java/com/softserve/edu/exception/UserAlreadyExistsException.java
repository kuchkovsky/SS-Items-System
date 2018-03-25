package com.softserve.edu.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {}

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
