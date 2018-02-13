package io.github.takzhanov.umbrella.hw09.common;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T executeQuery(String sql, ResultHandler<T> resultHandler) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            return resultHandler.handle(resultSet);
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        }
    }
}
