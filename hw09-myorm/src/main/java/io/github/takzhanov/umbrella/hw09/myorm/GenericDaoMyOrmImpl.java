package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.common.Executor;
import io.github.takzhanov.umbrella.hw09.common.GenericDao;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class GenericDaoMyOrmImpl implements GenericDao {
    private Connection connection;

    public GenericDaoMyOrmImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) throws SQLException {
        Executor executor = new Executor(connection);
        executor.executeUpdate(MyOrm.makeInsertStatement(dataSet));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Executor executor = new Executor(connection);
        return executor.executeQuery(MyOrm.makeSelectStatement(id, clazz), MyOrm.makeResultHandler(clazz));
    }
}
