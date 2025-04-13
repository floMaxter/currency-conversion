package com.projects.currencyconversion.service.impl;

import com.projects.currencyconversion.Utils.RequestUtils;
import com.projects.currencyconversion.dto.ExchangeCurrencyRequestDto;
import com.projects.currencyconversion.dto.ExchangeCurrencyResponseDto;
import com.projects.currencyconversion.entity.ExchangeCurrency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.exception.NotFoundException;
import com.projects.currencyconversion.mapper.impl.ExchangeCurrencyResponseMapper;
import com.projects.currencyconversion.service.ExchangeCurrencyService;
import com.projects.currencyconversion.validator.Validator;
import com.projects.currencyconversion.validator.impl.ExchangeCurrencyValidator;

import java.util.Optional;

import static com.projects.currencyconversion.Utils.ValidationUtils.validate;

public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    private static final ExchangeCurrencyServiceImpl INSTANCE = new ExchangeCurrencyServiceImpl();
    private final ExchangeRateResolverImpl exchangeRateResolver = ExchangeRateResolverImpl.getInstance();
    private final ExchangeCurrencyResponseMapper exchangeCurrencyResponseMapper = ExchangeCurrencyResponseMapper.getInstance();
    private final Validator<ExchangeCurrencyRequestDto> exchangeCurrencyValidator = ExchangeCurrencyValidator.getInstance();

    private ExchangeCurrencyServiceImpl() {
    }

    public static ExchangeCurrencyServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeCurrencyResponseDto exchange(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        validate(exchangeCurrencyValidator.isValid(exchangeCurrencyRequestDto));

        return getExchangeCurrency(exchangeCurrencyRequestDto)
                .map(exchangeCurrencyResponseMapper::toDto)
                .orElseThrow(() -> new NotFoundException("It is impossible to exchange such currencies: "
                                                   + exchangeCurrencyRequestDto.baseCurrencyCode() + " to "
                                                   + exchangeCurrencyRequestDto.targetCurrencyCode()));
    }

    private Optional<ExchangeCurrency> getExchangeCurrency(ExchangeCurrencyRequestDto exchangeCurrencyRequestDto) {
        String baseCurrencyCode = exchangeCurrencyRequestDto.baseCurrencyCode();
        String targetCurrencyCode = exchangeCurrencyRequestDto.targetCurrencyCode();
        Optional<ExchangeRate> maybeExchangeRate = exchangeRateResolver.getExchangeRate(baseCurrencyCode, targetCurrencyCode);

        if (maybeExchangeRate.isPresent()) {
            ExchangeRate exchangeRate = maybeExchangeRate.get();
            Double amount = RequestUtils.getDouble(exchangeCurrencyRequestDto.amount());
            Double convertedAmount = RequestUtils.round(exchangeRate.getRate() * amount);

            return Optional.of(ExchangeCurrency.builder()
                    .exchangeRate(exchangeRate)
                    .amount(amount)
                    .convertedAmount(convertedAmount)
                    .build());
        }

        return Optional.empty();
    }
}
