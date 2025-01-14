package com.projects.currencyconversion.dao;

import com.projects.currencyconversion.Utils.ConnectionManager;
import com.projects.currencyconversion.entity.ExchangeRates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRatesDao implements Dao<Long, ExchangeRates> {

    private static final ExchangeRatesDao INSTANCE = new ExchangeRatesDao();

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

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE t_exchange_rates
            SET c_base_currency_id = ?,
                c_target_currency_id = ?,
                c_rate = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM t_exchange_rates
            WHERE id = ?
            """;


    private ExchangeRatesDao() {
    }

    public static ExchangeRatesDao getInstance() {
        return INSTANCE;
    }

    @Override
    public ExchangeRates save(ExchangeRates exchangeRates) {
        try (Connection connection = ConnectionManager.get()) {
            return save(exchangeRates, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ExchangeRates save(ExchangeRates exchangeRates, Connection connection) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, exchangeRates.getBaseCurrency().getId());
            preparedStatement.setLong(2, exchangeRates.getTargetCurrency().getId());
            preparedStatement.setDouble(3, exchangeRates.getRate());

            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                exchangeRates.setId(generatedKey.getLong(1));
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExchangeRates> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            return findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExchangeRates> findAll(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<ExchangeRates> exchangeRates = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRates(resultSet));
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ExchangeRates> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ExchangeRates> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ExchangeRates findExchangeRates = null;
            if (resultSet.next()) {
                findExchangeRates = buildExchangeRates(resultSet);
            }
            return Optional.ofNullable(findExchangeRates);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRates buildExchangeRates(ResultSet resultSet) throws SQLException {
        return new ExchangeRates(
                resultSet.getLong("id"),
                currencyDao.findById(resultSet.getLong("c_base_currency_id")).orElse(null),
                currencyDao.findById(resultSet.getLong("c_target_currency_id")).orElse(null),
                resultSet.getDouble("c_rate")

        );
    }

    @Override
    public void update(ExchangeRates exchangeRates) {
        try (Connection connection = ConnectionManager.get()) {
            update(exchangeRates, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ExchangeRates exchangeRates, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, exchangeRates.getBaseCurrency().getId());
            preparedStatement.setLong(2, exchangeRates.getTargetCurrency().getId());
            preparedStatement.setDouble(3, exchangeRates.getRate());
            preparedStatement.setLong(4, exchangeRates.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return delete(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
