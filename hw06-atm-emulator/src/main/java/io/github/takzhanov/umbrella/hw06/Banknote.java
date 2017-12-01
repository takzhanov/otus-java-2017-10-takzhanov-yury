package io.github.takzhanov.umbrella.hw06;

import org.jetbrains.annotations.NotNull;

public class Banknote implements Comparable<Banknote> {
    final public int nominal;

    private Banknote(int nominal) {
        if (nominal <= 0) {
            throw new IllegalArgumentException("Nominal must be positive");
        }
        this.nominal = nominal;
    }

    public static Banknote of(int nominal) {
        return new Banknote(nominal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banknote)) return false;

        Banknote banknote = (Banknote) o;

        return nominal == banknote.nominal;
    }

    @Override
    public int hashCode() {
        return nominal;
    }

    @Override
    public int compareTo(@NotNull Banknote o) {
        return Integer.compare(nominal, o.nominal);
    }
}
