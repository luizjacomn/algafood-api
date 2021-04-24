package com.luizjacomn.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoInputId {

    @NotNull
    private Long id;

}
