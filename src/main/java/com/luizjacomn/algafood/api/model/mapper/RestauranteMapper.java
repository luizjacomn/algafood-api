package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.*;
import com.luizjacomn.algafood.api.model.input.RestauranteInput;
import com.luizjacomn.algafood.api.model.output.RestauranteOutput;
import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class RestauranteMapper extends GenericRepresentationModelMapper<Restaurante, RestauranteInput, RestauranteOutput, RestauranteController> {

    @Override
    public RestauranteOutput toModel(Restaurante entity) {
        RestauranteOutput restauranteOutput = super.toModel(entity);

        restauranteOutput.getCozinha().add(linkTo(CozinhaController.class)
                .slash(restauranteOutput.getCozinha().getId()).withSelfRel());

        if (restauranteOutput.getEndereco() != null) {
            restauranteOutput.getEndereco().getCidade().add(linkTo(CidadeController.class)
                    .slash(restauranteOutput.getEndereco().getCidade().getId()).withSelfRel());
        }

        restauranteOutput.add(linkTo(RestauranteFormaPagamentoController.class, restauranteOutput.getId())
                .withRel("formas-pagamento"));

        restauranteOutput.add(linkTo(RestauranteResponsavelController.class, restauranteOutput.getId())
                .withRel("responsaveis"));

        return restauranteOutput;
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
