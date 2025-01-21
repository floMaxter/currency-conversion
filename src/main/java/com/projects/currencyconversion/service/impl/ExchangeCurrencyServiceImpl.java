package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.service.ExchangeRateService;

public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    private static final ExchangeCurrencyServiceImpl INSTANCE = new ExchangeCurrencyServiceImpl();
    ExchangeRateService exchangeRateService = ExchangeRateServiceImpl.getInstance();

    private ExchangeCurrencyServiceImpl() {
    }

    public static ExchangeCurrencyServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeCurrencyResponseDto exchange(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        // Найти ExchangeRate по паре кодов, рассмотреть три возможных сценария (AB, BA, AC-CB)
        // Посчитать rate * amount = convertedAmount
        // Занести поля в ExchangeCurrencyResponse

        String coupleOfCode = exchangeCurrencyRequestDto.baseCurrencyCode() +
                              exchangeCurrencyRequestDto.targetCurrencyCode();
        ExchangeRateResponseDto exchangeRate = exchangeRateService.findByCoupleOfCode(coupleOfCode);

        Double convertedAmount = exchangeCurrencyRequestDto.amount() * exchangeRate.rate();

        return ExchangeCurrencyResponseDto.builder()
                .baseCurrencyResponseDto(exchangeRate.baseCurrency())
                .targetCurrencyResponseDto(exchangeRate.targetCurrency())
                .rate(exchangeRate.rate())
                .amount(exchangeCurrencyRequestDto.amount())
                .convertedAmount(convertedAmount)
                .build();
    }
}
