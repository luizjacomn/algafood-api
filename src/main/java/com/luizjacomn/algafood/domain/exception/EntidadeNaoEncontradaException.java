package com.luizjacomn.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 3752112404642596859L;

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}

}
