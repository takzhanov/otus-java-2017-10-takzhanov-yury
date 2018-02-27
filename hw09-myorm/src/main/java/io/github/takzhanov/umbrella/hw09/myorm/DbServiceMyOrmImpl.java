package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.common.Executor;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbServiceMyOrmImpl implements DbService {
    private Logger LOGGER = LoggerFactory.getLogger(DbServiceMyOrmImpl.class);
    private Connection connection;
    private DataSetDaoMyOrmImpl dao;

    public DbServiceMyOrmImpl(Connection connection) {
        this.connection = connection;
        this.dao = new DataSetDaoMyOrmImpl(this.connection);
    }

    @Override
    public String getMetaData() {
        try {
            return "\n" +
                    "Connected to: " + connection.getMetaData().getURL() + "\n" +
                    "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + connection.getMetaData().getDriverName();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
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

    @Override
    public int prepareTables() throws SQLException {
        Executor executor = new Executor(connection);
        int result = executor.executeUpdate(CREATE_TABLE_USERS);
        LOGGER.info("Table created");
        return result;
    }

    @Override
    public int dropTables() throws SQLException {
        Executor executor = new Executor(connection);
        int result = executor.executeUpdate(DROP_TABLE_USERS);
        LOGGER.info("Table dropped");
        return result;
    }

    @Override
    public void saveUser(UserDataSet user) throws SQLException {
        dao.insert(user);
    }

    @Override
    public UserDataSet loadUser(long id) throws SQLException {
        return dao.findById(id, UserDataSet.class);
    }

    @Override
    public List<UserDataSet> loadAllUsers() throws SQLException {
        return dao.findAll(UserDataSet.class);
    }
}
