package com.luizjacomn.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 2425106641271687212L;

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Estado com id %d não foi encontrado", estadoId));
    }
}
