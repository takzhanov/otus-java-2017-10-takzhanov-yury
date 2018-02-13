package io.github.takzhanov.umbrella.hw09;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw09.main.UserDaoManualImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class MainTest {
    static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    @Before
    public void setUp() throws Exception {
        UserDaoManualImpl dbService = new UserDaoManualImpl();
        dbService.prepareTables();
    }

    @After
    public void tearDown() throws Exception {
        UserDaoManualImpl dbService = new UserDaoManualImpl();
        dbService.dropTables();
    }

    @Test
    public void saveUser() throws SQLException {
        UserDataSet user = new UserDataSet();
        try (UserDaoManualImpl dbService = new UserDaoManualImpl()) {
            LOGGER.info(dbService.getMetaData());
            dbService.addUser(user);
            dbService.save(user);
        }
    }
}