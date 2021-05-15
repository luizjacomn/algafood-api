package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.FotoProdutoInput;
import com.luizjacomn.algafood.api.model.output.FotoProdutoOutput;
import com.luizjacomn.algafood.domain.model.FotoProduto;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoMapper extends GenericMapper<FotoProduto, FotoProdutoInput, FotoProdutoOutput> {

    @Override
    public FotoProduto toEntity(FotoProdutoInput input) {
        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setNomeArquivo(input.getArquivo().getOriginalFilename());
        fotoProduto.setDescricao(input.getDescricao());
        fotoProduto.setContentType(input.getArquivo().getContentType());
        fotoProduto.setTamanho(input.getArquivo().getSize());
        fotoProduto.setProduto(input.getProduto());

        return fotoProduto;
    }
}
