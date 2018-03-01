package io.github.takzhanov.umbrella.hw13;

import io.github.takzhanov.umbrella.hw09.common.ConnectionHelper;
import io.github.takzhanov.umbrella.hw09.myorm.DbServiceMyOrmImpl;
import org.springframework.stereotype.Component;

@Component
public class DbServiceImpl extends DbServiceMyOrmImpl {
    public DbServiceImpl() {
        super(ConnectionHelper.getConnection());
    }
}
