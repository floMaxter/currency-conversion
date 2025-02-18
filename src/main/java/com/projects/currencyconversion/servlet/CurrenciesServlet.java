package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.mapper.impl.CurrencyHttpServletRequestMapper;
import com.projects.currencyconversion.service.CurrencyService;
import com.projects.currencyconversion.service.impl.CurrencyServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyServiceImpl.getInstance();
    private final CurrencyHttpServletRequestMapper currencyHttpServletRequestMapper
            = CurrencyHttpServletRequestMapper.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyResponseDto> currencyDtos = currencyService.findAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), currencyDtos);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrencyRequestDto currencyRequestDto = currencyHttpServletRequestMapper.fromRequest(req);

        CurrencyResponseDto savedCurrency = currencyService.create(currencyRequestDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), savedCurrency);
    }
}
