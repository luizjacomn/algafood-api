package com.luizjacomn.algafood.domain.exception;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -5176296147872923002L;

    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Pedido com código %s não foi encontrado", codigo));
    }
}
