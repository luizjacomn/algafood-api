package com.luizjacomn.algafood.infra.exception;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 4369257433005136997L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
