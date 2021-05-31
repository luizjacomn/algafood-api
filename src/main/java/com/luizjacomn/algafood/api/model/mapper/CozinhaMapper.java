package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.CozinhaController;
import com.luizjacomn.algafood.api.model.input.CozinhaInput;
import com.luizjacomn.algafood.api.model.output.CozinhaOutput;
import com.luizjacomn.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CozinhaMapper extends GenericRepresentationModelMapper<Cozinha, CozinhaInput, CozinhaOutput, CozinhaController> {

    @Override
    public Serializable getIdentifier(CozinhaOutput output) {
        return output.getId();
    }
}
