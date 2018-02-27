package io.github.takzhanov.umbrella.hw14;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ParallelSortUtilsTest {

    public static <T extends Comparable<T>> boolean isSortedBetween(T[] arr, int l, int r) {
        for (int i = l; i < r - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr) {
        return isSortedBetween(arr, 0, arr.length);
    }

    /**
     * Вероятность моргания теста 1/100! - пойдет
     */
    @Test
    public void helperTest() {
        Integer[] arr = ParallelSortUtils.generateShuffleArray(100);
        Assert.assertFalse(isSorted(arr));
        Arrays.sort(arr);
        Assert.assertTrue(isSorted(arr));
    }

    @Test
    public void mergeSortTest() {
        Integer[] arr;

        arr = new Integer[]{0, 2, 4, 6, 1, 3, 5};
        ParallelSortUtils.mergeSort(arr, 0, arr.length);
        Assert.assertTrue(isSorted(arr));

        arr = new Integer[]{0, 2, 4, 1, 3, 5};
        ParallelSortUtils.mergeSort(arr, 0, arr.length);
        Assert.assertTrue(isSorted(arr));

        arr = new Integer[]{0, 2, 4, 6, 1, 3, 5};
        ParallelSortUtils.mergeSort(arr, 1, arr.length);
        Assert.assertTrue(isSortedBetween(arr, 1, arr.length));

        arr = new Integer[]{2, 1, 2, 1, 0, 1, 2, 0, 1, 0, 1};
        ParallelSortUtils.mergeSort(arr, 4, 7);
        Assert.assertTrue(isSortedBetween(arr, 4, 7));
    }


    @Test
    public void parallelSortTest() {
        final int N = 20;
        for (int nThreads = 0; nThreads < 10; nThreads++) {
            Integer[] arr = ParallelSortUtils.generateShuffleArray(N);
            ParallelSortUtils.parallelSort(nThreads, arr);
            Assert.assertTrue(isSorted(arr));
        }
    }


}