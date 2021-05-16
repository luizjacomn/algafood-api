package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {

        Optional<FotoProduto> optional = produtoRepository.findFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId());

        if (optional.isPresent()) {
            produtoRepository.delete(optional.get());
        }

        return produtoRepository.save(fotoProduto);
    }

}
