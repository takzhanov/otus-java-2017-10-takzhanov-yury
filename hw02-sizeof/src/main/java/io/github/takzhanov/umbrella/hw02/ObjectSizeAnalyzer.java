package io.github.takzhanov.umbrella.hw02;

import io.github.takzhanov.umbrella.hw02.agent.ObjectSizeFetcherAgent;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;

public class ObjectSizeAnalyzer {
    /**
     * Возвращает размер самого объекта и всех достижимых из него
     *
     * @param o
     * @return
     */
    public static long getDeepObjectSize(Object o) {
        return getDeepObjectSize(o, new IdentityHashMap<>());
    }

    private static long getDeepObjectSize(Object o, IdentityHashMap<Object, Boolean> touched) {
        if (null == o) {
            return 0;
        }
        if (touched.containsKey(o)) {
            return 0; //уже считали
        }
        // примитивы и ссылки
        long result = ObjectSizeFetcherAgent.getObjectSize(o);
        touched.put(o, true);
        Class clazz = o.getClass();
        // если это массив объектов - то идем по элементам
        if (clazz.isArray() && !clazz.getComponentType().isPrimitive()) {
            int length = Array.getLength(o);
            for (int i = 0; i < length; i++) {
                Object arrayElement = Array.get(o, i);
                result += getDeepObjectSize(arrayElement, touched);
            }
        }
        // идем по ссылочным полям
        for (Field f : clazz.getDeclaredFields()) {
            // пропускаем - учтены в getObjectSize
            if (f.getType().isPrimitive()) {
                continue;
            }
            // пропускаем статики
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            f.setAccessible(true);
            try {
                result += getDeepObjectSize(f.get(o), touched);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Возвращает размер объекта в байтах:
     * сумма примитивов + сумма ссылок + выравнивание
     *
     * @param o
     * @return
     */
    public static long getObjectSize(Object o) {
        return ObjectSizeFetcherAgent.getObjectSize(o);
    }
}
