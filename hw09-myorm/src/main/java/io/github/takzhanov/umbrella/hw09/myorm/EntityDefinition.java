package io.github.takzhanov.umbrella.hw09.myorm;

import io.github.takzhanov.umbrella.hw09.domain.DataSet;

import java.util.Map;

public class EntityDefinition {
    private String tableName;
    private Map<String, String> fieldToColumn;
    private Map<String, String> columnToField;

    private EntityDefinition() {
    }

    public static <T extends DataSet> EntityDefinition of(Class<T> clazz) {
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getFieldToColumn() {
        return fieldToColumn;
    }

    public Map<String, String> getColumnToField() {
        return columnToField;
    }
}
