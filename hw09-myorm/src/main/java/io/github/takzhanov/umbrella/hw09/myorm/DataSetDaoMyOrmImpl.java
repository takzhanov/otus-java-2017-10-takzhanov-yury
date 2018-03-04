package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.common.Executor;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DataSetDaoMyOrmImpl implements AutoCloseable, DataSetDao {
    private Logger LOGGER = LoggerFactory.getLogger(DataSetDaoMyOrmImpl.class);
    private Connection connection;

    public DataSetDaoMyOrmImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void insert(T dataSet) {
        Executor executor = new Executor(connection);
        try {
            executor.executeUpdate(MyOrmHelper.makeInsertStatement(dataSet));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public <T extends DataSet> T findById(long id, Class<T> clazz) {
        Executor executor = new Executor(connection);
        List<T> listResult = null;
        try {
            listResult = executor.executeQuery(
                    MyOrmHelper.makeFindByIdStatement(id, clazz),
                    MyOrmHelper.makeResultHandler(clazz));

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        if (listResult.size() == 0) {
            return null;
        } else if (listResult.size() == 1) {
            return listResult.get(0);
        } else {
            throw new RuntimeException("Not unique ID");
        }
    }

    @Override
    public <T extends DataSet> List<T> findAll(Class<T> clazz) {
        Executor executor = new Executor(connection);
        try {
            return executor.executeQuery(MyOrmHelper.makeFindAllStatement(clazz), MyOrmHelper.makeResultHandler(clazz));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public <T extends UserDataSet> List<T> findByName(String name, Class<T> clazz) {
        Executor executor = new Executor(connection);
        try {
            return executor.executeQuery(
                    MyOrmHelper.makeFindByNameStatement(name, clazz),
                    MyOrmHelper.makeResultHandler(clazz));

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Close ex", e);
        }
    }

}
