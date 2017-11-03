package io.github.takzhanov.umbrella.hw03;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyArrayListTest {
    @Test
    public void size() throws Exception {
        assertEquals(0, new MyArrayList<Integer>().size());
        assertEquals(3, MyArrayList.of(1, 2, 3).size());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(new MyArrayList<Integer>().isEmpty());
        assertTrue(MyArrayList.of().isEmpty());
        assertTrue(!MyArrayList.of(1, 2, 3).isEmpty());
    }

    @Test
    public void contains() throws Exception {
        assertTrue(MyArrayList.of(1, 2, 3).contains(1));
        assertTrue(!MyArrayList.of(1, 2, 3).contains(4));
        assertTrue(!MyArrayList.of().contains(4));
    }

    @Test
    public void iterator() throws Exception {
    }

    @Test
    public void toArray() throws Exception {
        assertArrayEquals(new Integer[]{1, 2, 3}, MyArrayList.of(1, 2, 3).toArray());
    }

    @Test
    public void toArray1() throws Exception {
        assertArrayEquals(new Integer[]{1, 2, 3, null, null}, MyArrayList.of(1, 2, 3).toArray(new Integer[5]));
        assertArrayEquals(new Integer[]{1, 2, 3, null, null}, MyArrayList.of(1, 2, 3).toArray(new Object[5]));
        assertArrayEquals(new Object[]{1, 2, 3, 4, 5}, MyArrayList.of(1, 2, 3, 4, 5).toArray(new Number[3]));
    }

    @Test
    public void add() throws Exception {
        MyArrayList<Integer> list = MyArrayList.of();
        assertTrue(!list.contains(1));
        assertTrue(list.add(1));
        assertTrue(list.contains(1));
    }

    @Test
    public void remove() throws Exception {
        assertFalse(MyArrayList.of(1, 2, 3).remove((Integer) 0));
        assertTrue(MyArrayList.of(1, 2, 3).remove((Integer) 1));
    }

    @Test
    public void containsAll() throws Exception {

    }

    @Test
    public void addAll() throws Exception {
    }

    @Test
    public void addAll1() throws Exception {
    }

    @Test
    public void removeAll() throws Exception {
    }

    @Test
    public void retainAll() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void set() throws Exception {
    }

    @Test
    public void add1() throws Exception {
    }

    @Test
    public void remove1() throws Exception {
    }

    @Test
    public void indexOf() throws Exception {
    }

    @Test
    public void lastIndexOf() throws Exception {
    }

    @Test
    public void listIterator() throws Exception {
    }

    @Test
    public void listIterator1() throws Exception {
    }

    @Test
    public void subList() throws Exception {
    }

}