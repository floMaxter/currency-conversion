package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.service.ExchangeRateService;
import com.projects.currencyconversion.service.impl.ExchangeRateServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = ExchangeRateServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRateResponseDto> findExchangeRates = exchangeRateService.findAll();
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(findExchangeRates));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRateRequestDto exchangeRateRequestDto = ExchangeRateRequestDto.builder()
                .baseCurrencyCode(req.getParameter("baseCurrencyCode"))
                .targetCurrencyCode(req.getParameter("targetCurrencyCode"))
                .rate(Double.valueOf(req.getParameter("rate")))
                .build();

        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateService.create(exchangeRateRequestDto);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeRateResponseDto));
        }
    }
}
