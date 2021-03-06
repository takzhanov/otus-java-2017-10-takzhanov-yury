package io.github.takzhanov.umbrella.hw09.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Предоставляет обработанные результаты обращения к базе
 * похож на org.apache.commons.dbutils.QueryRunner
 */
public class Executor {
    private Logger LOGGER = LoggerFactory.getLogger(Executor.class);
    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T executeQuery(String sql, ResultHandler<T> resultHandler) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            LOGGER.info(sql);
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return resultHandler.handle(resultSet);
            }
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            LOGGER.info(sql);
            return statement.executeUpdate(sql);
        }
    }

    public int executeUpdate(String sql, ResultHandler<?> generatedKeysHandler)
            throws SQLException {

        try (Statement statement = connection.createStatement()) {
            LOGGER.info(sql);
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows == 0) {
                throw new SQLException("Creating failed, no rows affected.");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                generatedKeysHandler.handle(resultSet);
            }
            return affectedRows;
        }
    }
}
