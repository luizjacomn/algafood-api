package com.luizjacomn.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RestauranteOutput extends RepresentationModel<RestauranteOutput> {

    private Long id;

    private String nome;

    private BigDecimal frete;

    private CozinhaOutput cozinha;

    private EnderecoOutput endereco;

    private boolean ativo;

    private boolean aberto;

}
