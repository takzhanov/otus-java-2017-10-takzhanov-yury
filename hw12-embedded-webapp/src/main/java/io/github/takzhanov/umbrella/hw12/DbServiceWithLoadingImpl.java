package io.github.takzhanov.umbrella.hw12;

import io.github.takzhanov.umbrella.hw09.common.ConnectionHelper;
import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw09.myorm.DbServiceMyOrmImpl;
import io.github.takzhanov.umbrella.hw11.CachedDbService;
import io.github.takzhanov.umbrella.hw11.cache.CacheEngine;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DbServiceWithLoadingImpl extends CachedDbService {

    public DbServiceWithLoadingImpl(CacheEngine<Long, UserDataSet> usersCache) {
        super(new DbServiceMyOrmImpl(ConnectionHelper.getConnection()), usersCache);
        startLoading();
    }

    private void startLoading() {
        dropTables();
        prepareTables();
        this.saveUser(new UserDataSet("root", 99));
        for (int i = 0; i < 10; i++) {
            this.saveUser(new UserDataSet("Name " + i, i * 2));
        }

        final DbService dbService = this;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    dbService.loadUser(new Random().nextInt(10));
                }
            }
        }, 0, 300);
    }

}
