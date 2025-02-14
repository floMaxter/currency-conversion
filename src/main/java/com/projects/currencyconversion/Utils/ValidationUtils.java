package com.projects.currencyconversion.Utils;

import com.projects.currencyconversion.exception.ValidationException;
import com.projects.currencyconversion.validator.ValidationResult;

public final class ValidationUtils {

    public static void validate(ValidationResult validationResult) {
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getMessage());
        }
    }
}
