package io.github.takzhanov.umbrella.hw15.app;

import io.github.takzhanov.umbrella.hw15.ms.Address;
import io.github.takzhanov.umbrella.hw15.ms.Addressee;
import io.github.takzhanov.umbrella.hw15.ms.Message;

public abstract class MsgToDb extends Message {
    public MsgToDb(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DbService) {
            exec((DbService) addressee);
        }
    }

    public abstract void exec(DbService dbService);
}
