package com.projects.currencyconversion;

import com.projects.currencyconversion.Utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            System.out.println(connection.getTransactionIsolation());
        } finally {
            ConnectionManager.closePool();
        }
    }
}
