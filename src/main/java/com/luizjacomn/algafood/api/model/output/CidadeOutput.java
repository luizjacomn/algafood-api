package com.luizjacomn.algafood.api.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Fortaleza")
    private String nome;

    private EstadoOutput estado;

}
