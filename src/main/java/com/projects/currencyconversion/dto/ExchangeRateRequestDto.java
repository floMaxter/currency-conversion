package com.projects.currencyconversion.dto;

public record ExchangeRateRequestDto(CurrencyRequestDto baseCurrency,
                                     CurrencyRequestDto targetCurrency,
                                     Double rate) {

    @Override
    public String toString() {
        return "ExchangeRateRequestDto{" +
               "baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }

    public static class ExchangeRateRequestDtoBuilder {

        private CurrencyRequestDto baseCurrency;
        private CurrencyRequestDto targetCurrency;
        private Double rate;

        public ExchangeRateRequestDtoBuilder baseCurrency(CurrencyRequestDto baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeRateRequestDtoBuilder targetCurrency(CurrencyRequestDto targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRateRequestDtoBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRateRequestDto build() {
            return new ExchangeRateRequestDto(baseCurrency, targetCurrency, rate);
        }
    }
}
