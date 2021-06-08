package com.luizjacomn.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputV2 {

    @ApiModelProperty(example = "Fortaleza", required = true)
    @NotBlank
    private String cidadeNome;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long estadoId;
}
