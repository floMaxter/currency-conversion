package com.projects.currencyconversion.entity;

public record CurrencyPair(String baseCurrencyCode, String targetCurrencyCode) {

    public static CurrencyPairBuilder builder() {
        return new CurrencyPairBuilder();
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
               "baseCurrencyCode='" + baseCurrencyCode + '\'' +
               ", targetCurrencyCode='" + targetCurrencyCode + '\'' +
               '}';
    }

    public static class CurrencyPairBuilder {

        private String baseCurrencyCode;
        private String targetCurrencyCode;

        public CurrencyPairBuilder baseCurrencyCode(String baseCurrencyCode) {
            this.baseCurrencyCode = baseCurrencyCode;
            return this;
        }

        public CurrencyPairBuilder targetCurrencyCode(String targetCurrencyCode) {
            this.targetCurrencyCode = targetCurrencyCode;
            return this;
        }

        public CurrencyPair build() {
            return new CurrencyPair(baseCurrencyCode, targetCurrencyCode);
        }
    }
}
