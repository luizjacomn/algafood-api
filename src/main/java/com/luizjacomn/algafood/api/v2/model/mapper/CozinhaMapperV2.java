package com.luizjacomn.algafood.api.v2.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v2.controller.CozinhaControllerV2;
import com.luizjacomn.algafood.api.v2.model.input.CozinhaInputV2;
import com.luizjacomn.algafood.api.v2.model.output.CozinhaOutputV2;
import com.luizjacomn.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CozinhaMapperV2 extends GenericRepresentationModelMapper<Cozinha, CozinhaInputV2, CozinhaOutputV2, CozinhaControllerV2> {

    @Override
    protected boolean hasSort() {
        return true;
    }

    @Override
    protected boolean hasCollectionUriTemplate() {
        return true;
    }

    @Override
    public Serializable getIdentifier(CozinhaOutputV2 output) {
        return output.getCozinhaId();
    }
}
