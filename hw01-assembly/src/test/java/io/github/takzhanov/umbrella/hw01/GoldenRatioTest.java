package io.github.takzhanov.umbrella.hw01;

import org.junit.Assert;
import org.junit.Test;

public class GoldenRatioTest {
    @Test
    public void calc() {
        final Double gr = 1.6180339887498948482;
        final Double delta = 0.000000000001;
        Assert.assertEquals(gr, GoldenRatio.calc(delta), delta);
    }

}