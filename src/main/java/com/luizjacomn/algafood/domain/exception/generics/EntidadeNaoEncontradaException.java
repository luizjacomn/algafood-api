package com.luizjacomn.algafood.domain.exception.generics;

import com.luizjacomn.algafood.core.enums.Genero;

public class EntidadeNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 3752112404642596859L;

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException(String nomeEntidade, Long id, Genero genero) {
        this(String.format("%s com id %d não foi encontrad%s",
                nomeEntidade,
                id,
                Genero.MASCULINO.equals(genero) ? "o" : "a"));
    }

    public static EntidadeNaoEncontradaException nomeMasculino(String nomeEntidade, Long id) {
        return new EntidadeNaoEncontradaException(nomeEntidade, id, Genero.MASCULINO);
    }

    public static EntidadeNaoEncontradaException nomeFeminino(String nomeEntidade, Long id) {
        return new EntidadeNaoEncontradaException(nomeEntidade, id, Genero.FEMININO);
    }

}
