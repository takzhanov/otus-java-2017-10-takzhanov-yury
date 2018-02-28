package io.github.takzhanov.umbrella.hw09;

import io.github.takzhanov.umbrella.hw09.common.ConnectionHelper;
import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw09.myorm.DbServiceMyOrmImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DemoHw09 {
    static final Logger LOGGER = LoggerFactory.getLogger(DemoHw09.class);
    private final DbService dbService = new DbServiceMyOrmImpl(ConnectionHelper.getConnection());

    @Before
    public void setUp() throws Exception {
        LOGGER.info("==========================\n");
        LOGGER.info(dbService.getMetaData());
        dbService.prepareTables();
    }

    @After
    public void tearDown() throws Exception {
        dbService.dropTables();
        dbService.close();
    }

    @Test
    public void demo() throws SQLException {
        dbService.saveUser(new UserDataSet("jorj1", 21));
        dbService.saveUser(new UserDataSet("jorj2", 20));
        dbService.saveUser(new UserDataSet("jorj3", 22));

        for (UserDataSet user : dbService.loadAllUsers()) {
            LOGGER.info(user.toString());
        }
    }
}