package com.projects.currencyconversion.mapper;

import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.entity.Currency;

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
