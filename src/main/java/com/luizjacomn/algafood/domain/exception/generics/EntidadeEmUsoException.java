package com.luizjacomn.algafood.domain.exception.generics;

import com.luizjacomn.algafood.core.enums.Genero;

public class EntidadeEmUsoException extends RuntimeException {

    private static final long serialVersionUID = 542310275747145963L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }

    public EntidadeEmUsoException(String nomeEntidade, Genero genero) {
        this(String.format("%1$s está sendo utilizad%2$s e não pode ser excluíd%2$s",
				nomeEntidade,
				Genero.MASCULINO.equals(genero) ? "o" : "a"));
    }

    public static EntidadeEmUsoException nomeMasculino(String nomeEntidade) {
        return new EntidadeEmUsoException(nomeEntidade, Genero.MASCULINO);
    }

    public static EntidadeEmUsoException nomeFeminino(String nomeEntidade) {
        return new EntidadeEmUsoException(nomeEntidade, Genero.FEMININO);
    }
}

