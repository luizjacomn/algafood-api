package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.domain.model.Cidade;
import org.springframework.stereotype.Component;

@Component
public class CidadeMapper extends GenericMapper<Cidade, CidadeInput, CidadeOutput> {

    @Override
    public String[] getRelationshipAttributes() {
        return new String[] { "estado" };
    }
}
