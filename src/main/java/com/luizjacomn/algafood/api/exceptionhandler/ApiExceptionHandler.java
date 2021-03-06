package com.luizjacomn.algafood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.luizjacomn.algafood.core.validation.exception.ValidacaoException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            InvalidFormatException root = (InvalidFormatException) rootCause;
            String path = root.getPath().stream().map(Reference::getFieldName)
                    .collect(joining("."));
            String detail = String.format("A propriedade '%s' recebeu o valor '%s', que ?? de um tipo inv??lido. " +
                    "Corrija e informe um valor compat??vel com o tipo correto: %s.", path, root.getValue(), root.getTargetType().getSimpleName());
            return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, detail, request);
        }

        if (rootCause instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException root = (UnrecognizedPropertyException) rootCause;
            String path = root.getPath().stream().map(Reference::getFieldName)
                    .collect(joining("."));
            String detail = String.format("A propriedade '%s' n??o existe. " +
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

        return handle(ProblemType.MENSAGEM_INCOMPREENSIVEL, ex, "O corpo da requisi????o est?? inv??lido. Verifique erro de sintaxe.", request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileSizeLimitExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof FileSizeLimitExceededException) {
            FileSizeLimitExceededException fsleEx = (FileSizeLimitExceededException) rootCause;
            String detail = String.format("O tamanho do arquivo excede o m??ximo suportado de %d", fsleEx.getPermittedSize());
            return handle(ProblemType.TAMANHO_REQUISICAO_EXCEDIDO, fsleEx, detail, request);
        }

        return handle(ProblemType.TAMANHO_REQUISICAO_EXCEDIDO, ex, "Tamanho da requisi????o excede o m??ximo suportado", request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException root = (MethodArgumentTypeMismatchException) ex;
            String detail = String.format("O par??metro de URL '%s' recebeu o valor '%s', que ?? de um tipo inv??lido. " +
                    "Corrija e informe um valor compat??vel com o tipo %s.", root.getName(), root.getValue(), root.getRequiredType().getSimpleName());
            return handle(ProblemType.PARAMETRO_INVALIDO, ex, detail, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Um ou mais campos est??o inv??lidos. Fa??a o preenchimento correto e tente novamente.";
        return handle(ProblemType.DADOS_INVALIDOS, ex.getBindingResult(), ex, detail, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Um ou mais campos est??o inv??lidos. Fa??a o preenchimento correto e tente novamente.";
        return handle(ProblemType.DADOS_INVALIDOS, ex.getBindingResult(), ex, detail, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O m??todo HTTP '%s' n??o ?? suportado para este recurso.", ex.getMethod());
        return handle(ProblemType.METODO_HTTP_NA0_SUPORTADO, ex, detail, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso %s, que voc?? tentou acessar, n??o existe.", ex.getRequestURL());
        return handle(ProblemType.RECURSO_NAO_ENCONTRADO, ex, detail, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        return handle(ProblemType.DADOS_INVALIDOS, ex.getBindingResult(), ex, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        return handle(ProblemType.RECURSO_NAO_ENCONTRADO, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDemaisExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
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
        return handle(problemType, null, ex, ex.getMessage(), request);
    }

    private ResponseEntity<Object> handle(ProblemType problemType, BindingResult bindingResult, Exception ex, WebRequest request) {
        return handle(problemType, bindingResult, ex, ex.getMessage(), request);
    }

    private ResponseEntity<Object> handle(ProblemType problemType, Exception ex, String message, WebRequest request) {
        return handle(problemType, null, ex, message, request);
    }

    private ResponseEntity<Object> handle(ProblemType problemType, BindingResult bindingResult, Exception ex, String message, WebRequest request) {
        List<Problem.Field> fields = null;

        if (bindingResult != null && bindingResult.hasFieldErrors()) {
            fields = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> {
                        String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                        return Problem.Field.builder()
                                .name(fieldError.getField())
                                .userMessage(userMessage)
                                .build();
                    })
                    .collect(toList());
        }

        Problem problem = new Problem.Builder()
                .withProblemType(problemType)
                .withDetail(message)
                .withFields(fields)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.resolve(problem.getStatus()), request);
    }

}
