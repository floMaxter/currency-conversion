package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class CoupleOfCurrencyCodeValidator implements Validator<String> {

    private static final CoupleOfCurrencyCodeValidator INSTANCE = new CoupleOfCurrencyCodeValidator();
    private final Validator<String> currencyCodeValidator = CurrencyCodeValidator.getInstance();

    private CoupleOfCurrencyCodeValidator() {
    }

    @Override
    public ValidationResult isValid(String object) {
        ValidationResult validationResult = new ValidationResult();

        String[] codes = RequestUtils.getCoupleOfCurrencyCode(object);
        ValidationResult baseCodeValidationResult = currencyCodeValidator.isValid(codes[0]);
        if (!baseCodeValidationResult.isValid()) {
            validationResult.add(ValidationError.of("invalid.code",
                    "Base currency code is invalid: " + codes[0]));
        }
        ValidationResult targetCodeValidationResult = currencyCodeValidator.isValid(codes[1]);
        if (!targetCodeValidationResult.isValid()) {
            validationResult.add(ValidationError.of("invalid.code",
                    "Target currency code is invalid: " + codes[1]));
        }

        return validationResult;
    }

    public static CoupleOfCurrencyCodeValidator getInstance() {
        return INSTANCE;
    }
}
