package io.github.takzhanov.umbrella.hw01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoldenRatio {
    private static Logger LOGGER = LoggerFactory.getLogger(GoldenRatio.class);

    public static Double calc(Double precision) {
        int step = 0;
        int a = 1;
        int b = 1;
        Double prevCandidate = 0d;
        Double nextCandidate = 1d;

        while (Math.abs(nextCandidate - prevCandidate) > precision) {
            int c = a + b;
            a = b;
            b = c;
            prevCandidate = nextCandidate;
            nextCandidate = (double) b / a;
            LOGGER.debug("For {} and {} GR is {}", a, b, nextCandidate);
            step++;
        }

        LOGGER.info("Calculated in {} steps", step);
        return nextCandidate;
    }
}
