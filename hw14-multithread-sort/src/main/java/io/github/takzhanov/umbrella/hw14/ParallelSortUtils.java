package io.github.takzhanov.umbrella.hw14;

import java.util.Arrays;

public class ParallelSortUtils {

    public static <T extends Comparable<T>> void parallelSort(final int nThreads, T[] arr, int l, int r) {
        if (nThreads > 1) {
            final int k = l + (r - l + 1) / 2;
            Thread t1 = new Thread(() -> parallelSort(nThreads / 2, arr, l, k));
            t1.start();
            parallelSort(nThreads / 2, arr, k, r);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mergeSort(arr, l, r);
        } else {
            sortBetween(arr, l, r);
//            String s = String.format("%15s: left: %4d, right: %4d :: %s",
//                    Thread.currentThread().getName(), l, r, Arrays.toString(arr));
//            System.out.println(s);
        }
    }


    private static <T extends Comparable<T>> void sortBetween(T[] arr, int l, int r) {
        for (int i = 0; i < r - l; i++) {
            for (int j = l; j < r - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    public static <T extends Comparable<T>> void mergeSort(T[] arr, int l1, int r2) {
        final int l2 = l1 + (r2 - l1 + 1) / 2;
        int j = l2;
        int k = l1;
        T[] temp = Arrays.copyOfRange(arr, l1, l2);
        int i = 0;
        int r1 = l2 - l1;
        while (i < r1 && j < r2) {
            if (temp[i].compareTo(arr[j]) < 0) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }
        while (i < r1) {
            arr[k++] = temp[i++];
        }
        while (j < r2) {
            arr[k++] = arr[j++];
        }
    }


}
