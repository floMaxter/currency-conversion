package com.projects.currencyconversion.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
