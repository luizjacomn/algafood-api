package com.luizjacomn.algafood.api.mapper;

import com.luizjacomn.algafood.api.v1.model.output.OutputIdentifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class GenericRepresentationModelMapper<E, I, O extends RepresentationModel<O>, C>
        extends GenericMapper<E, I, O>
        implements RepresentationModelAssembler<E, O>, OutputIdentifier<O> {

    protected List<TemplateVariable> variables = new ArrayList<>();

    protected Class<C> controllerClass;

    public GenericRepresentationModelMapper() {
        super();
        this.controllerClass = (Class<C>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[3];

        if (hasSort() && hasCollectionUriTemplate()) {
            variables.add(new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM));
            variables.add(new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM));
            variables.add(new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));
        }
    }

    protected boolean hasCollectionUriTemplate() {
        return false;
    }

    protected boolean hasSort() {
        return false;
    }

    protected void addFilterTemplateVariable(String name, TemplateVariable.VariableType type) {
        variables.add(new TemplateVariable(name, type));
    }

    @Override
    public O toModel(E entity) {
        O output = modelMapper.map(entity, outputClass);

        output.add(linkTo(controllerClass).slash(getIdentifier(output)).withSelfRel());

        addSelfCollectionLink(output);

        return output;
    }

    protected void addSelfCollectionLink(O output) {
        if (hasCollectionUriTemplate()) {
            TemplateVariables templateVariables = new TemplateVariables(variables);

            String path = linkTo(controllerClass).toUri().toString();

            output.add(Link.of(UriTemplate.of(path, templateVariables), IanaLinkRelations.COLLECTION));
        } else {
            output.add(linkTo(controllerClass).withRel(IanaLinkRelations.COLLECTION));
        }
    }

    protected Link linkToCollection(Long parentId) {
        if (hasCollectionUriTemplate()) {
            TemplateVariables templateVariables = new TemplateVariables(variables);

            String path = linkTo(controllerClass, parentId).toUri().toString();

            return Link.of(UriTemplate.of(path, templateVariables), IanaLinkRelations.COLLECTION);
        } else {
            return linkTo(controllerClass, parentId).withRel(IanaLinkRelations.COLLECTION);
        }
    }

    @Override
    public CollectionModel<O> toCollectionModel(Iterable<? extends E> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(controllerClass).withRel(IanaLinkRelations.COLLECTION));
    }

    public CollectionModel<O> toCollectionModel(Iterable<? extends E> entities, Object... args) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(controllerClass, args).withRel(IanaLinkRelations.COLLECTION));
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

    /**
     * @param pageable
     * @param page
     * @throws RuntimeException if called
     * @deprecated for usage of {@link org.springframework.data.web.PagedResourcesAssembler#toModel(Page, RepresentationModelAssembler)} instead
     */
    @Deprecated
    @Override
    public Page<O> toOutputDTOPage(Pageable pageable, Page<E> page) {
        throw new RuntimeException(getMensagemMetodoInvalido());
    }

    private String getMensagemMetodoInvalido() {
        return "Método inválido para classes que herdam: " + GenericRepresentationModelMapper.class.getSimpleName();
    }

}
