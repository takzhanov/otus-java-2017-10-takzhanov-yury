package io.github.takzhanov.umbrella.hw09.main;

import io.github.takzhanov.umbrella.hw09.ConnectionHelper;
import io.github.takzhanov.umbrella.hw09.common.Executor;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDaoManualImpl implements AutoCloseable {
    protected Logger LOGGER = LoggerFactory.getLogger(UserDaoManualImpl.class);
    private static final String CREATE_TABLE_USERS = "create table if not exists users (id bigserial , name varchar(256), age smallint, primary key (id))";
    private static final String INSERT_USER = "insert into users(name, age) values ('%s', '%d')";
    private static final String DROP_TABLE_USERS = "drop table users";

    private Connection connection;

    public UserDaoManualImpl() {
        connection = ConnectionHelper.getConnection();
    }

    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            LOGGER.error("db access error or this method is called on a closed connection", e);
            return e.getMessage();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Close ex", e);
        }
    }

    public int prepareTables() throws SQLException {
        Executor executor = new Executor(connection);
        int result = executor.executeUpdate(CREATE_TABLE_USERS);
        LOGGER.info("Table created");
        return result;
    }

    public int dropTables() throws SQLException {
        Executor executor = new Executor(connection);
        int result = executor.executeUpdate(DROP_TABLE_USERS);
        LOGGER.info("Table droped");
        return result;
    }

    public int addUser(UserDataSet user) throws SQLException {
        Executor executor = new Executor(connection);
        int result = executor.executeUpdate(String.format(INSERT_USER, user.getName(), user.getAge()));
        LOGGER.info("User added");
        return result;
    }

    public <T extends DataSet> int save(T user) {
        Executor executor = new Executor(connection);
//        return executor.save(user);
        return -1;
    }

    public Connection getConnection() {
        return connection;
    }

}
