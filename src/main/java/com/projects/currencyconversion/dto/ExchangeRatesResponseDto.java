package com.projects.currencyconversion.dto;

public record ExchangeRatesResponseDto(Long id,
                                       CurrencyResponseDto baseCurrency,
                                       CurrencyResponseDto targetCurrency,
                                       Double rate) {

    public static ExchangeRatesResponseDtoBuilder builder() {
        return new ExchangeRatesResponseDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeRatesResponseDto{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }

    public static class ExchangeRatesResponseDtoBuilder {

        private Long id;
        private CurrencyResponseDto baseCurrency;
        private CurrencyResponseDto targetCurrency;
        private Double rate;

        public ExchangeRatesResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExchangeRatesResponseDtoBuilder baseCurrency(CurrencyResponseDto baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeRatesResponseDtoBuilder targetCurrency(CurrencyResponseDto targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRatesResponseDtoBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRatesResponseDto build() {
            return new ExchangeRatesResponseDto(id, baseCurrency, targetCurrency, rate);
        }
    }
}
