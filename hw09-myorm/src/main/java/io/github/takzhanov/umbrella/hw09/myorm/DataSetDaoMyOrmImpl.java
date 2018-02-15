package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.common.Executor;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DataSetDaoMyOrmImpl implements AutoCloseable {
    private Logger LOGGER = LoggerFactory.getLogger(DataSetDaoMyOrmImpl.class);
    private Connection connection;

    public DataSetDaoMyOrmImpl(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void insert(T dataSet) throws SQLException {
        Executor executor = new Executor(connection);
        executor.executeUpdate(MyOrmHelper.makeInsertStatement(dataSet));
    }

    public <T extends DataSet> T findById(long id, Class<T> clazz) throws SQLException {
        Executor executor = new Executor(connection);
        List<T> listResult = executor.executeQuery(MyOrmHelper.makeFindByIdStatement(id, clazz), MyOrmHelper.makeResultHandler(clazz));
        if (listResult.size() == 0) {
            return null;
        } else if (listResult.size() == 1) {
            return listResult.get(0);
        } else {
            throw new RuntimeException("Not unique ID");
        }
    }

    public <T extends DataSet> List<T> findAll(Class<T> clazz) throws SQLException {
        Executor executor = new Executor(connection);
        return executor.executeQuery(MyOrmHelper.makeFindAllStatement(clazz), MyOrmHelper.makeResultHandler(clazz));
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Close ex", e);
        }
    }

}
