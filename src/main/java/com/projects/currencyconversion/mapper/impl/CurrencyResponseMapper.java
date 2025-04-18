package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.mapper.EntityListToDtoListMapper;
import com.projects.currencyconversion.mapper.EntityToDtoMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CurrencyResponseMapper implements EntityToDtoMapper<Currency, CurrencyResponseDto>,
        EntityListToDtoListMapper<Currency, CurrencyResponseDto> {

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
                .map(currency -> CurrencyResponseDto.builder()
                        .id(currency.getId())
                        .name(currency.getFullName())
                        .code(currency.getCode())
                        .sign(currency.getSign())
                        .build()).collect(toList());
    }
}
