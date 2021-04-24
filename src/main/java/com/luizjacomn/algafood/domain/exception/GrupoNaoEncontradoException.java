package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -3505042133585400658L;

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

}
