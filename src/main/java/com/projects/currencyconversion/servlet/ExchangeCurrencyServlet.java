package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.service.impl.ExchangeCurrencyServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/exchange")
public class ExchangeCurrencyServlet extends HttpServlet {

    private final ExchangeCurrencyService exchangeCurrencyService = ExchangeCurrencyServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        Map<String, String[]> params = req.getParameterMap();
        String baseCurrencyCode = Arrays.stream(params.get("from")).toList().get(0);
        String targetCurrencyCode = Arrays.stream(params.get("to")).toList().get(0);
        String amountStr = Arrays.stream(params.get("amount")).toList().get(0);
        Double amount = Double.valueOf(amountStr);

        ExchangeCurrencyRequestDto exchangeCurrencyRequestDto = ExchangeCurrencyRequestDto.builder()
                .baseCurrencyCode(baseCurrencyCode)
                .targetCurrencyCode(targetCurrencyCode)
                .amount(amount)
                .build();

        ExchangeCurrencyResponseDto exchangeCurrency
                = exchangeCurrencyService.exchange(exchangeCurrencyRequestDto);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeCurrency));
        }
    }
}
