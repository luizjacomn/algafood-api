package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.core.enums.Genero;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 7503934280689835911L;

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(String nomeEntidade, Long id, Genero genero) {
        super(nomeEntidade, id, genero);
    }

}
