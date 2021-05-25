package com.luizjacomn.algafood.api.model.input.id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;

}
