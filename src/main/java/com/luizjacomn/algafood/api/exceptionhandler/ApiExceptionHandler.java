package com.luizjacomn.algafood.api.exceptionhandler;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Problema> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        Problema problema = new Problema.ProblemaBuilder()
                                        .dataHorario(LocalDateTime.now())
                                        .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(problema);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Problema> tratarEntidadeEmUsoException() {
        Problema problema = new Problema.ProblemaBuilder()
                                        .dataHorario(LocalDateTime.now())
                                        .mensagem("Entidade não pode ser excluída pois está sendo usada por outros registros")
                                        .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Problema> tratarNegocioException(NegocioException e) {
        Problema problema = new Problema.ProblemaBuilder()
                                        .dataHorario(LocalDateTime.now())
                                        .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(problema);
    }
}
