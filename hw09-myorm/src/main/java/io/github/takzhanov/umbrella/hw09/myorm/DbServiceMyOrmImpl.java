package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.common.DataSetDao;
import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.common.Executor;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbServiceMyOrmImpl implements DbService {
    public static final String CREATE_TABLE_USERS = "create table if not exists users (id bigserial , name varchar(256), age smallint, primary key (id))";
    public static final String DROP_TABLE_USERS = "drop table if exists users";
    private Logger LOGGER = LoggerFactory.getLogger(DbServiceMyOrmImpl.class);
    private Connection connection;
    private DataSetDao dao;

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
    public void prepareTables() {
        Executor executor = new Executor(connection);
        try {
            executor.executeUpdate(CREATE_TABLE_USERS);
            LOGGER.info("Table created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTables() {
        Executor executor = new Executor(connection);
        try {
            executor.executeUpdate(DROP_TABLE_USERS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Table dropped");
    }

    @Override
    public void saveUser(UserDataSet user) {
        dao.insert(user);
    }

    @Override
    public UserDataSet loadUser(long id) {
        return dao.findById(id, UserDataSet.class);
    }

    @Override
    public List<UserDataSet> loadAllUsers() {
        return dao.findAll(UserDataSet.class);
    }

    @Override
    public List<UserDataSet> findUserByName(String name) {
        return dao.findByName(name, UserDataSet.class);
    }
}
