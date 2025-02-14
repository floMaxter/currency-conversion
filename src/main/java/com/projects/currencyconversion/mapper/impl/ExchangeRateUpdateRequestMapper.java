package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dto.ExchangeRateUpdateDto;
import com.projects.currencyconversion.mapper.HttpServletRequestToDtoMapper;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeRateUpdateRequestMapper implements HttpServletRequestToDtoMapper<ExchangeRateUpdateDto> {

    private static final ExchangeRateUpdateRequestMapper INSTANCE = new ExchangeRateUpdateRequestMapper();

    private ExchangeRateUpdateRequestMapper() {
    }

    @Override
    public ExchangeRateUpdateDto fromRequest(HttpServletRequest req) {
        String coupleOfCode = RequestUtils.getPathFromRequest(req);
        String rate =  RequestUtils.getParamValueFromBody(req, "rate");

        return ExchangeRateUpdateDto.builder()
                .coupleOfCode(coupleOfCode)
                .rate(rate)
                .build();
    }

    public static ExchangeRateUpdateRequestMapper getInstance() {
        return INSTANCE;
    }
}
