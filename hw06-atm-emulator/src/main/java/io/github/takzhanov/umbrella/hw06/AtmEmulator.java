package io.github.takzhanov.umbrella.hw06;

import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class AtmEmulator implements Atm {
    private Map<Banknote, Integer> cassettes = new HashMap<>();
    private int balance = 0;

    @Override
    public int loadMoney(@NotNull Map<Banknote, Integer> newCassettes) {
        newCassettes.forEach(this::loadMoney);
        return balance;
    }

    @Override
    public int loadMoney(@NotNull Banknote banknote, @NotNull Integer count) {
        cassettes.put(banknote, cassettes.getOrDefault(banknote, 0) + count);
        balance += banknote.nominal * count;
        return balance;
    }

    @Override
    public Map<Banknote, Integer> getMoney(Integer sum) {
        if (sum > balance) {
            throw new TooMuchMoneyException("Requested too much money");
        }
        int length = cassettes.size();
        // [500, 100, 20, 1] - номиналы по убыванию
        int[] nominals = cassettes.entrySet().stream()
                .sorted(Map.Entry.<Banknote, Integer>comparingByKey().reversed())
                .mapToInt(v -> v.getKey().nominal).toArray();
        // [10, 10, 10, 10] - кол-во банкнот номинала
        int[] srcComb = cassettes.entrySet().stream()
                .sorted(Map.Entry.<Banknote, Integer>comparingByKey().reversed())
                .mapToInt(Map.Entry::getValue).toArray();
        int[] outComb = findCombination(nominals, srcComb, new int[length], sum, 0);
        Map<Banknote, Integer> result = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Banknote curBanknote = Banknote.of(nominals[i]);
            int curCount = outComb[i];
            cassettes.put(curBanknote, cassettes.get(curBanknote) - curCount);
            balance -= curBanknote.nominal * curCount;
            result.put(curBanknote, curCount);
        }
        return result;
    }

    @Override
    public Integer getAtmBalance() {
        balance = calculateSum(cassettes);
        return balance;
    }

    @Override
    public void reset() {
        throw new NotImplementedException();
    }

    private static int[] findCombination(int[] nominals, int[] srcComb, int[] outComb, int targetSum, int nextNominal) {
        if (targetSum == 0) {
            return outComb;
        }
        if (nextNominal >= nominals.length) {
            for (int i = nominals.length - 1; i >= 0; i--) {
                if (outComb[i] > 0) {
                    //откатываем последнюю купюру
                    srcComb[i] += 1;
                    outComb[i] -= 1;
                    targetSum += nominals[i];
                    nextNominal = i + 1;
                    return findCombination(nominals, srcComb, outComb, targetSum, nextNominal);
                }
            }
            throw new ImpossibleException("Невозможно выдать: " + targetSum);
        } else {
            int nextCount = min(targetSum / nominals[nextNominal], srcComb[nextNominal]);
            srcComb[nextNominal] -= nextCount;
            outComb[nextNominal] += nextCount;
            targetSum -= nominals[nextNominal] * nextCount;
            nextNominal++;
            return findCombination(nominals, srcComb, outComb, targetSum, nextNominal);
        }
    }

    private static int calculateSum(@NotNull Map<Banknote, Integer> cassettes) {
        return cassettes.entrySet().stream()
                .map(banknoteIntegerEntry -> banknoteIntegerEntry.getKey().nominal * banknoteIntegerEntry.getValue())
                .mapToInt(Integer::intValue)
                .sum();
    }

}
