package com.br.sistemainternoparaempresas.permissao.entity;

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
import jakarta.persistence.UniqueConstraint;

/**
 * Ação autorizada em um módulo do sistema.
 *
 * RN-037 e RN-038.
 */
@Entity
@Table(
        name = "permissao",
        uniqueConstraints = @UniqueConstraint(name = "uk_permissao_modulo_acao", columnNames = {"modulo", "acao"})
)
public class Permissao {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ModuloPermissao modulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private AcaoPermissao acao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Permissao() {
        this.id = UUID.randomUUID().toString();
    }

    public Permissao(String nome, String descricao, ModuloPermissao modulo, AcaoPermissao acao) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.modulo = modulo;
        this.acao = acao;
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

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public ModuloPermissao getModulo() {
        return modulo;
    }

    public AcaoPermissao getAcao() {
        return acao;
    }
}
