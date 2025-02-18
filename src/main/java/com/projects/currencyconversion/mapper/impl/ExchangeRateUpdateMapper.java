package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateUpdateDto;
import com.projects.currencyconversion.entity.CurrencyPair;
import com.projects.currencyconversion.mapper.DtoToDtoMapper;

public class ExchangeRateUpdateMapper implements DtoToDtoMapper<ExchangeRateUpdateDto, ExchangeRateRequestDto> {

    private static final ExchangeRateUpdateMapper INSTANCE = new ExchangeRateUpdateMapper();

    private ExchangeRateUpdateMapper() {
    }

    @Override
    public ExchangeRateRequestDto toDto(ExchangeRateUpdateDto baseDto) {
        CurrencyPair currencyPair = RequestUtils.getCurrencyPair(baseDto.coupleOfCode());
        return ExchangeRateRequestDto.builder()
                .baseCurrencyCode(currencyPair.baseCurrencyCode())
                .targetCurrencyCode(currencyPair.targetCurrencyCode())
                .rate(baseDto.rate())
                .build();
    }

    public static ExchangeRateUpdateMapper getInstance() {
        return INSTANCE;
    }
}
