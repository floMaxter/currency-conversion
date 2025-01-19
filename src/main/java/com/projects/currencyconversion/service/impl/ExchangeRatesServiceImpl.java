package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.ExchangeRatesDao;
import com.projects.currencyconversion.dto.ExchangeRatesRequestDto;
import com.projects.currencyconversion.dto.ExchangeRatesResponseDto;
import com.projects.currencyconversion.entity.ExchangeRates;
import com.projects.currencyconversion.mapper.impl.ExchangeRatesResponseMapper;
import com.projects.currencyconversion.service.ExchangeRatesService;

import java.util.List;
import java.util.Optional;

public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final ExchangeRatesServiceImpl INSTANCE = new ExchangeRatesServiceImpl();
    private final ExchangeRatesDao exchangeRatesDao = ExchangeRatesDao.getInstance();
    private final ExchangeRatesResponseMapper exchangeRatesResponseMapper = ExchangeRatesResponseMapper.getInstance();

    private ExchangeRatesServiceImpl() {
    }

    public static ExchangeRatesServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ExchangeRatesResponseDto> findAll() {
        List<ExchangeRates> findExchangeRates = exchangeRatesDao.findAll();
        return exchangeRatesResponseMapper.toDto(findExchangeRates);
    }

    @Override
    public Optional<ExchangeRatesResponseDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ExchangeRatesResponseDto create(ExchangeRatesRequestDto exchangeRatesRequestDto) {
        return null;
    }
}
