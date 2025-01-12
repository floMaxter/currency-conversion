package com.projects.currencyconversion.dao;

public class CurrencyDao {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private CurrencyDao() {
    }

    public void delete(Long id) {

    }


    public static CurrencyDao getInstance() {
        return INSTANCE;
    }
}
