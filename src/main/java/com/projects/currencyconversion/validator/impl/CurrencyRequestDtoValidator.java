package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class CurrencyRequestDtoValidator implements Validator<CurrencyRequestDto> {

    private static final CurrencyRequestDtoValidator INSTANCE = new CurrencyRequestDtoValidator();
    private final Validator<String> currencyCodeValidator = CurrencyCodeValidator.getInstance();

    private CurrencyRequestDtoValidator() {
    }

    @Override
    public ValidationResult isValid(CurrencyRequestDto object) {
        ValidationResult validationResult = new ValidationResult();

        ValidationResult codeValidationResult = currencyCodeValidator.isValid(object.code());
        if (!codeValidationResult.isValid()) {
            validationResult.add(codeValidationResult.getErrors());
        }
        if (!isValidCurrencyName(object.name())) {
            validationResult.add(ValidationError.of("invalid.name",
                    "Invalid currency name: " + object.name()));
        }
        if (!isValidCurrencySign(object.sign())) {
            validationResult.add(ValidationError.of("invalid.sign",
                    "Invalid currency sign: " + object.sign()));
        }
        return validationResult;
    }

    private boolean isValidCurrencyName(String name) {
        int minLengthName = Integer.parseInt(PropertiesUtil.get("db.currency.full_name.min_length"));
        int maxLengthName = Integer.parseInt(PropertiesUtil.get("db.currency.full_name.max_length"));
        return name != null && name.length() <= maxLengthName && name.length() >= minLengthName;
    }

    private boolean isValidCurrencySign(String sign) {
        int minSignLength = Integer.parseInt(PropertiesUtil.get("db.currency.sign.min_length"));
        int maxSignLength = Integer.parseInt(PropertiesUtil.get("db.currency.sign.max_length"));
        return sign != null && sign.length() <= maxSignLength && sign.length() >= minSignLength;
    }

    public static CurrencyRequestDtoValidator getInstance() {
        return INSTANCE;
    }
}
