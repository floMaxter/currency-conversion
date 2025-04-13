package com.projects.currencyconversion.mapper;

import jakarta.servlet.http.HttpServletRequest;

public interface HttpServletRequestToDtoMapper<T> {

    T fromRequest(HttpServletRequest req);
}
