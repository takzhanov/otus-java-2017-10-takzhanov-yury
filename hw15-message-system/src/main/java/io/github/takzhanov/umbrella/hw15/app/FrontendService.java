package io.github.takzhanov.umbrella.hw15.app;

import io.github.takzhanov.umbrella.hw15.ms.Addressee;

public interface FrontendService extends Addressee {
    void init();

    void handleRequest(String login);

    void addUser(int id, String name);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}

