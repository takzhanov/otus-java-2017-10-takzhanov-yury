package io.github.takzhanov.umbrella.hw08;

import io.github.takzhanov.umbrella.hw05.ReflectionHelper;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MySerializator {
    public String toJson(Object object) {
        if (object == null) {
            return null;
        }
        if (object.getClass().isArray()) {
            JSONArray jsonArray = arrayToJSONArray(object);
            return jsonArray.toJSONString();
        } else {
            JSONObject jsonObject = objectToJSONObject(object);
            return jsonObject.toJSONString();
        }
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (null == jsonString || "null".equals(jsonString)) {
            return null;
        }
        JSONParser parser = new JSONParser();
        T result = ReflectionHelper.instantiate(clazz);
        try {
            Object parsedObject = parser.parse(jsonString);
//            if (parsedObject instanceof )
//            switch (parsedObject.getClass().getTypeName()) {
            case "String":
//
//            }

        } catch (ParseException e) {
            throw new RuntimeException("Can't parse jsonString");
        }
        return result;
    }

    private JSONArray arrayToJSONArray(@NotNull Object object) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object item = Array.get(object, i);
            if (item == null) {
                jsonArray.add(null);
            } else {
                jsonArray.add(objectToJSONObject(item));
            }
        }
        return jsonArray;
    }

    private JSONObject objectToJSONObject(@NotNull Object object) {
        JSONObject result = new JSONObject();
        Class<?> klass = object.getClass();
        while (klass != null && klass != Object.class) {
            for (Field field : klass.getDeclaredFields()) {
                if (Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                String fieldName = field.getName();
                Object fieldValue = ReflectionHelper.getFieldValue(object, fieldName);
                if (null == fieldValue) {
                    continue;
                } else if (field.getType().isPrimitive()) {
                    result.put(fieldName, fieldValue);
                } else if (fieldValue instanceof String) {
                    result.put(fieldName, fieldValue);
                } else if (fieldValue instanceof Number) {
                    result.put(fieldName, fieldValue);
                } else if (field.getType().isArray()) {
                    result.put(fieldName, arrayToJSONArray(fieldValue));
                } else {
                    result.put(fieldName, objectToJSONObject(fieldValue));
                }
            }
            klass = klass.getSuperclass();
        }
        return result;
    }

    private Object arrayFromJSONArray(@NotNull Object object) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(object); i++) {
            jsonArray.add(objectToJSONObject(Array.get(object, i)));
        }
        return jsonArray;
    }

    private Object objectFromJSONObject() {
        return null;
    }
}
