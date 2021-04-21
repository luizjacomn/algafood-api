package com.luizjacomn.algafood.api.model.converter;

import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.domain.model.Cidade;
import org.springframework.stereotype.Component;

@Component
public class CidadeConverter extends GenericConverter<Cidade, CidadeInput, CidadeOutput> {

    @Override
    public String[] getRelationshipAttributes() {
        return new String[] { "estado" };
    }
}
