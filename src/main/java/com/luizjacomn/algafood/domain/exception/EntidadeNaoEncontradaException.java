package com.luizjacomn.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 3752112404642596859L;

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}

}
