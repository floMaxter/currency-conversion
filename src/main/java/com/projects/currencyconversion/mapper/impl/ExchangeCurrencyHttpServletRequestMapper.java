package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.mapper.HttpServletRequestToDtoMapper;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeCurrencyHttpServletRequestMapper implements HttpServletRequestToDtoMapper<ExchangeCurrencyRequestDto> {

    private static final ExchangeCurrencyHttpServletRequestMapper INSTANCE = new ExchangeCurrencyHttpServletRequestMapper();

    private ExchangeCurrencyHttpServletRequestMapper() {
    }

    @Override
    public ExchangeCurrencyRequestDto fromRequest(HttpServletRequest req) {
        return ExchangeCurrencyRequestDto.builder()
                .baseCurrencyCode(RequestUtils.getParamValueFromRequest(req, "from"))
                .targetCurrencyCode(RequestUtils.getParamValueFromRequest(req, "to"))
                .amount(RequestUtils.getParamValueFromRequest(req, "amount"))
                .build();
    }

    public static ExchangeCurrencyHttpServletRequestMapper getInstance() {
        return INSTANCE;
    }
}
