package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 3729396788806778741L;

    public FotoProdutoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe uma foto cadastrada para o produto com código %d para o restaurante de código %d", produtoId, restauranteId));
    }
}
