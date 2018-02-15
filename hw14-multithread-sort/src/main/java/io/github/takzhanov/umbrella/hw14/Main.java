package io.github.takzhanov.umbrella.hw14;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int N = 20;
        final int nThreads = 4;
        final Integer[] arr = ParallelSortUtils.generateShuffleArray(N, 1000);
        System.out.println(Arrays.toString(arr));
        ParallelSortUtils.parallelSort(nThreads, arr);
        System.out.println(Arrays.toString(arr));
    }
}
