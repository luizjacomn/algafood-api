package com.luizjacomn.algafood.api.model.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class GenericConverter<E, I, O> implements RelationshipAttributes {

    private Class<E> entityClass;

    private Class<I> inputClass;

    private Class<O> outputClass;

    @Autowired
    private ModelMapper modelMapper;

    public GenericConverter() {
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
            Field field = entityClass.getDeclaredField(attribute);
            field.setAccessible(true);
            field.set(entity, field.getType().newInstance());
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

    public List<O> toOutputDTOList(List<E> entitys) {
        return entitys.stream()
                .map(this::toOutputDTO)
                .collect(toList());
    }
}
