package com.softserve.edu.exception;

public class EmptyFieldsException extends ServiceException {

    public EmptyFieldsException() {}

    public EmptyFieldsException(String message) {
        super(message);
    }

}
