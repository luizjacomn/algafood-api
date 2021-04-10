package com.luizjacomn.algafood.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -8677383905291121191L;

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}
}
