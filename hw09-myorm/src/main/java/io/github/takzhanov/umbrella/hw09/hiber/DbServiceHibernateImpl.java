package io.github.takzhanov.umbrella.hw09.hiber;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class DbServiceHibernateImpl implements DbService {
    private Session getSession() {
        return null;
    }

    @Override
    public String getMetaData() {
        return null;
    }

    @Override
    public int prepareTables() throws SQLException {
        return 0;
    }

    @Override
    public int dropTables() throws SQLException {
        return 0;
    }

    @Override
    public void saveUser(UserDataSet user) throws SQLException {

    }

    @Override
    public UserDataSet loadUser(long id) throws SQLException {
        return null;
    }

    @Override
    public List<UserDataSet> loadAllUsers() throws SQLException {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}

