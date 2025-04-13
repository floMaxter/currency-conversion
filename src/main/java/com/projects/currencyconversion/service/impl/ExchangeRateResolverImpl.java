package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.provider.DirectExchangeRateProvider;
import com.projects.currencyconversion.provider.ExchangeRateProvider;
import com.projects.currencyconversion.provider.PivotCurrencyExchangeRateProvider;
import com.projects.currencyconversion.provider.ReverseExchangeRateProvider;
import com.projects.currencyconversion.service.ExchangeRateResolver;

import java.util.Optional;

public class ExchangeRateResolverImpl implements ExchangeRateResolver {

    private final ExchangeRateProvider provider;
    private static final ExchangeRateResolverImpl INSTANCE = new ExchangeRateResolverImpl();

    private ExchangeRateResolverImpl() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
        String pivotCurrencyCode = PropertiesUtil.get("exchange_rate.pivot_currency_code");

        provider = ExchangeRateProvider.link(
                new DirectExchangeRateProvider(exchangeRateDao),
                new ReverseExchangeRateProvider(exchangeRateDao),
                new PivotCurrencyExchangeRateProvider(exchangeRateDao, pivotCurrencyCode)
        );
    }

    @Override
    public Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        return provider.getExchangeRate(baseCurrencyCode, targetCurrencyCode);
    }

    public static ExchangeRateResolverImpl getInstance() {
        return INSTANCE;
    }
}
