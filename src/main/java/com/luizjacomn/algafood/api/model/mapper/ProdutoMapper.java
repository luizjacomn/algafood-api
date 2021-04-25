package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.ProdutoInput;
import com.luizjacomn.algafood.api.model.output.ProdutoOutput;
import com.luizjacomn.algafood.domain.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper extends GenericMapper<Produto, ProdutoInput, ProdutoOutput> {

//    @Override
//    public String[] getRelationshipAttributes() {
//        return new String[] { "restaurante" };
//    }
}
