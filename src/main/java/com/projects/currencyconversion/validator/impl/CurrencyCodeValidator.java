package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

import java.util.regex.Pattern;

public class CurrencyCodeValidator implements Validator<String> {

    private static final CurrencyCodeValidator INSTANCE = new CurrencyCodeValidator();

    private CurrencyCodeValidator() {
    }

    @Override
    public ValidationResult isValid(String object) {
        ValidationResult validationResult = new ValidationResult();
        if (!isValidCode(object)) {
            validationResult.add(ValidationError.of("invalid.code",
                    "Invalid currency code: " + object));
        }
        return validationResult;
    }

    private boolean isValidCode(String code) {
        int numberOfChar = Integer.parseInt(PropertiesUtil.get("db.currency.code.length"));
        return code != null && Pattern.matches("[A-Z]{" + numberOfChar + "}", code);
    }

    public static CurrencyCodeValidator getInstance() {
        return INSTANCE;
    }
}
