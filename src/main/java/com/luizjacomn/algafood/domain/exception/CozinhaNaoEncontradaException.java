package com.luizjacomn.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -552506044215860388L;

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("Cozinha com id %d não foi encontrada", cozinhaId));
    }
}
