package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.mapper.EntityToDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class CurrencyResponseMapper implements EntityToDtoMapper<Currency, CurrencyResponseDto> {

    private static final CurrencyResponseMapper INSTANCE = new CurrencyResponseMapper();

    private CurrencyResponseMapper() {
    }

    public static CurrencyResponseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public CurrencyResponseDto toDto(Currency entity) {
        return CurrencyResponseDto.builder()
                .id(entity.getId())
                .name(entity.getFullName())
                .code(entity.getCode())
                .sign(entity.getSign())
                .build();
    }

    @Override
    public List<CurrencyResponseDto> toDto(List<Currency> entities) {
        return entities.stream()
                .map(currency -> new CurrencyResponseDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                )).collect(toList());
    }
}
