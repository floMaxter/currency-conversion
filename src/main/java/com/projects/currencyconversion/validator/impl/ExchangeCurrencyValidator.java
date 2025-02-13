package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.Utils.CommonUtils;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.validator.ValidationError;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class ExchangeCurrencyValidator implements Validator<ExchangeCurrencyRequestDto> {

    private static final ExchangeCurrencyValidator INSTANCE = new ExchangeCurrencyValidator();
    private final Validator<String> coupleOfCurrencyCodeValidator = CoupleOfCurrencyCodeValidator.getInstance();

    private ExchangeCurrencyValidator() {
    }

    @Override
    public ValidationResult isValid(ExchangeCurrencyRequestDto object) {
        ValidationResult validationResult = new ValidationResult();

        String coupleOfCode = object.baseCurrencyCode() + object.targetCurrencyCode();
        ValidationResult coupleOfCodeValidationResult = coupleOfCurrencyCodeValidator.isValid(coupleOfCode);
        if (!coupleOfCodeValidationResult.isValid()) {
            validationResult.add(coupleOfCodeValidationResult.getErrors());
        }
        if (!isValidAmount(object.amount())) {
            validationResult.add(ValidationError.of("invalid.amount",
                    "Incorrect exchange amount: " + object.amount()));
        }

        return validationResult;
    }

    private boolean isValidAmount(String amount) {
        return amount != null && CommonUtils.isDouble(amount) && Double.parseDouble(amount) > 0;
    }

    public static ExchangeCurrencyValidator getInstance() {
        return INSTANCE;
    }
}
