package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 780889075475293768L;

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

}
