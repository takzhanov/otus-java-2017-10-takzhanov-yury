package io.github.takzhanov.umbrella.hw15.app;

import io.github.takzhanov.umbrella.hw15.ms.Addressee;

public interface DbService extends Addressee, io.github.takzhanov.umbrella.hw09.common.DbService {
    void init();

    int getUserId(String name);
}