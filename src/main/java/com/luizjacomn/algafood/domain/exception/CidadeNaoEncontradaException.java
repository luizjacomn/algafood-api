package com.luizjacomn.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -2818690919031540417L;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long cidadeId) {
        this(String.format("Cidade com id %d não foi encontrada", cidadeId));
    }
}
