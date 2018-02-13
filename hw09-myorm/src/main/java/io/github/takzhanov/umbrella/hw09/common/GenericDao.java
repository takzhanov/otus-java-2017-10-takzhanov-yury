package io.github.takzhanov.umbrella.hw09.common;

import io.github.takzhanov.umbrella.hw09.domain.DataSet;

import java.sql.SQLException;

public interface GenericDao {
    <T extends DataSet> void save(T dataSet) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
