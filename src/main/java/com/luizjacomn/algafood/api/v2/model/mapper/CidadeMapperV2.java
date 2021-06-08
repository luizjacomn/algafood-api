package com.luizjacomn.algafood.api.v2.model.mapper;

import com.luizjacomn.algafood.api.mapper.GenericRepresentationModelMapper;
import com.luizjacomn.algafood.api.v2.controller.CidadeControllerV2;
import com.luizjacomn.algafood.api.v2.model.input.CidadeInputV2;
import com.luizjacomn.algafood.api.v2.model.output.CidadeOutputV2;
import com.luizjacomn.algafood.domain.model.Cidade;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CidadeMapperV2 extends GenericRepresentationModelMapper<Cidade, CidadeInputV2, CidadeOutputV2, CidadeControllerV2> {

    @Override
    public Serializable getIdentifier(CidadeOutputV2 output) {
        return output.getCidadeId();
    }
}
