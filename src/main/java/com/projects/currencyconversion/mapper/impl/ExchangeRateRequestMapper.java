package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.mapper.DtoToEntityMapper;

public class ExchangeRateRequestMapper implements DtoToEntityMapper<ExchangeRateRequestDto, ExchangeRate> {

    private static final ExchangeRateRequestMapper INSTANCE = new ExchangeRateRequestMapper();
    private final CurrencyRequestMapper currencyRequestMapper = CurrencyRequestMapper.getInstance();

    private ExchangeRateRequestMapper() {
    }

    public static ExchangeRateRequestMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeRate toEntity(ExchangeRateRequestDto dto) {
        return null;
    }
}
