package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dto.CurrencyRequestDto;
import com.projects.currencyconversion.dto.CurrencyResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.mapper.impl.CurrencyRequestMapper;
import com.projects.currencyconversion.mapper.impl.CurrencyResponseMapper;
import com.projects.currencyconversion.service.CurrencyService;
import com.projects.currencyconversion.validator.Validator;
import com.projects.currencyconversion.validator.impl.CreateCurrencyValidator;
import com.projects.currencyconversion.validator.impl.CurrencyCodeValidator;

import java.util.List;

import static com.projects.currencyconversion.validator.ValidationUtils.validate;

public class CurrencyServiceImpl implements CurrencyService {

    private static final CurrencyServiceImpl INSTANCE = new CurrencyServiceImpl();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final CurrencyRequestMapper currencyRequestMapper = CurrencyRequestMapper.getInstance();
    private final CurrencyResponseMapper currencyResponseMapper = CurrencyResponseMapper.getInstance();
    private final Validator<String> currencyCodeValidator = CurrencyCodeValidator.getInstance();
    private final Validator<CurrencyRequestDto> createCurrencyValidator = CreateCurrencyValidator.getInstance();

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
    public CurrencyResponseDto findByCode(String code) {
        validate(currencyCodeValidator.isValid(code));

        return currencyDao.findByCode(code)
                .map(currencyResponseMapper::toDto)
                .orElseThrow(() -> new NotFoundException("The currency with this code was not found: " + code));
    }

    @Override
    public CurrencyResponseDto create(CurrencyRequestDto currencyRequestDto) {
        validate(createCurrencyValidator.isValid(currencyRequestDto));

        Currency currency = currencyRequestMapper.toEntity(currencyRequestDto);
        Currency savedCurrency = currencyDao.save(currency);
        return currencyResponseMapper.toDto(savedCurrency);
    }
}
