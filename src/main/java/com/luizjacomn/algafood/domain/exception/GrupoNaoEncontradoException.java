package com.luizjacomn.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -3505042133585400658L;

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }
    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Grupo com id %d não foi encontrado", id));
    }
}
