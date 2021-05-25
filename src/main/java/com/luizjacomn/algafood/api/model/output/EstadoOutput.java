package com.luizjacomn.algafood.api.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Ceará")
    private String nome;

}
