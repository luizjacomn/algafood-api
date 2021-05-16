package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.FotoProdutoInput;
import com.luizjacomn.algafood.api.model.output.FotoProdutoOutput;
import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.model.Produto;
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

        return fotoProduto;
    }

    public FotoProduto toEntity(FotoProdutoInput fotoProdutoInput, Produto produto) {
        FotoProduto fotoProduto = this.toEntity(fotoProdutoInput);
        fotoProduto.setProduto(produto);

        return fotoProduto;
    }
}
