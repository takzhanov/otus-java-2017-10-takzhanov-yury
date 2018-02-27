package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.domain.DataSet;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class EntityDefinition {
    private String tableName;
    private Map<Field, String> fieldToColumn = new HashMap<>();
    private Map<String, Field> columnToField = new HashMap<>();

    private EntityDefinition() {
    }

    public static <T extends DataSet> EntityDefinition of(Class<T> clazz) {
        EntityDefinition entityDefinition = new EntityDefinition();
        String tableNameFromAnnotation = clazz.getAnnotation(Table.class).name();
        if (tableNameFromAnnotation.trim().equals("")) {
            entityDefinition.tableName = clazz.getSimpleName();
        } else {
            entityDefinition.tableName = tableNameFromAnnotation;
        }
        Class<?> klass = clazz;
        while (klass != null && klass != Object.class) {
            for (Field field : klass.getDeclaredFields()) {
                if (Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                String fieldName = field.getName();
                String columnNameFromAnnotation = field.getAnnotation(Column.class).name();
                String columnName;
                if (columnNameFromAnnotation.trim().equals("")) {
                    columnName = fieldName;
                } else {
                    columnName = tableNameFromAnnotation;
                }
                entityDefinition.getFieldToColumn().put(field, columnName);
                entityDefinition.getColumnToField().put(columnName, field);
            }
            klass = klass.getSuperclass();
        }
        return entityDefinition;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<Field, String> getFieldToColumn() {
        return fieldToColumn;
    }

    public Map<String, Field> getColumnToField() {
        return columnToField;
    }
}
