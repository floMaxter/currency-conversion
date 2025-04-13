package com.projects.currencyconversion.provider;

import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.entity.ExchangeRate;

import java.util.Optional;

public class DirectExchangeRateProvider extends ExchangeRateProvider {

    private final ExchangeRateDao exchangeRateDao;

    public DirectExchangeRateProvider(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    public Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        return exchangeRateDao.findByCurrencyPair(baseCurrencyCode, targetCurrencyCode)
                .or(() -> checkNext(baseCurrencyCode, targetCurrencyCode));
    }
}
