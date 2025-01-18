package com.projects.currencyconversion;

import com.projects.currencyconversion.Utils.ConnectionManager;
import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dao.ExchangeRatesDao;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeRates;

import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        saveTest();
    }

    private static void deleteExchangeRatesTest() {
        ExchangeRatesDao exchangeRatesDao = ExchangeRatesDao.getInstance();
        var deleteResult = exchangeRatesDao.delete(5L);
        System.out.println(deleteResult);
    }

    private static void updateExchangeRatesTest() {
        ExchangeRatesDao exchangeRatesDao = ExchangeRatesDao.getInstance();
        Optional<ExchangeRates> optionalExchangeRates = exchangeRatesDao.findById(1L);
        System.out.println(optionalExchangeRates);

        optionalExchangeRates.ifPresent(exchangeRates -> {
            exchangeRates.setRate(0.0098);
            exchangeRatesDao.update(exchangeRates);
        });
    }

    private static void findByIdExchangeRates() {
        ExchangeRatesDao exchangeRatesDao = ExchangeRatesDao.getInstance();
        Optional<ExchangeRates> optionalExchangeRates = exchangeRatesDao.findById(1L);
        System.out.println(optionalExchangeRates);
    }

    private static void findAllExchangeRatesTest() {
        ExchangeRatesDao exchangeRatesDao = ExchangeRatesDao.getInstance();
        System.out.println(exchangeRatesDao.findAll());
    }

    private static void saveTestExchangeRate() {
        ExchangeRatesDao exchangeRatesDao = ExchangeRatesDao.getInstance();

        Currency currency1 = new Currency();
        currency1.setId(12L);
        currency1.setCode("T1");
        currency1.setFullName("Test1");
        currency1.setSign("T1");

        Currency currency2 = new Currency();
        currency2.setId(13L);
        currency2.setCode("T2");
        currency2.setFullName("Test2");
        currency2.setSign("T2");

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setBaseCurrency(currency1);
        exchangeRates.setTargetCurrency(currency2);
        exchangeRates.setRate(0.9);

        var savedExchangeRates = exchangeRatesDao.save(exchangeRates);
        System.out.println(savedExchangeRates);
    }

    private static void findAllTest() {
        CurrencyDao currencyDao = CurrencyDao.getInstance();
        System.out.println(currencyDao.findAll());
    }

    private static void updateTest() {
        CurrencyDao currencyDao = CurrencyDao.getInstance();
        Optional<Currency> optionalCurrency = currencyDao.findById(1L);
        System.out.println(optionalCurrency);

        optionalCurrency.ifPresent(currency -> {
            currency.setFullName("US Dollar");
            currencyDao.update(currency);
        });
    }

    private static void saveTest() {
        CurrencyDao currencyDao = CurrencyDao.getInstance();
        Currency currency = new Currency();
        currency.setCode("EUR");
        currency.setFullName("Euro");
        currency.setSign("€");

        Currency savedCurrency = currencyDao.save(currency);
        System.out.println(savedCurrency);
    }

    private static void deleteTest() {
        CurrencyDao currencyDao = CurrencyDao.getInstance();
        var deletedResult = currencyDao.delete(8L);
        System.out.println(deletedResult);
    }

    private static void test1() {
        String sql = """
                CREATE TABLE IF NOT EXISTS info(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    data VARCHAR NOT NULL
                );
                """;

        String sql_drop = """
                DROP TABLE IF EXISTS info;
                """;

        String sql_insert = """
                INSERT INTO info (data)
                VALUES ('Test2')
                """;

        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());
            var executeResult = statement.execute(sql_drop);
            System.out.println(executeResult);
            System.out.println(statement.getUpdateCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closePool();
        }
    }
}
