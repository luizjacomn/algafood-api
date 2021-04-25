package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.core.enums.Genero;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -2818690919031540417L;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(String nomeEntidade, Long id, Genero genero) {
        super(nomeEntidade, id, genero);
    }
}
