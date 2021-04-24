package com.luizjacomn.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioSenhaInput extends UsuarioInput {

    @NotBlank
    private String senha;
}
