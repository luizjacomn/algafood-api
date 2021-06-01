package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.RestauranteProdutoController;
import com.luizjacomn.algafood.api.model.input.ProdutoInput;
import com.luizjacomn.algafood.api.model.output.ProdutoOutput;
import com.luizjacomn.algafood.domain.model.Produto;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ProdutoMapper extends GenericRepresentationModelMapper<Produto, ProdutoInput, ProdutoOutput, RestauranteProdutoController> {

    public ProdutoMapper() {
        addFilterTemplateVariable("incluir-inativos", TemplateVariable.VariableType.REQUEST_PARAM);
    }

    @Override
    public ProdutoOutput toModel(Produto entity) {
        ProdutoOutput output = modelMapper.map(entity, outputClass);

        output.add(linkTo(controllerClass, entity.getRestaurante().getId()).slash(getIdentifier(output)).withSelfRel());

        output.add(linkToCollection(entity.getRestaurante().getId()));

        output.add(linkTo(controllerClass, entity.getRestaurante().getId()).slash(getIdentifier(output))
                .slash("/foto").withRel("foto"));

        return output;
    }

    @Override
    public CollectionModel<ProdutoOutput> toCollectionModel(Iterable<? extends Produto> entities) {
        Long idRestaurante = entities.iterator().next().getRestaurante().getId();
        return super.toCollectionModel(entities, linkToCollection(idRestaurante));
    }

    @Override
    protected boolean hasCollectionUriTemplate() {
        return true;
    }

    @Override
    public Serializable getIdentifier(ProdutoOutput output) {
        return output.getId();
    }
}
