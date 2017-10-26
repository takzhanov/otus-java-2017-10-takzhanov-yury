package io.github.takzhanov.umbrella.hw02;

import org.junit.Assert;
import org.junit.Test;

import static io.github.takzhanov.umbrella.hw02.ObjectSizeAnalyzer.getDeepObjectSize;
import static io.github.takzhanov.umbrella.hw02.ObjectSizeAnalyzer.getObjectSize;

//тесты работают только с агентом в опциях
public class ObjectSizeAnalyzerTest {
    @Test
    public void sizeOfNull() {
        Assert.assertEquals(0, getObjectSize(null));
        Assert.assertEquals(0, getDeepObjectSize(null));
    }

    @Test
    public void testLink() {
        Assert.assertTrue(getObjectSize(new Object()) >= 8);
        Assert.assertEquals(getDeepObjectSize(new Object()), getDeepObjectSize(new A()));
        Assert.assertTrue(getDeepObjectSize(new AWithLink()) <= getDeepObjectSize(new A()) + 8);
        AWithLink aWithLink = new AWithLink();
        AWithLink bWithLink = new AWithLink();
        aWithLink.link = bWithLink;
        bWithLink.link = aWithLink;
        getDeepObjectSize(aWithLink);
    }

    @Test
    public void testPrivate() {
        getDeepObjectSize(new BWithPrivateField());
    }

    public static class A {
    }

    public static class AWithLink {
        Object link;
    }

    public static class BWithPrivateField {
        private Object o = new Object();
    }

}