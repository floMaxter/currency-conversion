package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.exception.ValidationException;
import com.projects.currencyconversion.mapper.impl.ExchangeRateRequestMapper;
import com.projects.currencyconversion.mapper.impl.ExchangeRateResponseMapper;
import com.projects.currencyconversion.service.ExchangeRateService;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.Validator;
import com.projects.currencyconversion.validator.impl.CoupleOfCurrencyCodeValidator;
import com.projects.currencyconversion.validator.impl.CreateExchangeRateValidator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final ExchangeRateServiceImpl INSTANCE = new ExchangeRateServiceImpl();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final ExchangeRateResponseMapper exchangeRateResponseMapper = ExchangeRateResponseMapper.getInstance();
    private final ExchangeRateRequestMapper exchangeRateRequestMapper = ExchangeRateRequestMapper.getInstance();
    private final Validator<String> coupleOfCurrencyCodeValidator = CoupleOfCurrencyCodeValidator.getInstance();
    private final CreateExchangeRateValidator createExchangeRateValidator = CreateExchangeRateValidator.getInstance();

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
        ValidationResult codesValidationResult = coupleOfCurrencyCodeValidator.isValid(coupleOfCode);
        if (!codesValidationResult.isValid()) {
            throw new ValidationException(codesValidationResult.getMessage());
        }

        int codeLength = Integer.parseInt(PropertiesUtil.get("db.currency.code.length"));
        String baseCurrencyCode = coupleOfCode.substring(0, codeLength);
        String targetCurrencyCode = coupleOfCode.substring(codeLength);

        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao
                .findByCoupleOfCurrencyCode(baseCurrencyCode, targetCurrencyCode);
        if (optionalExchangeRate.isEmpty()) {
            throw new NotFoundException("The exchange rate with this codes was not found: " + coupleOfCode);
        }

        return exchangeRateResponseMapper.toDto(optionalExchangeRate.get());
    }

    @Override
    public ExchangeRateResponseDto create(ExchangeRateRequestDto exchangeRateRequestDto) {
        // Получить exchangeRateRequestDto с полями String вместо Currency
        // Выполнить запрос в currencyDao findByCode() для base и target Currency
        // Проверить, что сущности найдены, если нет - кинуть исключение
        // Записать сущности в сущность ExchangeRate
        // Произвести сохранение сущности save(exchangeRate)
        // Вернуть ExchangeRateResponseDto

        ValidationResult validationResult = createExchangeRateValidator.isValid(exchangeRateRequestDto);
        if (!validationResult.isValid()) {
            throw new RuntimeException(String.valueOf(validationResult.getErrors()));
        }

        Optional<Currency> optionalBaseCurrency = currencyDao.findByCode(exchangeRateRequestDto.baseCurrencyCode());
        Optional<Currency> optionalTargetCurrency = currencyDao.findByCode(exchangeRateRequestDto.targetCurrencyCode());

        if (optionalBaseCurrency.isPresent() && optionalTargetCurrency.isPresent()) {
            ExchangeRate exchangeRate = ExchangeRate.builder()
                    .baseCurrency(optionalBaseCurrency.get())
                    .targetCurrency(optionalTargetCurrency.get())
                    .rate(Double.valueOf(exchangeRateRequestDto.rate()))
                    .build();

            ExchangeRate savedExchangeRate = exchangeRateDao.save(exchangeRate);
            return exchangeRateResponseMapper.toDto(savedExchangeRate);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public ExchangeRateResponseDto update(String coupleOfCode, String newRate) {
        // Извлечь из Dto rate
        // Найти exchangeRate по coupleOfCode
        // Занести в найденную сущность новый rate
        // Сохранить измененную сущность

//        String baseCurrencyCode = coupleOfCode.substring(0, 3);
//        String targetCurrencyCode = coupleOfCode.substring(3);
//
//        Optional<Currency> optionalBaseCurrency = currencyDao.findByCode(baseCurrencyCode);
//        Optional<Currency> optionalTargetCurrency = currencyDao.findByCode(targetCurrencyCode);
//        if (optionalBaseCurrency.isPresent() && optionalTargetCurrency.isPresent()) {
//            Currency baseCurrency = optionalBaseCurrency.get();
//            Currency targetCurrency = optionalTargetCurrency.get();
//
//            Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao
//                    .findByCoupleOfCurrencyCode(baseCurrency.getId(), targetCurrency.getId());
//            ExchangeRate findExchangeRate = optionalExchangeRate.get();
//            findExchangeRate.setRate(Double.valueOf(newRate));
//
//            ExchangeRate savedExchangeRate = exchangeRateDao.update(findExchangeRate);
//
//            return exchangeRateResponseMapper.toDto(savedExchangeRate);
//        } else {
//            throw new NoSuchElementException();
//        }
        return null;
    }
}
