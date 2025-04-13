package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.mapper.HttpServletRequestToDtoMapper;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeRateHttpServletRequestMapper implements HttpServletRequestToDtoMapper<ExchangeRateRequestDto> {

    private static final ExchangeRateHttpServletRequestMapper INSTANCE = new ExchangeRateHttpServletRequestMapper();

    private ExchangeRateHttpServletRequestMapper() {
    }

    @Override
    public ExchangeRateRequestDto fromRequest(HttpServletRequest req) {
        return ExchangeRateRequestDto.builder()
                .baseCurrencyCode(req.getParameter("baseCurrencyCode"))
                .targetCurrencyCode(req.getParameter("targetCurrencyCode"))
                .rate(req.getParameter("rate"))
                .build();
    }

    public static ExchangeRateHttpServletRequestMapper getInstance() {
        return INSTANCE;
    }
}
