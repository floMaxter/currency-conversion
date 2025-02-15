package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateUpdateDto;
import com.projects.currencyconversion.mapper.DtoToDtoMapper;

public class ExchangeRateUpdateMapper implements DtoToDtoMapper<ExchangeRateUpdateDto, ExchangeRateRequestDto> {

    private static final ExchangeRateUpdateMapper INSTANCE = new ExchangeRateUpdateMapper();

    private ExchangeRateUpdateMapper() {
    }

    @Override
    public ExchangeRateRequestDto toDto(ExchangeRateUpdateDto baseDto) {
        String[] coupleOfCode = RequestUtils.getCoupleOfCurrencyCode(baseDto.coupleOfCode());
        return ExchangeRateRequestDto.builder()
                .baseCurrencyCode(coupleOfCode[0])
                .targetCurrencyCode(coupleOfCode[1])
                .rate(baseDto.rate())
                .build();
    }

    public static ExchangeRateUpdateMapper getInstance() {
        return INSTANCE;
    }
}
