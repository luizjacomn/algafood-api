package com.luizjacomn.algafood.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizjacomn.algafood.core.validation.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class MergeUtil {

    @Autowired
    private SmartValidator validator;

    public <T> void mergeMapIntoObject(Map<String, Object> values, T object, String objectName) {
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

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(object, objectName);

        validator.validate(object, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }
}
