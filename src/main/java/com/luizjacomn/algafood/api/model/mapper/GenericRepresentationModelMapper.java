package com.luizjacomn.algafood.api.model.mapper;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class GenericRepresentationModelMapper<E, I, O extends RepresentationModel<O>, C>
        extends GenericMapper<E, I, O>
        implements RepresentationModelAssembler<E, O> {

    private Class<C> controllerClass;

    public GenericRepresentationModelMapper() {
        super();
        this.controllerClass = (Class<C>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[3];
    }

    @Override
    public CollectionModel<O> toCollectionModel(Iterable<? extends E> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(controllerClass).withRel(IanaLinkRelations.COLLECTION));
    }

    /**
     * @param entity
     * @throws RuntimeException if called
     * @deprecated for usage of {@link RepresentationModelAssembler#toModel(E)} instead
     */
    @Deprecated
    @Override
    public O toOutputDTO(E entity) {
        throw new RuntimeException(getMensagemMetodoInvalido());
    }

    /**
     * @param entities
     * @throws RuntimeException if called
     * @deprecated for usage of {@link RepresentationModelAssembler#toCollectionModel(Iterable)} instead
     */
    @Deprecated
    @Override
    public List<O> toOutputDTOList(Collection<E> entities) {
        throw new RuntimeException(getMensagemMetodoInvalido());
    }

    private String getMensagemMetodoInvalido() {
        return "Método inválido para classes que herdam: " + GenericRepresentationModelMapper.class.getSimpleName();
    }

}
