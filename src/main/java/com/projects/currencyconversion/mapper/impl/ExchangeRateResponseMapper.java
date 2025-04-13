package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.mapper.EntityListToDtoListMapper;
import com.projects.currencyconversion.mapper.EntityToDtoMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExchangeRateResponseMapper implements EntityToDtoMapper<ExchangeRate, ExchangeRateResponseDto>,
        EntityListToDtoListMapper<ExchangeRate, ExchangeRateResponseDto> {

    private static final ExchangeRateResponseMapper INSTANCE = new ExchangeRateResponseMapper();
    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();

    private ExchangeRateResponseMapper() {
    }

    public static ExchangeRateResponseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeRateResponseDto toDto(ExchangeRate entity) {
        return ExchangeRateResponseDto.builder()
                .id(entity.getId())
                .baseCurrency(currencyResponseMapper.toDto(entity.getBaseCurrency()))
                .targetCurrency(currencyResponseMapper.toDto(entity.getTargetCurrency()))
                .rate(entity.getRate())
                .build();
    }

    @Override
    public List<ExchangeRateResponseDto> toDto(List<ExchangeRate> entities) {
        return entities.stream()
                .map(exchangeRate -> new ExchangeRateResponseDto(
                        exchangeRate.getId(),
                        currencyResponseMapper.toDto(exchangeRate.getBaseCurrency()),
                        currencyResponseMapper.toDto(exchangeRate.getTargetCurrency()),
                        exchangeRate.getRate()
                )).collect(toList());
    }
}
