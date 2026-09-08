package com.br.sistemainternoparaempresas.empresa.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Entity: Empresa
 *
 * Representa a empresa administrada pelo sistema.
 *
 * RN-008: Cadastro da empresa
 * RN-009: Unicidade do CNPJ
 * RN-010: Empresa ativa
 * RN-011: Empresa inativa
 */
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 255)
    private String razaoSocial;

    @Column(nullable = false, length = 255)
    private String nomeFantasia;

    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmpresa status = StatusEmpresa.ATIVA;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    // Construtores
    public Empresa() {
        this.id = UUID.randomUUID().toString();
    }

    public Empresa(String razaoSocial, String nomeFantasia, String cnpj, String email) {
        this();
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.email = email;
    }

    // Callbacks
    @PrePersist
    protected void onPersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Validações de domínio
    public boolean isAtiva() {
        return this.status == StatusEmpresa.ATIVA;
    }

    public void inativar() {
        this.status = StatusEmpresa.INATIVA;
    }

    public void ativar() {
        this.status = StatusEmpresa.ATIVA;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public StatusEmpresa getStatus() {
        return status;
    }

    public void setStatus(StatusEmpresa status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Enum para status da empresa
     * RN-010, RN-011
     */
    public enum StatusEmpresa {
        ATIVA,
        INATIVA
    }
}