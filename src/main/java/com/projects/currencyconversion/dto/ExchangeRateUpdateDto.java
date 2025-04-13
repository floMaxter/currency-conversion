package com.projects.currencyconversion.dto;

public record ExchangeRateUpdateDto(String coupleOfCode, String rate) {

    public static ExchangeRateUpdateDtoBuilder builder() {
        return new ExchangeRateUpdateDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeRateUpdateDto{" +
               "coupleOfCode='" + coupleOfCode + '\'' +
               ", rate='" + rate + '\'' +
               '}';
    }

    public static class ExchangeRateUpdateDtoBuilder {

        private String coupleOfCode;
        private String rate;

        public ExchangeRateUpdateDtoBuilder coupleOfCode(String coupleOfCode) {
            this.coupleOfCode = coupleOfCode;
            return this;
        }

        public ExchangeRateUpdateDtoBuilder rate(String rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRateUpdateDto build() {
            return new ExchangeRateUpdateDto(coupleOfCode, rate);
        }
    }
}
