package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

import java.util.regex.Pattern;

public class CoupleOfCurrencyCodeValidator implements Validator<String> {

    private static final CoupleOfCurrencyCodeValidator INSTANCE = new CoupleOfCurrencyCodeValidator();
    private final Validator<String> currencyCodeValidator = CurrencyCodeValidator.getInstance();
    private static final Integer CODE_LENGTH = Integer.parseInt(PropertiesUtil.get("db.currency.code.length"));
    private static final Integer COUPLE_OF_CODE_LENGTH = CODE_LENGTH * 2;

    private CoupleOfCurrencyCodeValidator() {
    }

    @Override
    public ValidationResult isValid(String object) {
        ValidationResult validationResult = new ValidationResult();

        if (!isValidCoupleOfCode(object)) {
            validationResult.add(ValidationError.of("invalid.code",
                    "Invalid couple of code: " + object));
            return validationResult;
        }
        ValidationResult baseCodeValidationResult = isValidCode(object, 0, CODE_LENGTH);
        if (!baseCodeValidationResult.isValid()) {
            validationResult.add(baseCodeValidationResult.getErrors());
        }
        ValidationResult targetCodeValidationResult = isValidCode(object, CODE_LENGTH, COUPLE_OF_CODE_LENGTH);
        if (!targetCodeValidationResult.isValid()) {
            validationResult.add(targetCodeValidationResult.getErrors());
        }

        return validationResult;
    }

    private boolean isValidCoupleOfCode(String coupleOfCode) {
        return coupleOfCode != null && Pattern.matches("[A-Z]{" + COUPLE_OF_CODE_LENGTH + "}", coupleOfCode);
    }

    private ValidationResult isValidCode(String coupleOfCode, int startIdx, int finishIdx) {
        ValidationResult validationResult = new ValidationResult();
        String code = coupleOfCode.substring(startIdx, finishIdx);
        ValidationResult baseCodeValidationResult = currencyCodeValidator.isValid(code);
        if (!baseCodeValidationResult.isValid()) {
            validationResult.add(baseCodeValidationResult.getErrors());
        }
        return validationResult;
    }

    public static CoupleOfCurrencyCodeValidator getInstance() {
        return INSTANCE;
    }
}
