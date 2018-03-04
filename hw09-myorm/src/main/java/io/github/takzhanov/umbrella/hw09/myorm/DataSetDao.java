package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;

import java.util.List;

public interface DataSetDao {
    <T extends DataSet> void insert(T dataSet);

    <T extends DataSet> T findById(long id, Class<T> clazz);

    <T extends DataSet> List<T> findAll(Class<T> clazz);

    <T extends UserDataSet> List<T> findByName(String name, Class<T> clazz);
}
