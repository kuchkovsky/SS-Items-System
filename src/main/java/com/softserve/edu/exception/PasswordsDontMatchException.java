package com.softserve.edu.exception;

public class PasswordsDontMatchException extends Exception {

    public PasswordsDontMatchException() {}

    public PasswordsDontMatchException(String message) {
        super(message);
    }

}
