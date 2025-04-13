package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeCurrency;
import com.projects.currencyconversion.mapper.EntityToDtoMapper;

public class ExchangeCurrencyResponseMapper implements EntityToDtoMapper<ExchangeCurrency, ExchangeCurrencyResponseDto> {

    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();

    private static final ExchangeCurrencyResponseMapper INSTANCE = new ExchangeCurrencyResponseMapper();

    private ExchangeCurrencyResponseMapper() {
    }

    @Override
    public ExchangeCurrencyResponseDto toDto(ExchangeCurrency entity) {
        Currency baseCurrency = entity.getExchangeRate().getBaseCurrency();
        Currency targetCurrency = entity.getExchangeRate().getTargetCurrency();

        return ExchangeCurrencyResponseDto.builder()
                .baseCurrencyResponseDto(currencyResponseMapper.toDto(baseCurrency))
                .targetCurrencyResponseDto(currencyResponseMapper.toDto(targetCurrency))
                .rate(entity.getExchangeRate().getRate())
                .amount(entity.getAmount())
                .convertedAmount(entity.getConvertedAmount())
                .build();
    }

    public static ExchangeCurrencyResponseMapper getInstance() {
        return INSTANCE;
    }
}
