package com.projects.currencyconversion.validator;

public record ValidationError(String code, String message) {

    public static ValidationError of(String code, String message) {
        return new ValidationError(code, message);
    }

    @Override
    public String toString() {
        return "ValidationError{" +
               "code='" + code + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
