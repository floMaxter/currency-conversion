package com.projects.currencyconversion.entity;

public class ExchangeRate {

    private Long id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private Double rate;

    private ExchangeRate() {
    }

    public ExchangeRate(Long id, Currency baseCurrency, Currency targetCurrency, Double rate) {
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

    public static ExchangeRateBuilder builder() {
        return new ExchangeRateBuilder();
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }

    public static class ExchangeRateBuilder {

        private Long id;
        private Currency baseCurrency;
        private Currency targetCurrency;
        private Double rate;

        public ExchangeRateBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExchangeRateBuilder baseCurrency(Currency baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public ExchangeRateBuilder targetCurrency(Currency targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRateBuilder rate(Double rate) {
            this.rate = rate;
            return this;
        }

        public ExchangeRate build() {
            return new ExchangeRate(id, baseCurrency, targetCurrency, rate);
        }
    }
}
