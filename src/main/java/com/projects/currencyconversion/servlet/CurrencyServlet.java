package com.projects.currencyconversion.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.service.CurrencyService;
import com.projects.currencyconversion.service.impl.CurrencyServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static com.projects.currencyconversion.Utils.RequestUtils.getPathFromRequest;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        String code = getPathFromRequest(req);

        CurrencyResponseDto findCurrencyDto = currencyService.findByCode(code);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(findCurrencyDto));
        }
    }
}
