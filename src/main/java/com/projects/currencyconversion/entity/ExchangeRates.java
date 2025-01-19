package com.projects.currencyconversion.entity;

public class ExchangeRates {

    private Long id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private Double rate;

    public ExchangeRates() {
    }

    public ExchangeRates(Long id, Currency baseCurrency, Currency targetCurrency, Double rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public static ExchangeRatesBuilder builder() {
        return new ExchangeRatesBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }

    public static class ExchangeRatesBuilder {

        private Long id;
        private Currency baseCurrency;
        private Currency targetCurrency;
        private Double rate;

        public ExchangeRatesBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExchangeRatesBuilder baseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeRatesBuilder targetCurrency(Currency targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRatesBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRates build() {
            return new ExchangeRates(id, baseCurrency, targetCurrency, rate);
        }
    }
}
