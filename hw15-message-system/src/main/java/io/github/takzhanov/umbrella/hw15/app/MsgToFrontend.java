package io.github.takzhanov.umbrella.hw15.app;

import io.github.takzhanov.umbrella.hw15.ms.Address;
import io.github.takzhanov.umbrella.hw15.ms.Addressee;
import io.github.takzhanov.umbrella.hw15.ms.Message;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}