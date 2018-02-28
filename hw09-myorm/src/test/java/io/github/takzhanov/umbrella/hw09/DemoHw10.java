package io.github.takzhanov.umbrella.hw09;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw09.hiber.DbServiceHibernateImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DemoHw10 {
    static final Logger LOGGER = LoggerFactory.getLogger(DemoHw10.class);
    private final DbService dbService = new DbServiceHibernateImpl();

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
        UserDataSet jorj = new UserDataSet("jorj", 21);
        jorj.addPhone("777");
        jorj.addPhone("888");
        dbService.saveUser(jorj);
        dbService.saveUser(new UserDataSet("jorj2", 20));
        dbService.saveUser(new UserDataSet("jorj3", 22));
        dbService.saveUser(new UserDataSet("jorj3", 23, "Mark Twen st."));

        for (UserDataSet user : dbService.loadAllUsers()) {
            LOGGER.info(user.toString());
        }
    }
}