package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw05.ReflectionHelper;
import io.github.takzhanov.umbrella.hw09.common.ResultHandler;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrmHelper {
    private static Map<Class<? extends DataSet>, EntityDefinition> cache = new HashMap<>();

    public static <T extends DataSet> String makeInsertStatement(T dataSet) {
        EntityDefinition definition = getEntityDefinition(dataSet.getClass());

        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for (Map.Entry<Field, String> entry : definition.getFieldToColumn().entrySet()) {
            Object value = ReflectionHelper.getFieldValue(entry.getKey(), dataSet);
            if (value == null) {
                continue;
            }
            if (entry.getKey().getAnnotation(Id.class) != null && (long) value == 0) {
                continue;
            }
            columns.add(entry.getValue());
            values.add("'" + value.toString() + "'");
        }

        return String.format("insert into %s (%s) values (%s)",
                definition.getTableName(),
                String.join(",", columns),
                String.join(",", values));
    }

    public static <T extends DataSet> String makeFindByIdStatement(long id, Class<T> clazz) {
        EntityDefinition definition = getEntityDefinition(clazz);
        String idColumn = "id";
        try {
            idColumn = definition.getFieldToColumn().get(DataSet.class.getDeclaredField("id"));
        } catch (NoSuchFieldException ignored) {
        }
        return String.format("select %s from %s where %s=%d",
                String.join(",", definition.getColumnToField().keySet()),
                definition.getTableName(),
                idColumn,
                id);
    }

    public static <T extends UserDataSet> String makeFindByNameStatement(String name, Class<T> clazz) {
        EntityDefinition definition = getEntityDefinition(clazz);
        String nameColumn = "name";
        try {
            nameColumn = definition.getFieldToColumn().get(UserDataSet.class.getDeclaredField("name"));
        } catch (NoSuchFieldException ignored) {
        }
        return String.format("select %s from %s where %s='%s'",
                String.join(",", definition.getColumnToField().keySet()),
                definition.getTableName(),
                nameColumn,
                name);
    }

    public static <T extends DataSet> String makeFindAllStatement(Class<T> clazz) {
        EntityDefinition definition = getEntityDefinition(clazz);
        return String.format("select %s from %s",
                String.join(",", definition.getColumnToField().keySet()),
                definition.getTableName());
    }

    public static <T extends DataSet> ResultHandler<List<T>> makeResultHandler(Class<T> clazz) {
        EntityDefinition definition = getEntityDefinition(clazz);
        return new ResultHandler<List<T>>() {
            @Override
            public List<T> handle(ResultSet resultSet) throws SQLException {
                List<T> list = new ArrayList<>();
                while (resultSet.next()) {
                    T instance = ReflectionHelper.instantiate(clazz);
                    for (Map.Entry<Field, String> entry : definition.getFieldToColumn().entrySet()) {
                        ReflectionHelper.setFieldValue(instance, entry.getKey(), resultSet.getObject(entry.getValue()));
                    }
                    list.add(instance);
                }
                return list;
            }
        };
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
