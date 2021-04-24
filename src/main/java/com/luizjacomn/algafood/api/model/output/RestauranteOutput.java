package com.luizjacomn.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RestauranteOutput {

    private Long id;

    private String nome;

    private BigDecimal frete;

    private CozinhaOutput cozinha;

    private EnderecoOutput endereco;

    private boolean ativo;

}
