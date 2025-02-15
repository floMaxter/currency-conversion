package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.entity.ExchangeCurrency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.mapper.impl.ExchangeCurrencyResponseMapper;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.validator.Validator;
import com.projects.currencyconversion.validator.impl.ExchangeCurrencyValidator;

import java.util.Optional;

import static com.projects.currencyconversion.Utils.ValidationUtils.validate;

public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    private static final ExchangeCurrencyServiceImpl INSTANCE = new ExchangeCurrencyServiceImpl();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final ExchangeCurrencyResponseMapper exchangeCurrencyResponseMapper = ExchangeCurrencyResponseMapper.getInstance();
    private final Validator<ExchangeCurrencyRequestDto> exchangeCurrencyValidator = ExchangeCurrencyValidator.getInstance();

    private ExchangeCurrencyServiceImpl() {
    }

    public static ExchangeCurrencyServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeCurrencyResponseDto exchange(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        validate(exchangeCurrencyValidator.isValid(exchangeCurrencyRequestDto));

        ExchangeCurrency exchangeCurrency = createExchangeCurrency(exchangeCurrencyRequestDto);
        return exchangeCurrencyResponseMapper.toDto(exchangeCurrency);
    }

    private ExchangeCurrency createExchangeCurrency(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        ExchangeRate exchangeRate = getExchangeRate(exchangeCurrencyRequestDto);
        Double amount = Double.valueOf(exchangeCurrencyRequestDto.amount());
        Double convertedAmount = exchangeRate.getRate() * amount;

        return ExchangeCurrency.builder()
                .exchangeRate(exchangeRate)
                .amount(amount)
                .convertedAmount(convertedAmount)
                .build();
    }


    private ExchangeRate getExchangeRate(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        String baseCurrencyCode = exchangeCurrencyRequestDto.baseCurrencyCode();
        String targetCurrencyCode = exchangeCurrencyRequestDto.targetCurrencyCode();

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

        String pivotCurrencyCode = "USD";
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
