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
import java.util.Map;
import java.util.function.Consumer;

public class MySerializator {

    public String toJson(Object object) {
        if (object == null) {
            return "null";
        } else if (object instanceof Boolean) {
            return object.toString();
        } else if (object instanceof String) {
            return "\"" + object.toString() + "\"";
        } else if (object instanceof Number) {
            return object.toString();
        } else if (object.getClass().isArray()) {
            JSONArray jsonArray = arrayToJSONArray(object);
            return jsonArray.toJSONString();
        } else {
            JSONObject jsonObject = objectToJSONObject(object);
            return jsonObject.toJSONString();
        }
    }

    public <T> T fromJson(String jsonString, @NotNull Class<T> clazz) {
        if (null == jsonString || jsonString.trim().equals("")) {
            return null;
        }
        try {
            JSONParser parser = new JSONParser();
            Object parsedObject = parser.parse(jsonString);
            return fromParsedObject(parsedObject, clazz);
        } catch (ParseException e) {
            throw new RuntimeException("Can't parse jsonString", e);
        }
    }

    private JSONArray arrayToJSONArray(@NotNull Object object) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object item = Array.get(object, i);
            if (null == item) {
                jsonArray.add(null);
            } else if (item.getClass().isPrimitive()) {
                jsonArray.add(item);
            } else if (item instanceof Boolean) {
                jsonArray.add(item);
            } else if (item instanceof String) {
                jsonArray.add(item);
            } else if (item instanceof Number) {
                jsonArray.add(item);
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
                Object fieldValue = ReflectionHelper.getFieldValue(field, object);
                if (null == fieldValue) {
                    continue;
                } else if (field.getType().isPrimitive()) {
                    result.put(fieldName, fieldValue);
                } else if (fieldValue instanceof Boolean) {
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

    private <T> T fromLong(@NotNull Long parsedObject, @NotNull Class<T> clazz) {
        if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
            return (T) Byte.valueOf((parsedObject).byteValue());
        } else if (Short.class.equals(clazz) || short.class.equals(clazz)) {
            return (T) Short.valueOf((parsedObject).shortValue());
        } else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            return (T) Integer.valueOf((parsedObject).intValue());
        } else {
            return (T) parsedObject;
        }
    }

    private <T> T fromDouble(@NotNull Double parsedObject, @NotNull Class<T> clazz) {
        if (Float.class.equals(clazz) || float.class.equals(clazz)) {
            return (T) Float.valueOf(parsedObject.floatValue());
        } else {
            return (T) parsedObject;
        }
    }


    private <T> T fromJSONObject(@NotNull JSONObject parsedObject, @NotNull Class<T> clazz) {
        final T result = ReflectionHelper.instantiate(clazz);
        parsedObject.entrySet().forEach(new Consumer<Map.Entry<String, Object>>() {
            @Override
            public void accept(Map.Entry<String, Object> entry) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    ReflectionHelper.setFieldValue(result, fieldName, fromParsedObject(fieldValue, field.getType()));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }
        });
        return result;
    }

    private <T> T fromJSONArray(@NotNull JSONArray parsedObject, @NotNull Class<T> clazz) {
        T objects = (T) Array.newInstance(clazz.getComponentType(), parsedObject.size());
        for (int i = 0; i < parsedObject.size(); i++) {
            Array.set(objects, i, fromParsedObject(parsedObject.get(i), clazz.getComponentType()));
        }
        return objects;
    }

    private <T> T fromParsedObject(Object parsedObject, @NotNull Class<T> clazz) {
        if (null == parsedObject) {
            return null;
        } else if (parsedObject instanceof Boolean) {
            return (T) parsedObject;
        } else if (parsedObject instanceof String) {
            return (T) parsedObject;
        } else if (parsedObject instanceof Long) {
            return fromLong((Long) parsedObject, clazz);
        } else if (parsedObject instanceof Double) {
            return fromDouble((Double) parsedObject, clazz);
        } else if (parsedObject instanceof JSONArray) {
            return fromJSONArray((JSONArray) parsedObject, clazz);
        } else if (parsedObject instanceof JSONObject) {
            return fromJSONObject((JSONObject) parsedObject, clazz);
        } else {
            throw new IllegalArgumentException("parsedObject must be one of: " +
                    "Boolean, String, Long, Double, JSONObject or JSONArray");
        }
    }
}
