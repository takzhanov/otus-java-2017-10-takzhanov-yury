package io.github.takzhanov.umbrella.hw02.agent;

import java.lang.instrument.Instrumentation;

public class ObjectSizeFetcherAgent {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    /**
     * Возвращает размер объекта в байтах:
     * сумма примитивов + сумма ссылок + выравнивание
     *
     * @param o
     * @return
     */
    public static long getObjectSize(Object o) {
        if (null == o) {
            return 0;
        }
        if (null == instrumentation) {
            throw new IllegalStateException("ObjectSizeFetcherAgent is not initialized");
        }
        return instrumentation.getObjectSize(o);
    }

}