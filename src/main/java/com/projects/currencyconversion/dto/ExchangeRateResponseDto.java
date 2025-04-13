package com.projects.currencyconversion.dto;

public record ExchangeRateResponseDto(Long id,
                                      CurrencyResponseDto baseCurrency,
                                      CurrencyResponseDto targetCurrency,
                                      Double rate) {

    public static ExchangeRateResponseDtoBuilder builder() {
        return new ExchangeRateResponseDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeRateResponseDto{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }

    public static class ExchangeRateResponseDtoBuilder {

        private Long id;
        private CurrencyResponseDto baseCurrency;
        private CurrencyResponseDto targetCurrency;
        private Double rate;

        public ExchangeRateResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExchangeRateResponseDtoBuilder baseCurrency(CurrencyResponseDto baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeRateResponseDtoBuilder targetCurrency(CurrencyResponseDto targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRateResponseDtoBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRateResponseDto build() {
            return new ExchangeRateResponseDto(id, baseCurrency, targetCurrency, rate);
        }
    }
}
