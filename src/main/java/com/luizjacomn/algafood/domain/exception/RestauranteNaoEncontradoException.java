package com.luizjacomn.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 7503934280689835911L;

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this(String.format("Restaurante com id %d não foi encontrado", restauranteId));
    }
}
