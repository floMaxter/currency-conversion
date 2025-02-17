package com.projects.currencyconversion.provider;

import com.projects.currencyconversion.entity.ExchangeRate;

import java.util.Optional;

public abstract class ExchangeRateProvider {

    private ExchangeRateProvider next;

    public static ExchangeRateProvider link(ExchangeRateProvider first, ExchangeRateProvider... chain) {
        ExchangeRateProvider head = first;
        for (ExchangeRateProvider nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode);

    protected Optional<ExchangeRate> checkNext(String baseCode, String targetCode) {
        if (next == null) {
            return Optional.empty();
        }
        return next.getExchangeRate(baseCode, targetCode);
    }

}
