package ru.job4j.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.sql.*;

public abstract class StoreSQL implements AutoCloseable, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(StoreSQL.class);
    private Config config;
    private Connection connection;

    public StoreSQL(Config config) throws SQLException {
        this.config = config;
        try {
            if (!init()) {
                throw new SQLException("Connection is not created!");
            }
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private boolean init() throws SQLException, ClassNotFoundException {
        String url  = config.get("jdbc.url");
        Class.forName(config.get("jdbc.driver"));
        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS vacancy (id SERIAL PRIMARY KEY NOT NULL, name VARCHAR(200), text CHAR, link CHAR)");
            }

        }
        createConnection();
        return this.connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(config.get("jdbc.driver"));
        DriverManager.getDrivers();
        connection = DriverManager.getConnection(
                config.get("jdbc.url"),
                config.get("jdbc.username"),
                config.get("jdbc.password")
        );
    }

    public abstract void execute() throws SQLException;

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
