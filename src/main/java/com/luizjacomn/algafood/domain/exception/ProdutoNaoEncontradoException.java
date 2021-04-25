package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 7503934280689835911L;

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d", produtoId, restauranteId));
    }
}
