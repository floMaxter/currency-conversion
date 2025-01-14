package com.projects.currencyconversion.Utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {

    private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    private static final String URL_KEY = "db.url";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }

    private ConnectionManager() {
    }

    private static void loadDriver() {
        try {
            Class.forName(SQLITE_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesConnectionUtil.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Connection connection = openConnection();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection openConnection() {
        try {
            return DriverManager.getConnection(PropertiesConnectionUtil.get(URL_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        try {
            for (Connection connection : sourceConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
