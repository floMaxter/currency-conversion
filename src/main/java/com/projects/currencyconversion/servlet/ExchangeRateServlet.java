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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = ExchangeRateServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String coupleOfCode = RequestUtils.getPathFromRequest(req);

        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateService.findByCoupleOfCode(coupleOfCode);
        resp.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeRateResponseDto));
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String coupleOfCode = RequestUtils.getPathFromRequest(req);
        Map<String, String> params =  RequestUtils.getParamsFromRequestBody(req);

        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateService.update(coupleOfCode, params.get("rate"));
        resp.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(exchangeRateResponseDto));
        }
    }
}
