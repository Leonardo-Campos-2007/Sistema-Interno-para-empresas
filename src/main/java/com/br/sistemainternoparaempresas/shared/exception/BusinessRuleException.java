package com.br.sistemainternoparaempresas.shared.exception;

/**
 * Exceção para violação de regras de negócio.
 *
 * Deve ser lançada quando uma operação é solicitada mas as condições
 * de domínio não a permitem.
 *
 * Exemplo: Confirmar uma venda sem itens.
 */
public class BusinessRuleException extends RuntimeException {

    private final String code;

    public BusinessRuleException(String message) {
        super(message);
        this.code = "BUSINESS_RULE_VIOLATION";
    }

    public BusinessRuleException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessRuleException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}