package com.luizjacomn.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class GrupoOutput {

    private Long id;

    private String nome;
}
