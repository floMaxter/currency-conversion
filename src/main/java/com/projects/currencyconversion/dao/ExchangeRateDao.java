package com.projects.currencyconversion.dao;

import com.projects.currencyconversion.Utils.ConnectionManager;
import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.entity.ExchangeRate;
import com.projects.currencyconversion.exception.AlreadyExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<Long, ExchangeRate> {

    private static final ExchangeRateDao INSTANCE = new ExchangeRateDao();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private static final String SAVE_SQL = """
            INSERT INTO t_exchange_rates (c_base_currency_id, c_target_currency_id, c_rate)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_ALL_SQL = """
            SELECT t_exchange_rates.id,
                   t_exchange_rates.c_base_currency_id,
                   t_exchange_rates.c_target_currency_id,
                   t_exchange_rates.c_rate
            FROM t_exchange_rates
            """;

    private static final String FIND_BY_COUPLE_OF_CODES_SQL = """
            SELECT
                er.id exchange_rate_id,
                bc.id base_currency_id,
                bc.c_code base_currency_code,
                bc.c_full_name base_currency_name,
                bc.c_sign base_currency_sign,
                tc.id target_currency_id,
                tc.c_code target_currency_code,
                tc.c_full_name target_currency_name,
                tc.c_sign target_currency_sign,
                er.c_rate exchange_rate
            FROM t_exchange_rates er
            LEFT JOIN t_currencies bc
                ON er.c_base_currency_id = bc.id
            LEFT JOIN t_currencies tc
                ON er.c_target_currency_id = tc.id
            WHERE bc.c_code = ? AND tc.c_code = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE t_exchange_rates
            SET c_base_currency_id = ?,
                c_target_currency_id = ?,
                c_rate = ?
            WHERE id = ?
            """;

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setLong(2, exchangeRate.getTargetCurrency().getId());
            preparedStatement.setDouble(3, exchangeRate.getRate());

            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                exchangeRate.setId(generatedKey.getLong(1));
            }

            return exchangeRate;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE") &&
                e.getErrorCode() == Integer.parseInt(PropertiesUtil.get("db.unique_error_code"))) {
                throw new AlreadyExistsException("The exchange rate for such currencies already exists: "
                                                 + exchangeRate.getBaseCurrency().getCode() + ", "
                                                 + exchangeRate.getTargetCurrency().getCode());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExchangeRate> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRate> exchangeRates = new ArrayList<>();
            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRateWithDaoLookup(resultSet));
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate buildExchangeRateWithDaoLookup(ResultSet resultSet) throws SQLException {
        Long baseCode = resultSet.getLong("c_base_currency_id");
        Long targetCode = resultSet.getLong("c_target_currency_id");
        return ExchangeRate.builder()
                .id(resultSet.getLong("id"))
                .baseCurrency(currencyDao.findById(baseCode)
                        .orElseThrow(() -> new RuntimeException("There is no currency with this code: " + baseCode)))
                .targetCurrency(currencyDao.findById(targetCode)
                        .orElseThrow(() -> new RuntimeException("There is no currency with this code: " + targetCode)))
                .rate(resultSet.getDouble("c_rate"))
                .build();
    }

    public Optional<ExchangeRate> findByCurrencyPair(String baseCurrencyCode, String targetCurrencyCode) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COUPLE_OF_CODES_SQL)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            ExchangeRate findExchangeRate = null;
            if (resultSet.next()) {
                findExchangeRate = mapExchangeRateFromResultSet(resultSet);
            }
            return Optional.ofNullable(findExchangeRate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate mapExchangeRateFromResultSet(ResultSet resultSet) throws SQLException {
        Currency baseCurrency = Currency.builder()
                .id(resultSet.getLong("base_currency_id"))
                .code(resultSet.getString("base_currency_code"))
                .fullName(resultSet.getString("base_currency_name"))
                .sign(resultSet.getString("base_currency_sign"))
                .build();
        Currency targetCurrency = Currency.builder()
                .id(resultSet.getLong("target_currency_id"))
                .code(resultSet.getString("target_currency_code"))
                .fullName(resultSet.getString("target_currency_name"))
                .sign(resultSet.getString("target_currency_sign"))
                .build();
        return ExchangeRate.builder()
                .id(resultSet.getLong("exchange_rate_id"))
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(resultSet.getDouble("exchange_rate"))
                .build();
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(UPDATE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setLong(2, exchangeRate.getTargetCurrency().getId());
            preparedStatement.setDouble(3, exchangeRate.getRate());
            preparedStatement.setLong(4, exchangeRate.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                exchangeRate.setId(generatedKeys.getLong(1));
            }
            return exchangeRate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
