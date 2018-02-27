package io.github.takzhanov.umbrella.hw09.common;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public interface DbService extends AutoCloseable {
    String CREATE_TABLE_USERS = "create table if not exists users (id bigserial , name varchar(256), age smallint, primary key (id))";
    String DROP_TABLE_USERS = "drop table if exists users";

    String getMetaData();

    int prepareTables() throws SQLException;

    int dropTables() throws SQLException;

    void saveUser(UserDataSet user) throws SQLException;

    UserDataSet loadUser(long id) throws SQLException;

    List<UserDataSet> loadAllUsers() throws SQLException;
}
