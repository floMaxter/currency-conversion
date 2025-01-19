package com.projects.currencyconversion.service;

import com.projects.currencyconversion.dto.ExchangeRatesRequestDto;
import com.projects.currencyconversion.dto.ExchangeRatesResponseDto;

import java.util.List;
import java.util.Optional;

public interface ExchangeRatesService {

    List<ExchangeRatesResponseDto> findAll();

    Optional<ExchangeRatesResponseDto> findById(Long id);

    ExchangeRatesResponseDto create(ExchangeRatesRequestDto exchangeRatesRequestDto);
}
