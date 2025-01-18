package com.projects.currencyconversion.mapper;

import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;

public class CurrencyResponseMapper implements EntityToDtoMapper<Currency, CurrencyResponseDto> {

    private static final CurrencyResponseMapper INSTANCE = new CurrencyResponseMapper();

    private CurrencyResponseMapper() {
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

    public static CurrencyResponseMapper getInstance() {
        return INSTANCE;
    }
}
