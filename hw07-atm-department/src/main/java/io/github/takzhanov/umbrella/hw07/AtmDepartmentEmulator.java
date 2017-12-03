package io.github.takzhanov.umbrella.hw07;

import io.github.takzhanov.umbrella.hw06.Atm;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartmentEmulator implements AtmDepartment {
    List<Atm> atms = new ArrayList<>();

    @Override
    public void addAtm(@NotNull Atm atm) {
        atms.add(atm);
    }

    @Override
    public void resetDepartments() {
        atms.forEach(Atm::reset);
    }

    @Override
    public Integer getDepartmentBalance() {
        return atms.stream().map(Atm::getAtmBalance).mapToInt(Integer::intValue).sum();
    }
}
