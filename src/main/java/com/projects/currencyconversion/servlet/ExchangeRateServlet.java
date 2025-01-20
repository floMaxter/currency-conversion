package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.service.ExchangeRateService;
import com.projects.currencyconversion.service.impl.ExchangeRateServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.DoubleBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = ExchangeRateServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        String coupleOfCode = RequestUtils.getPathFromRequest(req);
        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateService.findByCoupleOfCode(coupleOfCode);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeRateResponseDto));
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

        String coupleOfCode = RequestUtils.getPathFromRequest(req);
        try (PrintWriter writer = resp.getWriter()) {
            Map<String, String> params =  RequestUtils.getParamsFromRequestBody(req);
            Double newRate = Double.valueOf(params.get("rate"));

            ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateService.update(coupleOfCode, newRate);
            writer.write(objectMapper.writeValueAsString(exchangeRateResponseDto));
        }
    }
}
