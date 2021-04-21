package com.luizjacomn.algafood.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.luizjacomn.algafood.domain.model.Estado;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
