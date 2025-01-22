package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class ExchangeRateValidator implements Validator<String> {

    private static final ExchangeRateValidator INSTANCE = new ExchangeRateValidator();

    private ExchangeRateValidator() {
    }

    @Override
    public ValidationResult isValid(String object) {
        ValidationResult validationResult = new ValidationResult();
        if (!isDouble(object)) {
            validationResult.add(ValidationError.of("invalid.rate",
                    "The \"" + object + "\" is not a number"));
        }
        return validationResult;
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static ExchangeRateValidator getInstance() {
        return INSTANCE;
    }
}
