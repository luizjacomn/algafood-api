package com.luizjacomn.algafood.api.exceptionhandler;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, "O corpo da requisição está inválido. Verifique erro de sintaxe.", request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        return handle(ProblemType.ENTIDADE_NAO_ENCONTRADA, ex, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        return handle(ProblemType.ENTIDADE_EM_USO, ex, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        return handle(ProblemType.ERRO_NEGOCIO, ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (!(body instanceof Problem)) {
            body = new Problem.Builder()
                    .withStatus(status.value())
                    .withTitle(body != null ? (String) body : status.getReasonPhrase())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handle(ProblemType problemType, Exception ex, WebRequest request) {
        return handle(problemType, ex, ex.getMessage(), request);
    }

    private ResponseEntity<Object> handle(ProblemType problemType, Exception ex, String message, WebRequest request) {
        Problem problem = new Problem.Builder()
                .withProblemType(problemType)
                .withDetail(message)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.resolve(problem.getStatus()), request);
    }

}
