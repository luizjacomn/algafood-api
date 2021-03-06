package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v1.controller.*;
import com.luizjacomn.algafood.api.v1.model.input.RestauranteInput;
import com.luizjacomn.algafood.api.v1.model.output.RestauranteOutput;
import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestauranteMapper extends GenericRepresentationModelMapper<Restaurante, RestauranteInput, RestauranteOutput, RestauranteController> {

    public RestauranteMapper() {
        addFilterTemplateVariable("campos", TemplateVariable.VariableType.REQUEST_PARAM);
    }

    @Override
    public RestauranteOutput toModel(Restaurante entity) {
        RestauranteOutput restauranteOutput = super.toModel(entity);

        restauranteOutput.getCozinha().add(linkTo(CozinhaController.class)
                .slash(restauranteOutput.getCozinha().getId()).withSelfRel());

        if (restauranteOutput.getEndereco() != null && restauranteOutput.getEndereco().getCidade() != null) {
            restauranteOutput.getEndereco().getCidade().add(linkTo(CidadeController.class)
                    .slash(restauranteOutput.getEndereco().getCidade().getId()).withSelfRel());
        }

        restauranteOutput.add(linkTo(methodOn(RestauranteProdutoController.class).listar(restauranteOutput.getId(), null))
                .withRel("produtos"));

        restauranteOutput.add(linkTo(RestauranteFormaPagamentoController.class, restauranteOutput.getId())
                .withRel("formas-pagamento"));

        restauranteOutput.add(linkTo(RestauranteResponsavelController.class, restauranteOutput.getId())
                .withRel("responsaveis"));

        if (entity.podeAbrir()) {
            restauranteOutput.add(linkTo(controllerClass).slash(restauranteOutput.getId()).slash("/abrir")
                    .withRel("abrir"));
        }

        if (entity.podeFechar()) {
            restauranteOutput.add(linkTo(controllerClass).slash(restauranteOutput.getId()).slash("/fechar")
                    .withRel("fechar"));
        }

        if (entity.podeAtivar()) {
            restauranteOutput.add(linkTo(controllerClass).slash(restauranteOutput.getId()).slash("/ativar")
                    .withRel("ativar"));
        }

        if (entity.podeDesativar()) {
            restauranteOutput.add(linkTo(controllerClass).slash(restauranteOutput.getId()).slash("/desativar")
                    .withRel("desativar"));
        }

        return restauranteOutput;
    }

    @Override
    protected boolean hasCollectionUriTemplate() {
        return true;
    }

    @Override
    public Serializable getIdentifier(RestauranteOutput output) {
        return output.getId();
    }

    @Override
    public String[] getRelationshipAttributes() {
        return new String[]{"cozinha", "endereco.cidade"};
    }
}
