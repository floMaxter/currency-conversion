package com.projects.currencyconversion;

import com.projects.currencyconversion.Utils.ConnectionManager;
import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.dao.ExchangeRateDao;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.validator.ValidationResult;
import com.projects.currencyconversion.validator.impl.CoupleOfCurrencyCodeValidator;

import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        findExchangeRateDaoTest();
    }

    private static void findExchangeRateDaoTest() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
        var findExchangeRate = exchangeRateDao.findByCoupleOfCurrencyCode("USD", "RUB");
        findExchangeRate.ifPresent(System.out::println);
    }

    private static void coupleOfCodeValidationTest() {
        CoupleOfCurrencyCodeValidator validator = CoupleOfCurrencyCodeValidator.getInstance();
        ValidationResult validationResult = validator.isValid("RUBUSDD");
        System.out.println(validationResult.isValid());
    }

    private static void deleteExchangeRatesTest() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
        var deleteResult = exchangeRateDao.delete(5L);
        System.out.println(deleteResult);
    }

    private static void updateExchangeRatesTest() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
        Optional<ExchangeRate> optionalExchangeRates = exchangeRateDao.findById(1L);
        System.out.println(optionalExchangeRates);

        optionalExchangeRates.ifPresent(exchangeRates -> {
            exchangeRates.setRate(0.0098);
            exchangeRateDao.update(exchangeRates);
        });
    }

    private static void findByIdExchangeRates() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
        Optional<ExchangeRate> optionalExchangeRates = exchangeRateDao.findById(1L);
        System.out.println(optionalExchangeRates);
    }

    private static void findAllExchangeRatesTest() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
        System.out.println(exchangeRateDao.findAll());
    }

    private static void saveTestExchangeRate() {
        ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

        Currency currency1 = Currency.builder()
                .id(12L)
                .code("T1")
                .fullName("Test1")
                .sign("T1")
                .build();

        Currency currency2 = Currency.builder()
                .id(13L)
                .code("T2")
                .fullName("Test2")
                .sign("T2")
                .build();

        ExchangeRate exchangeRate = ExchangeRate.builder()
                .baseCurrency(currency1)
                .targetCurrency(currency2)
                .rate(0.9)
                .build();

        var savedExchangeRates = exchangeRateDao.save(exchangeRate);
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
        Currency currency = Currency.builder()
                .code("EUR")
                .fullName("Euro")
                .sign("€")
                .build();

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
