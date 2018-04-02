package io.github.takzhanov.umbrella.hw15.db;

import io.github.takzhanov.umbrella.hw15.app.FrontendService;
import io.github.takzhanov.umbrella.hw15.app.MsgToFrontend;
import io.github.takzhanov.umbrella.hw15.ms.Address;

public class MsgGetUserIdAnswer extends MsgToFrontend {
    private final String name;
    private final int id;

    public MsgGetUserIdAnswer(Address from, Address to, String name, int id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name);
    }
}
