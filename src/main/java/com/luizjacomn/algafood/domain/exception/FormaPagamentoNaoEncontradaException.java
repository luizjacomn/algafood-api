package com.luizjacomn.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -6893353224456594333L;

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format("Forma de pagamento com id %d não foi encontrada", id));
    }
}
