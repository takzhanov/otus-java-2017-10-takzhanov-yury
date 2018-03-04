package io.github.takzhanov.umbrella.hw11;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw11.cache.CacheEngine;
import io.github.takzhanov.umbrella.hw11.cache.CacheEngineImpl;

import java.util.List;

public class CachedDbService implements DbService {
    private DbService dbService;
    private CacheEngine<Long, UserDataSet> usersCache = new CacheEngineImpl<>(10, 1000, 0, false);

    public CachedDbService(DbService dbService) {
        this.dbService = dbService;
    }

    public CachedDbService(DbService dbService, CacheEngine<Long, UserDataSet> usersCache) {
        this.dbService = dbService;
        this.usersCache = usersCache;
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
        UserDataSet userDataSet = usersCache.get(id);
        if (userDataSet == null) {
            userDataSet = dbService.loadUser(id);
            usersCache.put(id, userDataSet);
        }
        return userDataSet;
    }


    @Override
    public List<UserDataSet> loadAllUsers() {
        return dbService.loadAllUsers();
    }

    @Override
    public List<UserDataSet> findUserByName(String name) {
        return dbService.findUserByName(name);
    }

    @Override
    public void close() throws Exception {
        dbService.close();
    }
}

