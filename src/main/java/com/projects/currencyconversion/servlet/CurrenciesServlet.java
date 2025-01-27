package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.service.CurrencyService;
import com.projects.currencyconversion.service.impl.CurrencyServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyResponseDto> currencyDtos = currencyService.findAll();

        resp.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(currencyDtos));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrencyRequestDto currencyRequestDto = buildCurrencyRequestDto(req);

        CurrencyResponseDto savedCurrency = currencyService.create(currencyRequestDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(savedCurrency));
        }
    }

    private CurrencyRequestDto buildCurrencyRequestDto(HttpServletRequest req) {
        return CurrencyRequestDto.builder()
                .name(req.getParameter("name"))
                .code(req.getParameter("code"))
                .sign(req.getParameter("sign"))
                .build();
    }
}
