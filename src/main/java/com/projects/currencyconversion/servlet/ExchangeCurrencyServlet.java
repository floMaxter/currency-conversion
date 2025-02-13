package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.exception.ValidationException;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.service.impl.ExchangeCurrencyServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/exchange")
public class ExchangeCurrencyServlet extends HttpServlet {

    private final ExchangeCurrencyService exchangeCurrencyService = ExchangeCurrencyServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeCurrencyRequestDto exchangeCurrencyRequestDto = buildExchangeCurrencyRequestDto(req);

        ExchangeCurrencyResponseDto exchangeCurrency
                = exchangeCurrencyService.exchange(exchangeCurrencyRequestDto);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeCurrency));
        }
    }

    private ExchangeCurrencyRequestDto buildExchangeCurrencyRequestDto(HttpServletRequest req) {
        Map<String, String[]> params = req.getParameterMap();

        String[] baseCurrencyCodeValues = params.get("from");
        if (baseCurrencyCodeValues == null || baseCurrencyCodeValues.length != 1) {
            throw new ValidationException("The request contains an incorrect base currency parameter.");
        }
        String[] targetCurrencyCodeValues = params.get("to");
        if (targetCurrencyCodeValues == null || targetCurrencyCodeValues.length != 1) {
            throw new ValidationException("The request contains an incorrect quote currency parameter.");
        }
        String[] amountValues = params.get("amount");
        if (amountValues == null || amountValues.length != 1) {
            throw new ValidationException("The request contains an incorrect currency amount parameter.");
        }

        String baseCurrencyCode = baseCurrencyCodeValues[0];
        String targetCurrencyCode = targetCurrencyCodeValues[0];
        String amount = amountValues[0];

        return ExchangeCurrencyRequestDto.builder()
                .baseCurrencyCode(baseCurrencyCode)
                .targetCurrencyCode(targetCurrencyCode)
                .amount(amount)
                .build();
    }
}
