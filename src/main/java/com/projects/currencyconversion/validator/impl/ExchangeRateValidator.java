package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
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

        if (object == null) {
            validationResult.add(ValidationError.of("invalid.rate",
                    "Exchange rate is null"));
            return validationResult;
        }
        if (!RequestUtils.canBeParsedToDouble(object)) {
            validationResult.add(ValidationError.of("invalid.rate",
                    "This rate is not a number: " + object));
        }
        if (Double.parseDouble(object) < 0) {
            validationResult.add(ValidationError.of("invalid.rate",
                    "The rate cannot be negative: " + object));
        }
        return validationResult;
    }

    public static ExchangeRateValidator getInstance() {
        return INSTANCE;
    }
}
