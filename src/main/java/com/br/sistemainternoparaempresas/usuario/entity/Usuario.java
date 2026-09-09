package com.br.sistemainternoparaempresas.usuario.entity;

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
 * Entity: Usuario
 *
 * Representa a conta de acesso ao sistema.
 * Relacionamento 1:0..1 com Funcionario (funcionario_id será obrigatório após S04).
 *
 * US-005: Login
 * RN: Status ATIVO/BLOQUEADO/INATIVO controla autenticação
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "funcionario_id", length = 36)
    private String funcionarioId;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(name = "token_version", nullable = false)
    private long tokenVersion = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Usuario() {
        this.id = UUID.randomUUID().toString();
    }

    public Usuario(String email, String senhaHash) {
        this();
        this.email = email;
        this.senhaHash = senhaHash;
    }

    @PrePersist
    protected void onPersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public boolean isAtivo() {
        return this.status == StatusUsuario.ATIVO;
    }

    public boolean podeAutenticar() {
        return this.status == StatusUsuario.ATIVO;
    }

    public void registrarLogin() {
        this.ultimoLogin = LocalDateTime.now();
    }

    public void alterarSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public void invalidarTokensAtuais() {
        this.tokenVersion++;
    }

    public void bloquear() {
        this.status = StatusUsuario.BLOQUEADO;
    }

    public void desbloquear() {
        this.status = StatusUsuario.ATIVO;
    }

    public void inativar() {
        this.status = StatusUsuario.INATIVO;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(String funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public long getTokenVersion() {
        return tokenVersion;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public enum StatusUsuario {
        ATIVO,
        BLOQUEADO,
        INATIVO
    }
}
