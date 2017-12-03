package io.github.takzhanov.umbrella.hw05;

public class TestSample1 {
    @Before
    public void before() {
        System.out.println("TS_1 before");
    }

    @Test
    public void test1() {
        System.out.println("TS_1 test_1");
    }

    @Test
    public void test2() {
        System.out.println("TS_1 test_2");
    }

    @After
    public void after() {
        System.out.println("TS_1 after");
    }
}
