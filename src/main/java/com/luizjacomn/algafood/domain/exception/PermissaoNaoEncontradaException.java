package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.core.enums.Genero;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 4863861287449103254L;

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

}
