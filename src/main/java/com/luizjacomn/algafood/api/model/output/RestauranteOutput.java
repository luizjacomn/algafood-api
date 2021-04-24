package com.luizjacomn.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutput {

    private Long id;

    private String nome;

    private BigDecimal frete;

    private CozinhaOutput cozinha;

    private boolean ativo;

}
