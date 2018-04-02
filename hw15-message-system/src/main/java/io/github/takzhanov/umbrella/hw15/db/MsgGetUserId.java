package io.github.takzhanov.umbrella.hw15.db;

import io.github.takzhanov.umbrella.hw15.app.DbService;
import io.github.takzhanov.umbrella.hw15.app.MsgToDb;
import io.github.takzhanov.umbrella.hw15.ms.Address;

public class MsgGetUserId extends MsgToDb {
    private final String login;

    public MsgGetUserId(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    @Override
    public void exec(DbService dbService) {
        int id = dbService.getUserId(login);
        dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), login, id));
    }
}

