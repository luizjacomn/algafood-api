package com.luizjacomn.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel("CozinhaInput")
@Getter
@Setter
public class CozinhaInputV2 {

    @NotBlank
    private String cozinhaNome;
}
