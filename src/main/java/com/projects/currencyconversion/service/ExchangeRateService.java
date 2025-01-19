package com.projects.currencyconversion.service;

import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateService {

    List<ExchangeRateResponseDto> findAll();

    Optional<ExchangeRateResponseDto> findById(Long id);

    ExchangeRateResponseDto create(ExchangeRateRequestDto exchangeRateRequestDto);
}
