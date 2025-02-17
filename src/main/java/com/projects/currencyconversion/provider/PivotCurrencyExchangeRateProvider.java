package com.projects.currencyconversion.provider;

import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.entity.ExchangeRate;

import java.util.Optional;

public class PivotCurrencyExchangeRateProvider extends ExchangeRateProvider {

    private final String pivotCurrencyCode;
    private final ExchangeRateDao exchangeRateDao;

    public PivotCurrencyExchangeRateProvider(ExchangeRateDao exchangeRateDao, String pivotCurrencyCode) {
        this.pivotCurrencyCode = pivotCurrencyCode;
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    public Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        Optional<ExchangeRate> usdBaseRate = exchangeRateDao.findByCoupleOfCurrencyCode(pivotCurrencyCode, baseCurrencyCode);
        Optional<ExchangeRate> usdTargetRate = exchangeRateDao.findByCoupleOfCurrencyCode(pivotCurrencyCode, targetCurrencyCode);
        if (usdBaseRate.isPresent() && usdTargetRate.isPresent()) {
            return Optional.of(getExchangeRateViaUsd(usdBaseRate.get(), usdTargetRate.get()));
        }
        return checkNext(baseCurrencyCode, targetCurrencyCode);
    }

    private ExchangeRate getExchangeRateViaUsd(ExchangeRate usdBaseExchangeRate, ExchangeRate usdTargetExchangeRate) {
        Double requiredRate = usdTargetExchangeRate.getRate() / usdBaseExchangeRate.getRate();
        return ExchangeRate.builder()
                .baseCurrency(usdBaseExchangeRate.getTargetCurrency())
                .targetCurrency(usdTargetExchangeRate.getTargetCurrency())
                .rate(requiredRate)
                .build();
    }
}
