package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class ExchangeRateRequestDtoValidator implements Validator<ExchangeRateRequestDto> {

    private static final ExchangeRateRequestDtoValidator INSTANCE = new ExchangeRateRequestDtoValidator();
    private final Validator<String> coupleOfCurrencyCodeValidator = CoupleOfCurrencyCodeValidator.getInstance();
    private final Validator<String> exchangeRateValidator = ExchangeRateValidator.getInstance();

    private ExchangeRateRequestDtoValidator() {
    }

    @Override
    public ValidationResult isValid(ExchangeRateRequestDto object) {
        ValidationResult validationResult = new ValidationResult();

        String coupleOfCode = object.baseCurrencyCode() + object.targetCurrencyCode();
        ValidationResult coupleOfCodeValidationResult = coupleOfCurrencyCodeValidator.isValid(coupleOfCode);
        if (!coupleOfCodeValidationResult.isValid()) {
            validationResult.add(coupleOfCodeValidationResult.getErrors());
        }
        ValidationResult exchangeRateValidationResult = exchangeRateValidator.isValid(object.rate());
        if (!exchangeRateValidationResult.isValid()) {
            validationResult.add(exchangeRateValidationResult.getErrors());
        }

        return validationResult;
    }

    public static ExchangeRateRequestDtoValidator getInstance() {
        return INSTANCE;
    }
}
