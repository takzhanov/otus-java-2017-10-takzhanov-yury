package io.github.takzhanov.umbrella.hw09.common;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;

import java.util.List;

public interface DbService extends AutoCloseable {
    String CREATE_TABLE_USERS = "create table if not exists users (id bigserial , name varchar(256), age smallint, primary key (id))";
    String DROP_TABLE_USERS = "drop table if exists users";

    String getMetaData();

    int prepareTables();

    int dropTables();

    void saveUser(UserDataSet user);

    UserDataSet loadUser(long id);

    List<UserDataSet> loadAllUsers();

    List<UserDataSet> findUserByName(String name);

    default UserDataSet checkCredentials(String username, String password) {
        List<UserDataSet> users = findUserByName(username);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}
