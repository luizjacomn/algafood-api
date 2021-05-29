package com.luizjacomn.algafood.api.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

interface RelationshipAttributes {

    default String[] getRelationshipAttributes() {
        return new String[0];
    }
}

public abstract class GenericMapper<E, I, O> implements RelationshipAttributes {

    @Autowired
    protected ModelMapper modelMapper;

    protected Class<E> entityClass;

    protected Class<I> inputClass;

    protected Class<O> outputClass;

    public GenericMapper() {
        this.entityClass = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.inputClass = (Class<I>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.outputClass = (Class<O>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[2];
    }

    /* to Entity */
    public E toEntity(I input) {
        return modelMapper.map(input, entityClass);
    }

    public void copyToEntity(I input, E entity) throws Exception {
        /* To avoid org.hibernate.HibernateException: identifier of an instance of
         * <entity.relationshipAttribute> was altered from 1 to 2
         */
        for (String attribute : getRelationshipAttributes()) {
            String[] objects = attribute.split("\\.");
            if (objects.length <= 1) {
                Field field = entityClass.getDeclaredField(attribute);
                field.setAccessible(true);
                field.set(entity, field.getType().newInstance());
            } else {
                Field tempField;
                Field entityField = entityClass.getDeclaredField(objects[0]);
                Object value = entity;
                for (int i = 1; i < objects.length; i++) {
                    tempField = entityField;
                    entityField = entityField.getType().getDeclaredField(objects[i]);
                    tempField.setAccessible(true);
                    value = tempField.get(value);
                    if (value == null) {
                        value = tempField.getType().newInstance();
                    }
                }
                entityField.setAccessible(true);
                entityField.set(value, entityField.getType().newInstance());
            }
        }

        modelMapper.map(input, entity);
    }

    /* to DTO */
    public I toInputDTO(E entity) {
        return modelMapper.map(entity, inputClass);
    }

    public O toOutputDTO(E entity) {
        return modelMapper.map(entity, outputClass);
    }

    public List<O> toOutputDTOList(Collection<E> entities) {
        return entities.stream()
                .map(this::toOutputDTO)
                .collect(toList());
    }

    public Page<O> toOutputDTOPage(Pageable pageable, Page<E> page) {
        return new PageImpl<O>(toOutputDTOList(page.getContent()), pageable, page.getTotalElements());
    }
}
