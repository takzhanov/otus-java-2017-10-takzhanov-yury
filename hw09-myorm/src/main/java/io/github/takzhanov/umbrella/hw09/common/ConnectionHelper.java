package io.github.takzhanov.umbrella.hw09.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Все знания о реальной базе должно быть только здесь
 */
public class ConnectionHelper {
    public static Connection getConnection() {
//        return getPgConnection();
        return getH2Connection();
    }

    private static Connection getPgConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String url = "jdbc:postgresql://localhost:5432/ytakzhanov";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getH2Connection() {
        try {
            DriverManager.registerDriver(new org.h2.Driver());
            return DriverManager.getConnection("jdbc:h2:./test", "sa", "sa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
