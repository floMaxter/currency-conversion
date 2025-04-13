package com.projects.currencyconversion.service;

import com.projects.currencyconversion.entity.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateResolver {

    Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode);
}
