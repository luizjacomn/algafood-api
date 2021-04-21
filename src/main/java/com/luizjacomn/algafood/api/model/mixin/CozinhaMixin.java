package com.luizjacomn.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizjacomn.algafood.domain.model.Restaurante;

import java.util.List;

public class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}
