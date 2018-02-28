package io.github.takzhanov.umbrella.hw11;

import io.github.takzhanov.umbrella.hw09.common.ConnectionHelper;
import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw09.myorm.DbServiceMyOrmImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {
    static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
    private final DbService dbService = new CachedDbService(new DbServiceMyOrmImpl(ConnectionHelper.getConnection()));

    @Before
    public void setUp() {
        LOGGER.info("==========================\n");
        LOGGER.info(dbService.getMetaData());
        dbService.prepareTables();

        dbService.saveUser(new UserDataSet("jorj1", 21));
        dbService.saveUser(new UserDataSet("jorj2", 20));
        dbService.saveUser(new UserDataSet("jorj3", 22));
    }

    @After
    public void tearDown() throws Exception {
        dbService.dropTables();
        dbService.close();
    }

    @Test
    public void demo() throws InterruptedException {
        for (UserDataSet user : dbService.loadAllUsers()) {
            LOGGER.info(user.toString());
        }

        UserDataSet user = dbService.loadUser(1);
        UserDataSet user2 = dbService.loadUser(1);
        Assert.assertTrue("Кэш не сработал", user == user2);

        Thread.sleep(1500);

        UserDataSet user3 = dbService.loadUser(1);
        Assert.assertFalse("Кэш не сбросился", user == user3);
    }
}