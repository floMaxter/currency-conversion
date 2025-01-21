package com.projects.currencyconversion.dto;

public record ExchangeCurrencyResponseDto(CurrencyResponseDto baseCurrencyResponseDto,
                                          CurrencyResponseDto targetCurrencyResponseDto,
                                          Double rate,
                                          Double amount,
                                          Double convertedAmount) {

    public static ExchangeCurrencyResponseDtoBuilder builder() {
        return new ExchangeCurrencyResponseDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeCurrencyResponseDto{" +
               "baseCurrencyResponseDto=" + baseCurrencyResponseDto +
               ", targetCurrencyResponseDto=" + targetCurrencyResponseDto +
               ", rate=" + rate +
               ", amount=" + amount +
               ", convertedAmount=" + convertedAmount +
               '}';
    }

    public static class ExchangeCurrencyResponseDtoBuilder {
        private CurrencyResponseDto baseCurrencyResponseDto;
        private CurrencyResponseDto targetCurrencyResponseDto;
        private Double rate;
        private Double amount;
        private Double convertedAmount;

        public ExchangeCurrencyResponseDtoBuilder baseCurrencyResponseDto(
                CurrencyResponseDto baseCurrencyResponseDto) {
            this.baseCurrencyResponseDto = baseCurrencyResponseDto;
            return this;
        }

        public ExchangeCurrencyResponseDtoBuilder targetCurrencyResponseDto(
                CurrencyResponseDto targetCurrencyResponseDto) {
            this.targetCurrencyResponseDto = targetCurrencyResponseDto;
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
                    baseCurrencyResponseDto,
                    targetCurrencyResponseDto,
                    rate,
                    amount,
                    convertedAmount
            );
        }
    }
}
