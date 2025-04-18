package com.projects.currencyconversion.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.currencyconversion.exception.AlreadyExistsException;
import com.projects.currencyconversion.exception.ErrorResponseDto;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.exception.ValidationException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebFilter("/*")
public class ExceptionHandlerFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ValidationException e) {
            handleException((HttpServletResponse) servletResponse, HttpServletResponse.SC_BAD_REQUEST, new ErrorResponseDto(e.getMessage()));
        } catch (NotFoundException e) {
            handleException((HttpServletResponse) servletResponse, HttpServletResponse.SC_NOT_FOUND, new ErrorResponseDto(e.getMessage()));
        } catch (AlreadyExistsException e) {
            handleException((HttpServletResponse) servletResponse, HttpServletResponse.SC_CONFLICT, new ErrorResponseDto(e.getMessage()));
        } catch (Exception e) {
            handleException((HttpServletResponse) servletResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new ErrorResponseDto(e.getMessage()));
        }
    }

    private void handleException(HttpServletResponse resp,
                                 int statusCode,
                                 ErrorResponseDto errorResponseDto) throws IOException {
        resp.setStatus(statusCode);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(errorResponseDto));
        }
    }
}
