package com.projects.currencyconversion.service;

import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;

public interface ExchangeCurrencyService {

    ExchangeCurrencyResponseDto exchange(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto);
}
