package com.projects.currencyconversion.entity;

public class ExchangeCurrency {

    private ExchangeRate exchangeRate;
    private Double amount;
    private Double convertedAmount;

    private ExchangeCurrency() {
    }

    public ExchangeCurrency(ExchangeRate exchangeRate, Double amount, Double convertedAmount) {
        this.exchangeRate = exchangeRate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public static ExchangeCurrencyBuilder builder() {
        return new ExchangeCurrencyBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeCurrency{" +
               "exchangeRate=" + exchangeRate +
               ", amount=" + amount +
               ", convertedAmount=" + convertedAmount +
               '}';
    }

    public static class ExchangeCurrencyBuilder {

        private ExchangeRate exchangeRate;
        private Double amount;
        private Double convertedAmount;

        public ExchangeCurrencyBuilder exchangeRate(ExchangeRate exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public ExchangeCurrencyBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public ExchangeCurrencyBuilder convertedAmount(Double convertedAmount) {
            this.convertedAmount = convertedAmount;
            return this;
        }

        public ExchangeCurrency build() {
            return new ExchangeCurrency(exchangeRate, amount, convertedAmount);
        }
    }
}
