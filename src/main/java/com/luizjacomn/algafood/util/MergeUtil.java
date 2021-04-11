package com.luizjacomn.algafood.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public final class MergeUtil {

    public static <T> void mergeMapIntoObject(Map<String, Object> values, T object) {
        Class<T> clazz = (Class<T>) object.getClass();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        T convertedValue = mapper.convertValue(values, clazz);

        values.keySet().forEach(key -> {
            Field field = ReflectionUtils.findField(clazz, key);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, convertedValue);

            ReflectionUtils.setField(field, object, newValue);
        });
    }
}
