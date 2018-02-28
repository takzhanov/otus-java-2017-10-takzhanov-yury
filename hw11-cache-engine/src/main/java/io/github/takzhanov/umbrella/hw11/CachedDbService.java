package io.github.takzhanov.umbrella.hw11;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedDbService implements DbService {
    private DbService dbService;
    Map<Long, SoftReference<UserDataSet>> usersCache = new HashMap<>();

    public CachedDbService(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public String getMetaData() {
        return dbService.getMetaData();
    }

    @Override
    public int prepareTables() {
        return dbService.prepareTables();
    }

    @Override
    public int dropTables() {
        return dbService.dropTables();
    }

    @Override
    public void saveUser(UserDataSet user) {
        dbService.saveUser(user);
    }

    @Override
    public UserDataSet loadUser(long id) {
        return usersCache.computeIfAbsent(id, key -> {
            UserDataSet userDataSet = dbService.loadUser(key);
            return new SoftReference<>(userDataSet);
        }).get();
    }

    @Override
    public List<UserDataSet> loadAllUsers() {
        return dbService.loadAllUsers();
    }

    @Override
    public void close() throws Exception {
        dbService.close();
    }
}

