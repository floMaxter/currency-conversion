package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.mapper.DtoToEntityMapper;

public class CurrencyRequestMapper implements DtoToEntityMapper<CurrencyRequestDto, Currency> {

    private static final CurrencyRequestMapper INSTANCE = new CurrencyRequestMapper();

    private CurrencyRequestMapper() {
    }

    @Override
    public Currency toEntity(CurrencyRequestDto dto) {
        return Currency.builder()
                .fullName(dto.name())
                .code(dto.code())
                .sign(dto.sign())
                .build();
    }

    public static CurrencyRequestMapper getInstance() {
        return INSTANCE;
    }
}
