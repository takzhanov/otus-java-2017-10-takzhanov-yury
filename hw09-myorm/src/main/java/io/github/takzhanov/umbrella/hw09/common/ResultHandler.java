package io.github.takzhanov.umbrella.hw09.common;

import java.sql.ResultSet;

public interface ResultHandler<T> {
    T handle(ResultSet resultSet);
}
