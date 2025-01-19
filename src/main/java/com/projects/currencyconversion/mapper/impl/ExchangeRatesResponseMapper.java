package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.ExchangeRatesResponseDto;
import com.projects.currencyconversion.entity.ExchangeRates;
import com.projects.currencyconversion.mapper.EntityToDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class ExchangeRatesResponseMapper implements EntityToDtoMapper<ExchangeRates, ExchangeRatesResponseDto> {

    private static final ExchangeRatesResponseMapper INSTANCE = new ExchangeRatesResponseMapper();
    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();

    private ExchangeRatesResponseMapper() {
    }

    public static ExchangeRatesResponseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeRatesResponseDto toDto(ExchangeRates entity) {
        return ExchangeRatesResponseDto.builder()
                .id(entity.getId())
                .baseCurrency(currencyResponseMapper.toDto(entity.getBaseCurrency()))
                .targetCurrency(currencyResponseMapper.toDto(entity.getTargetCurrency()))
                .rate(entity.getRate())
                .build();
    }

    @Override
    public List<ExchangeRatesResponseDto> toDto(List<ExchangeRates> entities) {
        return entities.stream()
                .map(exchangeRates -> new ExchangeRatesResponseDto(
                        exchangeRates.getId(),
                        currencyResponseMapper.toDto(exchangeRates.getBaseCurrency()),
                        currencyResponseMapper.toDto(exchangeRates.getTargetCurrency()),
                        exchangeRates.getRate()
                )).collect(toList());
    }
}
