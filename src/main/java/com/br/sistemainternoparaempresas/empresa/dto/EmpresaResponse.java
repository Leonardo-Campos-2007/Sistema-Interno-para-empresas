package com.br.sistemainternoparaempresas.empresa.dto;

import com.br.sistemainternoparaempresas.empresa.entity.Empresa;

import java.time.LocalDateTime;



/**
 * DTO para resposta da API com dados da empresa.
 *
 * US-002: Visualizar empresa
 *
 * Nunca retorna Entity diretamente.
 * CT-006: DTO
 */
public class EmpresaResponse {

    private String id;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String email;
    private String telefone;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Construtores
    public EmpresaResponse() {
    }

    /**
     * Converte Entity em DTO.
     *
     * Mapper manual para máximo controle.
     */
    public EmpresaResponse(Empresa empresa) {
        this.id = empresa.getId();
        this.razaoSocial = empresa.getRazaoSocial();
        this.nomeFantasia = empresa.getNomeFantasia();
        this.cnpj = empresa.getCnpj();
        this.email = empresa.getEmail();
        this.telefone = empresa.getTelefone();
        this.status = empresa.getStatus().toString();
        this.dataCriacao = empresa.getDataCriacao();
        this.dataAtualizacao = empresa.getDataAtualizacao();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}