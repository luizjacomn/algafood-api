package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v1.controller.CozinhaController;
import com.luizjacomn.algafood.api.v1.model.input.CozinhaInput;
import com.luizjacomn.algafood.api.v1.model.output.CozinhaOutput;
import com.luizjacomn.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CozinhaMapper extends GenericRepresentationModelMapper<Cozinha, CozinhaInput, CozinhaOutput, CozinhaController> {

    @Override
    protected boolean hasSort() {
        return true;
    }

    @Override
    protected boolean hasCollectionUriTemplate() {
        return true;
    }

    @Override
    public Serializable getIdentifier(CozinhaOutput output) {
        return output.getId();
    }
}
