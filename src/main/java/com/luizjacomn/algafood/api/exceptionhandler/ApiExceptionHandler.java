package com.luizjacomn.algafood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.NegocioException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.stream.Collectors.joining;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            InvalidFormatException root = (InvalidFormatException) rootCause;
            String path = root.getPath().stream().map(Reference::getFieldName)
                    .collect(joining("."));
            String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                    "Corrija e informe um valor compatível com o tipo correto: %s.", path, root.getValue(), root.getTargetType().getSimpleName());
            return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, detail, request);
        }

        if (rootCause instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException root = (UnrecognizedPropertyException) rootCause;
            String path = root.getPath().stream().map(Reference::getFieldName)
                    .collect(joining("."));
            String detail = String.format("A propriedade '%s' não existe. " +
                    "Corrija ou remova essa propriedade e tente novamente.", path);
            return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, detail, request);
        }

        if (rootCause instanceof IgnoredPropertyException) {
            IgnoredPropertyException root = (IgnoredPropertyException) rootCause;
            String path = root.getPath().stream().map(Reference::getFieldName)
                    .collect(joining("."));
            String detail = String.format("A propriedade '%s' foi marcada como ignorada. " +
                    "Corrija ou remova essa propriedade e tente novamente.", path);
            return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, detail, request);
        }

        return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, "O corpo da requisição está inválido. Verifique erro de sintaxe.", request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException root = (MethodArgumentTypeMismatchException) ex;
            String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                    "Corrija e informe um valor compatível com o tipo %s.", root.getName(), root.getValue(), root.getRequiredType().getSimpleName());
            return handle(ProblemType.PARAMETRO_INVALIDO, ex, detail, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso %s, que você tentou acessar, não existe.", ex.getRequestURL());
        return handle(ProblemType.RECURSO_NAO_ENCONTRADO, ex, detail, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        return handle(ProblemType.RECURSO_NAO_ENCONTRADO, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDemaisExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();// TODO substituir por log
        return handle(ProblemType.PROBLEMA_NO_SISTEMA, ex, "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema " +
                "persistir, entre em contato com o administardor do sistema.", request);
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
