package com.projects.currencyconversion;

import com.projects.currencyconversion.Utils.ConnectionManager;
import com.projects.currencyconversion.dao.CurrencyDao;
import com.projects.currencyconversion.entity.Currency;

import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        saveTest();
        findAllTest();
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
