package com.br.sistemainternoparaempresas.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para atualizar dados da empresa.
 *
 * US-003: Editar empresa
 *
 * Nota: CNPJ não pode ser alterado após criação.
 */
public class EmpresaUpdateRequest {

    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @Pattern(
            regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$|^$",
            message = "Telefone deve estar no formato correto ou vazio"
    )
    private String telefone;

    // Construtores
    public EmpresaUpdateRequest() {
    }

    public EmpresaUpdateRequest(String razaoSocial, String nomeFantasia, String email) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
    }

    // Getters and Setters
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}