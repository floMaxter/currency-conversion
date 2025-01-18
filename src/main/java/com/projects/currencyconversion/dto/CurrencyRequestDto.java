package com.projects.currencyconversion.dto;

public record CurrencyRequestDto(String code, String name, String sign) {

    public static CurrencyRequestDtoBuilder builder() {
        return new CurrencyRequestDtoBuilder();
    }

    @Override
    public String toString() {
        return "CurrencyRequestDto{" +
               "code='" + code + '\'' +
               ", name='" + name + '\'' +
               ", sign='" + sign + '\'' +
               '}';
    }

    public static class CurrencyRequestDtoBuilder {

        private String code;
        private String name;
        private String sign;

        public CurrencyRequestDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CurrencyRequestDtoBuilder code(String code) {
            this.code = code;
            return this;
        }

        public CurrencyRequestDtoBuilder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public CurrencyRequestDto build() {
            return new CurrencyRequestDto(code, name, sign);
        }
    }
}
