package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.ExchangeRatesResponseDto;
import com.projects.currencyconversion.service.impl.ExchangeRatesServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRatesServiceImpl exchangeRatesService = ExchangeRatesServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        List<ExchangeRatesResponseDto> findExchangeRates = exchangeRatesService.findAll();
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(findExchangeRates));
        }
    }
}
