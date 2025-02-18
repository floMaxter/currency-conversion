package com.projects.currencyconversion.dto;

public record CurrencyResponseDto(Long id, String name, String code, String sign) {

    public static CurrencyResponseDtoBuilder builder() {
        return new CurrencyResponseDtoBuilder();
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
               "id=" + id +
               ", code='" + code + '\'' +
               ", fullName='" + name + '\'' +
               ", sign='" + sign + '\'' +
               '}';
    }

    public static class CurrencyResponseDtoBuilder {

        private Long id;
        private String code;
        private String name;
        private String sign;


        public CurrencyResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CurrencyResponseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CurrencyResponseDtoBuilder code(String code) {
            this.code = code;
            return this;
        }

        public CurrencyResponseDtoBuilder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public CurrencyResponseDto build() {
            return new CurrencyResponseDto(id, name, code, sign);
        }
    }
}
