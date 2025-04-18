package com.projects.currencyconversion.service;

import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.dto.ExchangeRateUpdateDto;

import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRateResponseDto> findAll();

    ExchangeRateResponseDto findByCoupleOfCode(String coupleOfCode);


    ExchangeRateResponseDto create(ExchangeRateRequestDto exchangeRateRequestDto);

    ExchangeRateResponseDto update(ExchangeRateUpdateDto exchangeRateUpdateDto);
}
