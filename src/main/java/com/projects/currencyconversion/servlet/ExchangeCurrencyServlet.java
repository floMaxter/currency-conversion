package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.mapper.impl.ExchangeCurrencyHttpServletRequestMapper;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.service.impl.ExchangeCurrencyServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exchange")
public class ExchangeCurrencyServlet extends HttpServlet {

    private final ExchangeCurrencyService exchangeCurrencyService = ExchangeCurrencyServiceImpl.getInstance();
    private final ExchangeCurrencyHttpServletRequestMapper exchangeCurrencyHttpServletRequestMapper
            = ExchangeCurrencyHttpServletRequestMapper.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeCurrencyRequestDto exchangeCurrencyRequestDto = exchangeCurrencyHttpServletRequestMapper.fromRequest(req);

        ExchangeCurrencyResponseDto exchangeCurrency = exchangeCurrencyService.exchange(exchangeCurrencyRequestDto);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeCurrency));
        }
    }
}
