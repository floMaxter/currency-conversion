package com.projects.currencyconversion.dto;

public record ExchangeRateRequestDto(String baseCurrencyCode,
                                     String targetCurrencyCode,
                                     String rate) {

    public static ExchangeRateRequestDtoBuilder builder() {
        return new ExchangeRateRequestDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeRateRequestDto{" +
               "baseCurrencyCode='" + baseCurrencyCode + '\'' +
               ", targetCurrencyCode='" + targetCurrencyCode + '\'' +
               ", rate='" + rate + '\'' +
               '}';
    }

    public static class ExchangeRateRequestDtoBuilder {

        private String baseCurrencyCode;
        private String targetCurrencyCode;
        private String rate;

        public ExchangeRateRequestDtoBuilder baseCurrencyCode(String baseCurrency) {
            this.baseCurrencyCode = baseCurrency;
            return this;
        }

        public ExchangeRateRequestDtoBuilder targetCurrencyCode(String targetCurrency) {
            this.targetCurrencyCode = targetCurrency;
            return this;
        }

        public ExchangeRateRequestDtoBuilder rate(String rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRateRequestDto build() {
            return new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode, rate);
        }
    }
}
