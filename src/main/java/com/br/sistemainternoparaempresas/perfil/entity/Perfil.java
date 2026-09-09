package com.br.sistemainternoparaempresas.perfil.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.br.sistemainternoparaempresas.permissao.entity.Permissao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Conjunto de permissões que pode ser associado a usuários na S04.
 *
 * RN-033 a RN-035.
 */
@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPerfil status = StatusPerfil.ATIVO;

    @ManyToMany
    @JoinTable(
            name = "perfil_permissao",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<Permissao> permissoes = new HashSet<>();

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Perfil() {
        this.id = UUID.randomUUID().toString();
    }

    public Perfil(String nome, String descricao) {
        this();
        this.nome = nome;
        this.descricao = descricao;
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
        return status == StatusPerfil.ATIVO;
    }

    public void ativar() {
        this.status = StatusPerfil.ATIVO;
    }

    public void inativar() {
        this.status = StatusPerfil.INATIVO;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusPerfil getStatus() {
        return status;
    }

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }
}
