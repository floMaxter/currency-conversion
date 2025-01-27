package com.projects.currencyconversion.dao;

import com.projects.currencyconversion.Utils.ConnectionManager;
import com.projects.currencyconversion.Utils.PropertiesUtil;
import com.projects.currencyconversion.entity.Currency;
import com.projects.currencyconversion.exception.AlreadyExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<Long, Currency> {

    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String SAVE_SQL = """
            INSERT INTO t_currencies (c_code, c_full_name, c_sign)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id,
                c_code,
                c_full_name,
                c_sign
            FROM main.t_currencies
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
            WHERE c_code = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE t_currencies
            SET c_code = ?, c_full_name = ?, c_sign = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM t_currencies
            WHERE ID = ?
            """;

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Currency save(Currency currency) {
        try (Connection connection = ConnectionManager.get()) {
            return save(currency, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency save(Currency currency, Connection connection) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                currency.setId(generatedKeys.getLong(1));
            }
            return currency;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE") &&
                e.getErrorCode() == Integer.parseInt(PropertiesUtil.get("db.unique_error_code"))) {
                throw new AlreadyExistsException("The currency with this code already exists: " + currency.getCode());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Currency> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            return findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Currency> findAll(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Currency> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Currency findCurrency = null;
            if (resultSet.next()) {
                findCurrency = buildCurrency(resultSet);
            }
            return Optional.ofNullable(findCurrency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Currency> findByCode(String code) {
        try (Connection connection = ConnectionManager.get()) {
            return findByCode(code, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Currency> findByCode(String code, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE_SQL)) {
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            Currency findCurrency = null;
            if (resultSet.next()) {
                findCurrency = buildCurrency(resultSet);
            }
            return Optional.ofNullable(findCurrency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Currency buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getLong("id"),
                resultSet.getString("c_code"),
                resultSet.getString("c_full_name"),
                resultSet.getString("c_code")
        );
    }

    @Override
    public Currency update(Currency currency) {
        try (Connection connection = ConnectionManager.get()) {
            return update(currency, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency update(Currency currency, Connection connection) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.setLong(4, currency.getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                currency.setId(generatedKeys.getLong(1));
            }
            return currency;
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
