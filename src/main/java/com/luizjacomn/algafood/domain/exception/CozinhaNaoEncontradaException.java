package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.core.enums.Genero;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -552506044215860388L;

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(String nomeEntidade, Long id, Genero genero) {
        super(nomeEntidade, id, genero);
    }
}
