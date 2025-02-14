package com.projects.currencyconversion.mapper.impl;

import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.mapper.HttpServletRequestToDtoMapper;
import jakarta.servlet.http.HttpServletRequest;

public class CurrencyHttpServletRequestMapper implements HttpServletRequestToDtoMapper<CurrencyRequestDto> {

    private static final CurrencyHttpServletRequestMapper INSTANCE = new CurrencyHttpServletRequestMapper();

    private CurrencyHttpServletRequestMapper() {
    }

    @Override
    public CurrencyRequestDto fromRequest(HttpServletRequest req) {
        return CurrencyRequestDto.builder()
                .name(req.getParameter("name"))
                .code(req.getParameter("code"))
                .sign(req.getParameter("sign"))
                .build();
    }

    public static CurrencyHttpServletRequestMapper getInstance() {
        return INSTANCE;
    }
}
