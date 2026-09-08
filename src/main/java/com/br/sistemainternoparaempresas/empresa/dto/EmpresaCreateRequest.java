package com.br.sistemainternoparaempresas.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para criar empresa.
 *
 * US-001: Cadastrar empresa
 * CT-004: Validação
 * CT-006: DTO
 */
public class EmpresaCreateRequest {

    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(
            regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$|^\\d{14}$",
            message = "CNPJ deve estar no formato correto (XX.XXX.XXX/XXXX-XX ou XXXXXXXXXXXXXX)"
    )
    private String cnpj;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @Pattern(
            regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$|^$",
            message = "Telefone deve estar no formato correto ou vazio"
    )
    private String telefone;

    // Construtores
    public EmpresaCreateRequest() {
    }

    public EmpresaCreateRequest(String razaoSocial, String nomeFantasia, String cnpj, String email) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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