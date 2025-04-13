package com.projects.currencyconversion.dto;

public record ExchangeCurrencyResponseDto(CurrencyResponseDto baseCurrency,
                                          CurrencyResponseDto targetCurrency,
                                          Double rate,
                                          Double amount,
                                          Double convertedAmount) {

    public static ExchangeCurrencyResponseDtoBuilder builder() {
        return new ExchangeCurrencyResponseDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeCurrencyResponseDto{" +
               "baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               ", amount=" + amount +
               ", convertedAmount=" + convertedAmount +
               '}';
    }

    public static class ExchangeCurrencyResponseDtoBuilder {
        private CurrencyResponseDto baseCurrency;
        private CurrencyResponseDto targetCurrency;
        private Double rate;
        private Double amount;
        private Double convertedAmount;

        public ExchangeCurrencyResponseDtoBuilder baseCurrencyResponseDto(CurrencyResponseDto baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeCurrencyResponseDtoBuilder targetCurrencyResponseDto(CurrencyResponseDto targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeCurrencyResponseDtoBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeCurrencyResponseDtoBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public ExchangeCurrencyResponseDtoBuilder convertedAmount(Double convertedAmount) {
            this.convertedAmount = convertedAmount;
            return this;
        }

        public ExchangeCurrencyResponseDto build() {
            return new ExchangeCurrencyResponseDto(
                    baseCurrency,
                    targetCurrency,
                    rate,
                    amount,
                    convertedAmount
            );
        }
    }
}
