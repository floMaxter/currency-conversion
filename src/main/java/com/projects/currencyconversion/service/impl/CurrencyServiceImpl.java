package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.mapper.CurrencyRequestMapper;
import com.projects.currencyconversion.mapper.CurrencyResponseMapper;
import com.projects.currencyconversion.service.CurrencyService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CurrencyServiceImpl implements CurrencyService {

    private static final CurrencyServiceImpl INSTANCE = new CurrencyServiceImpl();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final CurrencyRequestMapper currencyRequestMapper = CurrencyRequestMapper.getInstance();
    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();

    public CurrencyServiceImpl() {
    }

    public static CurrencyServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<CurrencyResponseDto> findAll() {
        return currencyDao.findAll().stream()
                .map(currency -> new CurrencyResponseDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                )).collect(Collectors.toList());
    }

    @Override
    public CurrencyResponseDto findById(Long id) {
        return currencyDao.findById(id)
                .map(currency -> new CurrencyResponseDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                ))
                .orElseThrow(() -> new NoSuchElementException("The currency with the id = " + id + "wasn't found"));
    }

    @Override
    public CurrencyResponseDto findByCode(String code) {
        return currencyDao.findByCode(code)
                .map(currency -> new CurrencyResponseDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                ))
                .orElseThrow(() -> new NoSuchElementException("The currency with the code = " + code + "wasn't found"));
    }

    @Override
    public CurrencyResponseDto create(CurrencyRequestDto currencyRequestDto) {
        Currency currency = currencyRequestMapper.toEntity(currencyRequestDto);
        Currency savedCurrency = currencyDao.save(currency);
        return currencyResponseMapper.toDto(savedCurrency);
    }
}
