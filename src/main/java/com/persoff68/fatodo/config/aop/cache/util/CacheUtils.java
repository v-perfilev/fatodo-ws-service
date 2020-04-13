package com.persoff68.fatodo.config.aop.cache.util;

import com.persoff68.fatodo.exception.CacheException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CacheUtils {

    private CacheUtils() {
    }

    public static Object getValue(String[] names, Object[] args, String key) {
        if (names.length != args.length) {
            throw new CacheException("Args not valid");
        }
        key = handleKey(key);
        int keyIndex = find(names, key);
        return args[keyIndex];
    }

    @SuppressWarnings("unchecked")
    public static Collection<Object> getCollectionValue(String[] names, Object[] args, String key) {
        if (names.length != args.length) {
            throw new CacheException("Args not valid");
        }
        String[] keyParts = handleKeyParts(key);
        int keyIndex = find(names, keyParts[0]);
        Object object = args[keyIndex];
        Object result = getValueFromObjectByKeyParts(object, keyParts);
        if (result instanceof Collection) {
            return (Collection<Object>) result;
        } else {
            return List.of(result);
        }
    }

    private static Object getValueFromObjectByKeyParts(Object object, String[] keyParts) {
        if (keyParts.length == 1) {
            return object;
        }
        keyParts = Arrays.copyOfRange(keyParts, 1, keyParts.length);
        String key = keyParts[0];
        if (object instanceof Collection) {
            Collection<?> collection = (Collection<?>) object;
            Collection<Object> resultCollection = new ArrayList<>();
            for (Object o : collection) {
                Object result = getChildFieldByName(o, key);
                resultCollection.add(result);
            }
            return getValueFromObjectByKeyParts(resultCollection, keyParts);
        } else {
            Object result = getChildFieldByName(object, key);
            return getValueFromObjectByKeyParts(result, keyParts);
        }
    }

    private static Object getChildFieldByName(Object object, String name) {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] fieldNames = Arrays.stream(fields).map(Field::getName).toArray(String[]::new);
        int fieldIndex = find(fieldNames, name);
        Field field = fields[fieldIndex];
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new CacheException("Error during reading key");
        }
    }

    private static String handleKey(String key) {
        String regex = "^#\\w+$";
        if (!key.matches(regex)) {
            throw new CacheException("Key not valid");
        }
        return key.substring(1);
    }

    private static String[] handleKeyParts(String key) {
        String regex = "^#\\w+(\\.[\\w]+)*$";
        if (!key.matches(regex)) {
            throw new CacheException("Key not valid");
        }
        key = key.substring(1);
        return key.split("\\.");
    }

    private static <T> int find(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (target.equals(array[i])) {
                return i;
            }
        }
        throw new CacheException("Key not found");
    }

}
