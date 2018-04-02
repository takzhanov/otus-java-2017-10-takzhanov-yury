package io.github.takzhanov.umbrella.hw15.db;

import io.github.takzhanov.umbrella.hw09.common.ConnectionHelper;
import io.github.takzhanov.umbrella.hw09.myorm.DbServiceMyOrmImpl;
import io.github.takzhanov.umbrella.hw15.app.DbService;
import io.github.takzhanov.umbrella.hw15.app.MessageSystemContext;
import io.github.takzhanov.umbrella.hw15.ms.Address;
import io.github.takzhanov.umbrella.hw15.ms.MessageSystem;

public class DbServiceImpl extends DbServiceMyOrmImpl implements DbService {
    private final Address address;
    private final MessageSystemContext context;

    public DbServiceImpl(MessageSystemContext context, Address address) {
        super(ConnectionHelper.getConnection());
        this.context = context;
        this.address = address;
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public int getUserId(String name) {
        //todo: load id from db
        return name.hashCode();
    }
}