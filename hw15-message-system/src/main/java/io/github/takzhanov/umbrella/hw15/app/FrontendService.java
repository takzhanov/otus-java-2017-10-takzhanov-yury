package io.github.takzhanov.umbrella.hw15.app;

import io.github.takzhanov.umbrella.hw15.ms.Addressee;

public interface FrontendService extends Addressee {
    void init();

    void addListener(Listener listener);

    void removeListener(Listener listener);

    void sayAll(String msg);

    void showSysInfo(final Listener source);

    void showUserInfo(Listener source, String username);
}

