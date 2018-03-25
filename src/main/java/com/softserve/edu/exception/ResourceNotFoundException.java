package com.softserve.edu.exception;

public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
