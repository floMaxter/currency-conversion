package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class CoupleOfCurrencyCodeValidator implements Validator<String> {

    private static final CoupleOfCurrencyCodeValidator INSTANCE = new CoupleOfCurrencyCodeValidator();
    private final Validator<String> currencyCodeValidator = CurrencyCodeValidator.getInstance();
    private static final Integer CODE_LENGTH = Integer.parseInt(PropertiesUtil.get("db.currency.code.length"));
    private static final String EMPTY_STRING = "";

    private CoupleOfCurrencyCodeValidator() {
    }

    @Override
    public ValidationResult isValid(String object) {
        ValidationResult validationResult = new ValidationResult();

        String[] codes = getCoupleOfCurrencyCode(object);
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

    private String[] getCoupleOfCurrencyCode(String coupleOfCode) {
        String[] codes = new String[2];

        if (coupleOfCode.length() < CODE_LENGTH) {
            codes[0] = coupleOfCode;
            codes[1] = EMPTY_STRING;
        } else {
            codes[0] = coupleOfCode.substring(0, CODE_LENGTH);
            codes[1] = coupleOfCode.substring(CODE_LENGTH);
        }
        return codes;
    }

    public static CoupleOfCurrencyCodeValidator getInstance() {
        return INSTANCE;
    }
}
