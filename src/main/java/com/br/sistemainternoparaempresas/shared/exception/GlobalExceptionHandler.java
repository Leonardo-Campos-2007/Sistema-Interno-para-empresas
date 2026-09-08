package com.br.sistemainternoparaempresas.shared.exception;

import java.util.stream.Collectors;

import com.br.sistemainternoparaempresas.shared.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Handler global de exceções.
 *
 * TEC-007: Tratamento global de exceções
 *
 * Converte exceções internas em respostas HTTP padronizadas
 * sem expor informações sensíveis ou internas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata ResourceNotFoundException (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request) {

        ErrorResponse error = ErrorResponse.notFound(
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata BusinessRuleException (422)
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(
            BusinessRuleException ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse(
                422,
                ex.getCode(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    /**
     * Trata validação de entrada (400)
     *
     * CT-004: Validação
     * CT-006: DTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        ErrorResponse error = ErrorResponse.badRequest(
                "Dados inválidos",
                request.getDescription(false).replace("uri=", "")
        );

        // Adiciona detalhes de cada campo inválido
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->
                        error.addFieldError(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceções genéricas não previstas (500)
     *
     * Nunca expõe stack trace ou detalhes internos.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            WebRequest request) {

        ErrorResponse error = ErrorResponse.internalServerError(
                "Erro interno do servidor",
                request.getDescription(false).replace("uri=", "")
        );

        // Log interno (não exposto ao cliente)
        // logger.error("Erro não tratado", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}