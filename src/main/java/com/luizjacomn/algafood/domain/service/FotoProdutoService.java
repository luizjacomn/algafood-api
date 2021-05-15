package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        return produtoRepository.save(fotoProduto);
    }

}
