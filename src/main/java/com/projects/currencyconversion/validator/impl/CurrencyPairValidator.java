package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.entity.CurrencyPair;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class CurrencyPairValidator implements Validator<String> {

    private static final CurrencyPairValidator INSTANCE = new CurrencyPairValidator();
    private final Validator<String> currencyCodeValidator = CurrencyCodeValidator.getInstance();

    private CurrencyPairValidator() {
    }

    @Override
    public ValidationResult isValid(String object) {
        ValidationResult validationResult = new ValidationResult();

        CurrencyPair currencyPair = RequestUtils.getCurrencyPair(object);
        ValidationResult baseCodeValidationResult = currencyCodeValidator.isValid(currencyPair.baseCurrencyCode());
        if (!baseCodeValidationResult.isValid()) {
            validationResult.add(ValidationError.of("invalid.code",
                    "Base currency code is invalid: " + currencyPair.baseCurrencyCode()));
        }
        ValidationResult targetCodeValidationResult = currencyCodeValidator.isValid(currencyPair.targetCurrencyCode());
        if (!targetCodeValidationResult.isValid()) {
            validationResult.add(ValidationError.of("invalid.code",
                    "Target currency code is invalid: " + currencyPair.targetCurrencyCode()));
        }

        return validationResult;
    }

    public static CurrencyPairValidator getInstance() {
        return INSTANCE;
    }
}
