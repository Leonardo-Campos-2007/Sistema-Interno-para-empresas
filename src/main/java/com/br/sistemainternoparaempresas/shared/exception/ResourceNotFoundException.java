package com.br.sistemainternoparaempresas.shared.exception;

/**
 * Exceção para recurso não encontrado.
 *
 * Deve ser lançada quando uma operação tenta acessar um recurso
 * que não existe ou não pode ser localizado.
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(String.format("%s com ID %s não encontrado", resourceType, resourceId));
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceType = null;
        this.resourceId = null;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }
}