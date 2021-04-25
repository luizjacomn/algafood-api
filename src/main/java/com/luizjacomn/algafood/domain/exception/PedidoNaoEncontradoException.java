package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.core.enums.Genero;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -5176296147872923002L;

    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(String nomeEntidade, Long id, Genero genero) {
        super(nomeEntidade, id, genero);
    }
}
