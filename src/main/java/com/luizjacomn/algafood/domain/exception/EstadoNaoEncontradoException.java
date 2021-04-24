package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 2425106641271687212L;

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

}
