package com.projects.currencyconversion.exception;

public record ErrorResponseDto(String message) {

    @Override
    public String toString() {
        return "ErrorResponseDto{" +
               "message='" + message + '\'' +
               '}';
    }
}
