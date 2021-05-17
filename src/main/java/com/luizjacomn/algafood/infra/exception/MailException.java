package com.luizjacomn.algafood.infra.exception;

public class MailException extends RuntimeException {

    private static final long serialVersionUID = 4267126770827159605L;

    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
