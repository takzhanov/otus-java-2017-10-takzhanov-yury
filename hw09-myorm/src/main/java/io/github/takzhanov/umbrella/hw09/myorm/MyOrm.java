package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.common.ResultHandler;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;

import java.util.HashMap;
import java.util.Map;

public class MyOrm {
    private static Map<Class<? extends DataSet>, EntityDefinition> cache = new HashMap<>();

    public static <T extends DataSet> String makeInsertStatement(T dataSet) {
        EntityDefinition definition = getEntityDefinition(dataSet.getClass());

        return null;
    }

    public static <T extends DataSet> String makeSelectStatement(long id, Class<T> clazz) {
        EntityDefinition definition = getEntityDefinition(clazz);
        return null;
    }

    public static <T extends DataSet> ResultHandler<T> makeResultHandler(Class<T> clazz) {
        EntityDefinition definition = getEntityDefinition(clazz);
        return null;
    }

    private static EntityDefinition getEntityDefinition(Class<? extends DataSet> clazz) {
        EntityDefinition definition = cache.get(clazz);
        if (definition == null) {
            cache.put(clazz, EntityDefinition.of(clazz));
            definition = cache.get(clazz);
        }
        return definition;
    }
}
