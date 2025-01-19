package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.mapper.impl.ExchangeRateResponseMapper;
import com.projects.currencyconversion.service.ExchangeRateService;

import java.util.List;
import java.util.Optional;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final ExchangeRateServiceImpl INSTANCE = new ExchangeRateServiceImpl();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
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
    public Optional<ExchangeRateResponseDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ExchangeRateResponseDto create(ExchangeRateRequestDto exchangeRateRequestDto) {
        return null;
    }
}
