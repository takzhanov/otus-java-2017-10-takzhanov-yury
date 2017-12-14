package io.github.takzhanov.umbrella.hw06;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AtmTest {
    private Atm standardAtm;

    @Before
    public void setUp() {
        Map<Banknote, Integer> standardLoading = new HashMap<>();
        standardLoading.put(Banknote.of(10), 10);
        standardLoading.put(Banknote.of(5), 10);
        standardLoading.put(Banknote.of(2), 10);
        standardLoading.put(Banknote.of(1), 10);
        standardAtm = new AtmEmulator();
        standardAtm.loadMoney(standardLoading);
    }

    @Test
    public void getAtmBalance() {
        Atm atm = new AtmEmulator();
        assertTrue(0 == atm.getAtmBalance());
        atm.loadMoney(Banknote.of(100), 5);
        assertTrue(500 == atm.getAtmBalance());
        atm.loadMoney(Banknote.of(10), 3);
        assertTrue(530 == atm.getAtmBalance());
    }

    @Test
    public void testReset() {
        Atm atm = new AtmEmulator();
        assertTrue(0 == atm.getAtmBalance());
        atm.loadMoney(Banknote.of(100), 5);
        assertTrue(500 == atm.getAtmBalance());
        atm.reset();
        assertTrue(0 == atm.getAtmBalance());
    }

    @Test
    public void testReset2() {
        Map<Banknote, Integer> cassettes = new HashMap<>();
        cassettes.put(Banknote.of(100), 10);
        cassettes.put(Banknote.of(500), 2);
        Atm atm = new AtmEmulator(cassettes);
        assertTrue(2000 == atm.getAtmBalance());
        atm.loadMoney(Banknote.of(100), 5);
        assertTrue(2500 == atm.getAtmBalance());
        atm.reset();
        assertTrue(2000 == atm.getAtmBalance());
    }

    @Test
    public void loadMoney() {
        assertTrue(180 == standardAtm.getAtmBalance());
        Map<Banknote, Integer> additionalLoading = new HashMap<>();
        additionalLoading.put(Banknote.of(500), 10);
        additionalLoading.put(Banknote.of(100), 10);
        assertTrue(6180 == standardAtm.loadMoney(additionalLoading));
        assertTrue(6680 == standardAtm.loadMoney(Banknote.of(100), 5));
    }

    @Test(expected = TooMuchMoneyException.class)
    public void getMoney200() {
        standardAtm.getMoney(200);
    }

    @Test
    public void getMoney100() {
        Map<Banknote, Integer> result = standardAtm.getMoney(100);
        assertTrue(10 == result.get(Banknote.of(10)));
    }

    @Test
    public void getMoney19() {
        Map<Banknote, Integer> result = standardAtm.getMoney(19);
        assertTrue(1 == result.get(Banknote.of(10)));
        assertTrue(1 == result.get(Banknote.of(5)));
        assertTrue(2 == result.get(Banknote.of(2)));
        assertTrue(0 == result.get(Banknote.of(1)));
    }

    @Test
    public void getMoney14() {
        Map<Banknote, Integer> result = standardAtm.getMoney(14);
        assertTrue(1 == result.get(Banknote.of(10)));
        assertTrue(0 == result.get(Banknote.of(5)));
        assertTrue(2 == result.get(Banknote.of(2)));
        assertTrue(0 == result.get(Banknote.of(1)));
    }

    @Test
    public void getMoney26() {
        Map<Banknote, Integer> loading = new HashMap<>();
        loading.put(Banknote.of(15), 2);
        loading.put(Banknote.of(13), 2);
        loading.put(Banknote.of(1), 10);
        Atm atm = new AtmEmulator();
        atm.loadMoney(loading);
        Map<Banknote, Integer> result = atm.getMoney(26);
        assertTrue(2 == result.get(Banknote.of(13)));
        assertTrue(40 == atm.getAtmBalance());
    }

    @Test(expected = ImpossibleException.class)
    public void getMoney55() {
        Map<Banknote, Integer> loading = new HashMap<>();
        loading.put(Banknote.of(15), 2);
        loading.put(Banknote.of(13), 2);
        loading.put(Banknote.of(1), 10);
        Atm atm = new AtmEmulator();
        atm.loadMoney(loading);
        atm.getMoney(55);
    }


}