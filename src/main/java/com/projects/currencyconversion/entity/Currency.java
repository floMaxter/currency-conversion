package com.projects.currencyconversion.entity;

public class Currency {

    private Long id;
    private String fullName;
    private String code;
    private String sign;

    private Currency() {
    }

    public Currency(Long id, String fullName, String code, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static CurrencyBuilder builder() {
        return new CurrencyBuilder();
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public static class CurrencyBuilder {

        private Long id;
        private String fullName;
        private String code;
        private String sign;

        public CurrencyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CurrencyBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public CurrencyBuilder code(String code) {
            this.code = code;
            return this;
        }

        public CurrencyBuilder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public Currency build() {
            return new Currency(id, fullName, code, sign);
        }
    }
}
