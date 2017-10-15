package io.github.takzhanov.umbrella.hw01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        final Double deltaDefault = 0.001;
        final Double delta = (args.length > 0) ? Double.parseDouble(args[0]) : deltaDefault;
        final Double gr = GoldenRatio.calc(delta);
        LOGGER.info("Golden Ration is {} with precision {}", gr, delta);
    }
}
