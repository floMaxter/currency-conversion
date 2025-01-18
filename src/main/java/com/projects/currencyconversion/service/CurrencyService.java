package com.projects.currencyconversion.service;

import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.dto.CurrencyResponseDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyResponseDto> findAll();

    CurrencyResponseDto findById(Long id);

    CurrencyResponseDto findByCode(String code);

    CurrencyResponseDto create(CurrencyRequestDto currencyRequestDto);
}
