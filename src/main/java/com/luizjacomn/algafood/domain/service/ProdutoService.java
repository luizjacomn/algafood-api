package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.model.Produto;
import com.luizjacomn.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long produtoId, Long restauranteId) {
        try {
            Produto produto = buscar(produtoId, restauranteId);

            produtoRepository.delete(produto);

            produtoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw EntidadeEmUsoException.nomeMasculino("Produto");
        }
    }

    public Produto buscar(Long produtoId, Long restauranteId) {
        return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }
}
