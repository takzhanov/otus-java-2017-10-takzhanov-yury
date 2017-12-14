package io.github.takzhanov.umbrella.hw07;

import io.github.takzhanov.umbrella.hw06.Atm;

public interface AtmDepartment {
    void addAtm(Atm atm);

    void resetAllAtms();

    Integer getDepartmentBalance();
}