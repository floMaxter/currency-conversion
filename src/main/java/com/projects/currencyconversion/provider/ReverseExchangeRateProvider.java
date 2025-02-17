package com.projects.currencyconversion.provider;

import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.entity.ExchangeRate;

import java.util.Optional;

public class ReverseExchangeRateProvider extends ExchangeRateProvider {

    private final ExchangeRateDao exchangeRateDao;

    public ReverseExchangeRateProvider(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    public Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        return exchangeRateDao.findByCoupleOfCurrencyCode(targetCurrencyCode, baseCurrencyCode)
                .map(rate -> ExchangeRate.builder()
                        .id(rate.getId())
                        .baseCurrency(rate.getTargetCurrency())
                        .targetCurrency(rate.getBaseCurrency())
                        .rate(1 / rate.getRate())
                        .build())
                .or(() -> checkNext(baseCurrencyCode, targetCurrencyCode));
    }
}
