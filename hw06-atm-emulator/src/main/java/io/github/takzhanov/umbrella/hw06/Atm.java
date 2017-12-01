package io.github.takzhanov.umbrella.hw06;

import java.util.Map;

public interface Atm {
    int loadMoney(Map<Banknote, Integer> newCassettes);

    int loadMoney(Banknote banknote, Integer count);

    Map<Banknote, Integer> getMoney(Integer sum);

    Integer getAtmBalance();
}