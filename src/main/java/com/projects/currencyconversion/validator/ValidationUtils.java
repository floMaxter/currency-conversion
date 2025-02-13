package com.projects.currencyconversion.validator;

import com.projects.currencyconversion.exception.ValidationException;

public final class ValidationUtils {

    public static void validate(ValidationResult validationResult) {
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getMessage());
        }
    }
}
