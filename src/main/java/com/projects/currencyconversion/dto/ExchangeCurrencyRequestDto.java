package com.projects.currencyconversion.dto;

public record ExchangeCurrencyRequestDto(String baseCurrencyCode,
                                         String targetCurrencyCode,
                                         String amount) {

    public static ExchangeCurrencyRequestDtoBuilder builder() {
        return new ExchangeCurrencyRequestDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeCurrencyRequestDto{" +
               "baseCurrencyCode='" + baseCurrencyCode + '\'' +
               ", targetCurrencyCode='" + targetCurrencyCode + '\'' +
               ", amount=" + amount +
               '}';
    }

    public static class ExchangeCurrencyRequestDtoBuilder {

        private String baseCurrencyCode;
        private String targetCurrencyCode;
        private String amount;

        public ExchangeCurrencyRequestDtoBuilder baseCurrencyCode(String baseCurrencyCode) {
            this.baseCurrencyCode = baseCurrencyCode;
            return this;
        }

        public ExchangeCurrencyRequestDtoBuilder targetCurrencyCode(String targetCurrencyCode) {
            this.targetCurrencyCode = targetCurrencyCode;
            return this;
        }

        public ExchangeCurrencyRequestDtoBuilder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public ExchangeCurrencyRequestDto build() {
            return new ExchangeCurrencyRequestDto(baseCurrencyCode, targetCurrencyCode, amount);
        }
    }
}
