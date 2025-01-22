package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class CreateCurrencyValidator implements Validator<CurrencyRequestDto> {

    private static final CreateCurrencyValidator INSTANCE = new CreateCurrencyValidator();
    private final CurrencyCodeValidator currencyCodeValidator = CurrencyCodeValidator.getInstance();

    private CreateCurrencyValidator() {
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
                    "The \"" + object.name() + "\" name is invalid"));
        }
        if (!isValidCurrencySign(object.sign())) {
            validationResult.add(ValidationError.of("invalid.sign",
                    "The \"" + object.sign() + "\" sign is invalid"));
        }
        return validationResult;
    }

    private boolean isValidCurrencyName(String name) {
        int minLengthName = Integer.parseInt(PropertiesUtil.get("db.currency.full_name.min_length"));
        int maxLengthName = Integer.parseInt(PropertiesUtil.get("db.currency.full_name.max_length"));
        return name != null && name.length() < maxLengthName && name.length() > minLengthName;
    }

    private boolean isValidCurrencySign(String sign) {
        int minSignLength = Integer.parseInt(PropertiesUtil.get("db.currency.sign.min_length"));
        int maxSignLength = Integer.parseInt(PropertiesUtil.get("db.currency.sign.max_length"));
        return sign != null && sign.length() < maxSignLength && sign.length() > minSignLength;
    }

    public static CreateCurrencyValidator getInstance() {
        return INSTANCE;
    }
}
