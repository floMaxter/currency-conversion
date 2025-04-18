package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.dto.ExchangeRateRequestDto;
import com.projects.currencyconversion.dto.ExchangeRateResponseDto;
import com.projects.currencyconversion.dto.ExchangeRateUpdateDto;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.mapper.impl.ExchangeRateResponseMapper;
import com.projects.currencyconversion.mapper.impl.ExchangeRateUpdateMapper;
import com.projects.currencyconversion.service.ExchangeRateService;
import com.projects.currencyconversion.validator.Validator;
import com.projects.currencyconversion.validator.impl.CurrencyPairValidator;
import com.projects.currencyconversion.validator.impl.ExchangeRateRequestDtoValidator;

import java.util.List;

import static com.projects.currencyconversion.Utils.ValidationUtils.validate;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final ExchangeRateServiceImpl INSTANCE = new ExchangeRateServiceImpl();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final ExchangeRateResponseMapper exchangeRateResponseMapper = ExchangeRateResponseMapper.getInstance();
    private final ExchangeRateUpdateMapper exchangeRateUpdateMapper = ExchangeRateUpdateMapper.getInstance();
    private final Validator<String> currencyPairValidator = CurrencyPairValidator.getInstance();
    private final Validator<ExchangeRateRequestDto> createExchangeRateValidator = ExchangeRateRequestDtoValidator.getInstance();
    private static final int CODE_LENGTH = Integer.parseInt(PropertiesUtil.get("db.currency.code.length"));

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
        validate(currencyPairValidator.isValid(coupleOfCode));

        String baseCurrencyCode = coupleOfCode.substring(0, CODE_LENGTH);
        String targetCurrencyCode = coupleOfCode.substring(CODE_LENGTH);

        return exchangeRateDao
                .findByCurrencyPair(baseCurrencyCode, targetCurrencyCode)
                .map(exchangeRateResponseMapper::toDto)
                .orElseThrow(() -> new NotFoundException("The exchange rate with this codes was not found: " + coupleOfCode));
    }

    @Override
    public ExchangeRateResponseDto create(ExchangeRateRequestDto exchangeRateRequestDto) {
        validate(createExchangeRateValidator.isValid(exchangeRateRequestDto));

        ExchangeRate newExchangeRate = buildExchangeRate(exchangeRateRequestDto);
        ExchangeRate savedExchangeRate = exchangeRateDao.save(newExchangeRate);
        return exchangeRateResponseMapper.toDto(savedExchangeRate);
    }

    @Override
    public ExchangeRateResponseDto update(ExchangeRateUpdateDto exchangeRateUpdateDto) {
        ExchangeRateRequestDto exchangeRateRequestDto = exchangeRateUpdateMapper.toDto(exchangeRateUpdateDto);
        validate(createExchangeRateValidator.isValid(exchangeRateRequestDto));

        ExchangeRate existingExchangeRate = getExchangeRateOrThrow(
                exchangeRateRequestDto.baseCurrencyCode(),
                exchangeRateRequestDto.targetCurrencyCode()
        );
        existingExchangeRate.setRate(RequestUtils.getDouble(exchangeRateRequestDto.rate()));
        ExchangeRate updatedExchangeRate = exchangeRateDao.update(existingExchangeRate);
        return exchangeRateResponseMapper.toDto(updatedExchangeRate);
    }

    private ExchangeRate buildExchangeRate(ExchangeRateRequestDto exchangeRateRequestDto) {
        Currency baseCurrency = getCurrencyOrThrow(exchangeRateRequestDto.baseCurrencyCode());
        Currency targetCurrency = getCurrencyOrThrow(exchangeRateRequestDto.targetCurrencyCode());
        Double rate = RequestUtils.getDouble(exchangeRateRequestDto.rate());

        return ExchangeRate.builder()
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(rate)
                .build();
    }

    private Currency getCurrencyOrThrow(String code) {
        return currencyDao.findByCode(code)
                .orElseThrow(() -> new NotFoundException("There is no currency with this code: " + code));
    }

    private ExchangeRate getExchangeRateOrThrow(String baseCurrencyCode, String targetCurrencyCode) {
        return exchangeRateDao.findByCurrencyPair(baseCurrencyCode, targetCurrencyCode)
                .orElseThrow(() -> new NotFoundException("There is no exchange rate with such currency codes: "
                                                         + baseCurrencyCode + ", " + targetCurrencyCode));
    }
}
