package com.br.sistemainternoparaempresas.shared.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO padrão para respostas de erro.
 *
 * Nunca deve expor:
 * - stack trace
 * - SQL
 * - credenciais
 * - informações internas desnecessárias
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private ZonedDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private String path;
    private List<FieldError> fields;

    public ErrorResponse() {
        this.timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public ErrorResponse(int status, String code, String message, String path) {
        this();
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
    }

    public static ErrorResponse badRequest(String message, String path) {
        return new ErrorResponse(400, "VALIDATION_ERROR", message, path);
    }

    public static ErrorResponse unauthorized(String message, String path) {
        return new ErrorResponse(401, "UNAUTHORIZED", message, path);
    }

    public static ErrorResponse forbidden(String message, String path) {
        return new ErrorResponse(403, "FORBIDDEN", message, path);
    }

    public static ErrorResponse notFound(String message, String path) {
        return new ErrorResponse(404, "NOT_FOUND", message, path);
    }

    public static ErrorResponse conflict(String message, String path) {
        return new ErrorResponse(409, "CONFLICT", message, path);
    }

    public static ErrorResponse businessRuleViolation(String message, String path) {
        return new ErrorResponse(422, "BUSINESS_RULE_VIOLATION", message, path);
    }

    public static ErrorResponse internalServerError(String message, String path) {
        return new ErrorResponse(500, "INTERNAL_SERVER_ERROR", message, path);
    }

    // Getters and Setters
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FieldError> getFields() {
        return fields;
    }

    public void setFields(List<FieldError> fields) {
        this.fields = fields;
    }

    public void addFieldError(String field, String message) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(new FieldError(field, message));
    }

    /**
     * Erro de campo para validação de entrada.
     */
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}