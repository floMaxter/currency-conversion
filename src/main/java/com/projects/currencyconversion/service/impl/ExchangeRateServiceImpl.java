package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.mapper.impl.ExchangeRateResponseMapper;
import com.projects.currencyconversion.service.ExchangeRateService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final ExchangeRateServiceImpl INSTANCE = new ExchangeRateServiceImpl();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final ExchangeRateResponseMapper exchangeRateResponseMapper = ExchangeRateResponseMapper.getInstance();

    private ExchangeRateServiceImpl() {
    }

    public static ExchangeRateServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ExchangeRateResponseDto> findAll() {
        List<ExchangeRate> findExchangeRates = exchangeRateDao.findAll();
        return exchangeRateResponseMapper.toDto(findExchangeRates);
    }

    @Override
    public ExchangeRateResponseDto findByCoupleOfCode(String coupleOfCode) {
        // Парсинг строки на два кода
        // Валидация каждого кода
        // Получение сущностей по коду из CurrencyDao
        // Проверка, что сущности существуют
        // Отправка ID в ExchangeRateDao
        // Получение сущности ExchangeRate из ExchangeRateDao
        // Проверка, что сущность существует

        String baseCurrencyCode = coupleOfCode.substring(0, 3);
        String targetCurrencyCode = coupleOfCode.substring(3);

        Optional<Currency> optionalBaseCurrency = currencyDao.findByCode(baseCurrencyCode);
        Optional<Currency> optionalTargetCurrency = currencyDao.findByCode(targetCurrencyCode);
        if (optionalBaseCurrency.isPresent() && optionalTargetCurrency.isPresent()) {
            Currency baseCurrency = optionalBaseCurrency.get();
            Currency targetCurrency = optionalTargetCurrency.get();

            Optional<ExchangeRate> exchangeRate
                    = exchangeRateDao.findByCoupleOfCurrencyId(baseCurrency.getId(), targetCurrency.getId());
            return exchangeRateResponseMapper.toDto(exchangeRate.get());
        } else {
            throw new NoSuchElementException("There ara no pairs with such codes: " + coupleOfCode);
        }
    }

    @Override
    public ExchangeRateResponseDto create(ExchangeRateRequestDto exchangeRateRequestDto) {
        return null;
    }
}
