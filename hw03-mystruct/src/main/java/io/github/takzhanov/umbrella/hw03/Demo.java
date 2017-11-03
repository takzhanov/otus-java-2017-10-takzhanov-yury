package io.github.takzhanov.umbrella.hw03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Demo {
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        List<Integer> list = MyArrayList.of(1, 2, 3, 6, 5, 4);
        LOGGER.info(Arrays.toString(list.toArray()));

        Collections.addAll(list, 9, 8, 7);
        LOGGER.info(Arrays.toString(list.toArray()));

        final List<Integer> dest = new ArrayList<>();
        dest.addAll(Collections.nCopies(10, 1000));
        LOGGER.info(Arrays.toString(dest.toArray()));
        Collections.copy(dest, list);
        LOGGER.info(Arrays.toString(dest.toArray()));

        final MyArrayList<Integer> asDest = new MyArrayList<>();
        asDest.addAll(Collections.nCopies(10, 7));
        Collections.copy(asDest, dest);
        LOGGER.info(Arrays.toString(list.toArray()));

        Collections.sort(list);
        LOGGER.info(Arrays.toString(list.toArray()));
    }
}
