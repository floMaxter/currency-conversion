package com.projects.currencyconversion.dto;

public record ExchangeRatesRequestDto(CurrencyRequestDto baseCurrency,
                                      CurrencyRequestDto targetCurrency,
                                      Double rate) {

    @Override
    public String toString() {
        return "ExchangeRatesRequestDto{" +
               "baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }

    public static class ExchangeRatesRequestDtoBuilder {

        private Long id;
        private CurrencyRequestDto baseCurrency;
        private CurrencyRequestDto targetCurrency;
        private Double rate;

        public ExchangeRatesRequestDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExchangeRatesRequestDtoBuilder baseCurrency(CurrencyRequestDto baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeRatesRequestDtoBuilder targetCurrency(CurrencyRequestDto targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRatesRequestDtoBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRatesRequestDto build() {
            return new ExchangeRatesRequestDto(baseCurrency, targetCurrency, rate);
        }
    }
}
