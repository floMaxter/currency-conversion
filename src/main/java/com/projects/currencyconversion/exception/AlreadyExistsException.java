package com.projects.currencyconversion.exception;

public class AlreadyExistsException extends RuntimeException {

    private final String message;

    public AlreadyExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
