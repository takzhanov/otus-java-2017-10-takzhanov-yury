package io.github.takzhanov.umbrella.hw05;

public class TestSample2 {
    @After
    public void after1() {
        System.out.println("TS_2 after_1");
    }

    @After
    public void after2() {
        System.out.println("TS_2 after_2");
    }

    @Test
    public void test1() {
        System.out.println("TS_2 test_1");
    }

    @Test
    public void test2() {
        System.out.println("TS_2 test_2");
    }
}
