package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.mapper.impl.CurrencyRequestMapper;
import com.projects.currencyconversion.mapper.impl.CurrencyResponseMapper;
import com.projects.currencyconversion.service.CurrencyService;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.impl.CreateCurrencyValidator;

import java.util.List;
import java.util.NoSuchElementException;

public class CurrencyServiceImpl implements CurrencyService {

    private static final CurrencyServiceImpl INSTANCE = new CurrencyServiceImpl();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final CurrencyRequestMapper currencyRequestMapper = CurrencyRequestMapper.getInstance();
    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();
    private final CreateCurrencyValidator createCurrencyValidator = CreateCurrencyValidator.getInstance();

    public CurrencyServiceImpl() {
    }

    public static CurrencyServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<CurrencyResponseDto> findAll() {
        List<Currency> findCurrencies = currencyDao.findAll();
        return currencyResponseMapper.toDto(findCurrencies);
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
        ValidationResult validationResult = createCurrencyValidator.isValid(currencyRequestDto);
        if (!validationResult.isValid()) {
            throw new RuntimeException(String.valueOf(validationResult.getErrors()));
        }
        Currency currency = currencyRequestMapper.toEntity(currencyRequestDto);
        Currency savedCurrency = currencyDao.save(currency);
        return currencyResponseMapper.toDto(savedCurrency);
    }
}
