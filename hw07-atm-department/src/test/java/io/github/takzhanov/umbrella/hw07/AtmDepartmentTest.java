package io.github.takzhanov.umbrella.hw07;

import io.github.takzhanov.umbrella.hw06.Atm;
import io.github.takzhanov.umbrella.hw06.AtmEmulator;
import io.github.takzhanov.umbrella.hw06.Banknote;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AtmDepartmentTest {
    AtmDepartment dep;
    Atm atm1;
    Atm atm2;

    @Before
    public void init() {
        dep = new AtmDepartmentEmulator();
        atm1 = new AtmEmulator();
        atm2 = new AtmEmulator();
        dep.addAtm(atm1);
        dep.addAtm(atm2);
    }

    @Test
    public void testDepartmentBalance() {
        Assert.assertEquals(0L, dep.getDepartmentBalance().longValue());
        atm1.loadMoney(Banknote.of(500), 4);
        atm2.loadMoney(Banknote.of(100), 5);
        Assert.assertEquals(atm1.getAtmBalance() + atm2.getAtmBalance(),
                dep.getDepartmentBalance().longValue());
    }

    @Test
    public void testReset() {
        atm1.loadMoney(Banknote.of(500), 4);
        atm2.loadMoney(Banknote.of(100), 5);
        dep.resetAllAtms();
        Assert.assertEquals(0L, dep.getDepartmentBalance().longValue());
        Map<Banknote, Integer> cassettes = new HashMap<>();
        cassettes.put(Banknote.of(250), 4);
        Atm atm3 = new AtmEmulator(cassettes);
        atm3.loadMoney(cassettes);
        Assert.assertEquals(2000, atm3.getAtmBalance().longValue());
        dep.addAtm(atm3);
        Assert.assertEquals(2000, dep.getDepartmentBalance().longValue());
        atm2.loadMoney(Banknote.of(100), 5);
        Assert.assertEquals(2500, dep.getDepartmentBalance().longValue());
        dep.resetAllAtms();
        Assert.assertEquals(1000, dep.getDepartmentBalance().longValue());
    }
}