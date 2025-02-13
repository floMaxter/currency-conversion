package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.exception.ValidationException;
import com.projects.currencyconversion.mapper.impl.CurrencyResponseMapper;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;
import com.projects.currencyconversion.validator.impl.ExchangeCurrencyValidator;

import java.util.Optional;

public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    private static final ExchangeCurrencyServiceImpl INSTANCE = new ExchangeCurrencyServiceImpl();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();
    private final Validator<ExchangeCurrencyRequestDto> exchangeCurrencyValidator = ExchangeCurrencyValidator.getInstance();

    private ExchangeCurrencyServiceImpl() {
    }

    public static ExchangeCurrencyServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeCurrencyResponseDto exchange(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        ValidationResult validationResult = exchangeCurrencyValidator.isValid(exchangeCurrencyRequestDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getMessage());
        }

        // Input: String baseCode, String targetCode, String amount
        // Validation
        // Get exchange rate
        //      1: AB
        //      2: BA - take the inverse rate
        //      3: USD-A + USD-B - convert using another rate
        // Get amount
        // Get rate

        ExchangeRate exchangeRate = getExchangeRate(exchangeCurrencyRequestDto);

        Double amount = Double.valueOf(exchangeCurrencyRequestDto.amount());
        Double convertedAmount = exchangeRate.getRate() * amount;

        return ExchangeCurrencyResponseDto.builder()
                .baseCurrencyResponseDto(currencyResponseMapper.toDto(exchangeRate.getBaseCurrency()))
                .targetCurrencyResponseDto(currencyResponseMapper.toDto(exchangeRate.getTargetCurrency()))
                .rate(exchangeRate.getRate())
                .amount(amount)
                .convertedAmount(convertedAmount)
                .build();
    }

    private ExchangeRate getExchangeRate(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        String baseCurrencyCode = exchangeCurrencyRequestDto.baseCurrencyCode();
        String targetCurrencyCode = exchangeCurrencyRequestDto.targetCurrencyCode();
        String pivotCurrencyCode = "USD";

        Optional<ExchangeRate> optionalDirectExchangeRate = exchangeRateDao
                .findByCoupleOfCurrencyCode(baseCurrencyCode, targetCurrencyCode);
        if (optionalDirectExchangeRate.isPresent()) {
            return optionalDirectExchangeRate.get();
        }

        Optional<ExchangeRate> optionalReverseExchangeRate = exchangeRateDao
                .findByCoupleOfCurrencyCode(targetCurrencyCode, baseCurrencyCode);
        if (optionalReverseExchangeRate.isPresent()) {
            return getExchangeRateFromReverseRate(optionalReverseExchangeRate.get());
        }

        Optional<ExchangeRate> optionalUsdBaseExchangeRate = exchangeRateDao
                .findByCoupleOfCurrencyCode(pivotCurrencyCode, baseCurrencyCode);
        Optional<ExchangeRate> optionalUsdTargetExchangeRate = exchangeRateDao
                .findByCoupleOfCurrencyCode(pivotCurrencyCode, targetCurrencyCode);
        if (optionalUsdBaseExchangeRate.isPresent() && optionalUsdTargetExchangeRate.isPresent()) {
            return getExchangeRateViaUsd(optionalUsdBaseExchangeRate.get(), optionalUsdTargetExchangeRate.get());
        }

        throw new NotFoundException("It is impossible to exchange such currencies: "
                                    + exchangeCurrencyRequestDto.baseCurrencyCode() + " to "
                                    + exchangeCurrencyRequestDto.targetCurrencyCode());
    }

    private ExchangeRate getExchangeRateFromReverseRate(ExchangeRate reverseExchangeRate) {
        Double requiredRate = 1 / reverseExchangeRate.getRate();
        return ExchangeRate.builder()
                .baseCurrency(reverseExchangeRate.getTargetCurrency())
                .targetCurrency(reverseExchangeRate.getBaseCurrency())
                .rate(requiredRate)
                .build();
    }

    private ExchangeRate getExchangeRateViaUsd(ExchangeRate usdBaseExchangeRate, ExchangeRate usdTargetExchangeRate) {
        Double requiredRate = usdTargetExchangeRate.getRate() / usdBaseExchangeRate.getRate();
        return ExchangeRate.builder()
                .baseCurrency(usdBaseExchangeRate.getTargetCurrency())
                .targetCurrency(usdTargetExchangeRate.getTargetCurrency())
                .rate(requiredRate)
                .build();
    }
}
