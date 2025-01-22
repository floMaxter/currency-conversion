package com.projects.currencyconversion.validator.impl;

import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;

public class CreateExchangeRateValidator implements Validator<ExchangeRateRequestDto> {

    private static final CreateExchangeRateValidator INSTANCE = new CreateExchangeRateValidator();
    private final CurrencyCodeValidator currencyCodeValidator = CurrencyCodeValidator.getInstance();
    private final ExchangeRateValidator exchangeRateValidator = ExchangeRateValidator.getInstance();

    private CreateExchangeRateValidator() {
    }

    @Override
    public ValidationResult isValid(ExchangeRateRequestDto object) {
        ValidationResult validationResult = new ValidationResult();

        ValidationResult baseCodeValidationResult = currencyCodeValidator.isValid(object.baseCurrencyCode());
        if (!baseCodeValidationResult.isValid()) {
            validationResult.add(baseCodeValidationResult.getErrors());
        }
        ValidationResult targetCodeValidationResult = currencyCodeValidator.isValid(object.targetCurrencyCode());
        if (!targetCodeValidationResult.isValid()) {
            validationResult.add(targetCodeValidationResult.getErrors());
        }
        ValidationResult exchangeRateValidationResult = exchangeRateValidator.isValid(object.rate());
        if (!exchangeRateValidationResult.isValid()) {
            validationResult.add(exchangeRateValidationResult.getErrors());
        }

        return validationResult;
    }

    public static CreateExchangeRateValidator getInstance() {
        return INSTANCE;
    }
}
