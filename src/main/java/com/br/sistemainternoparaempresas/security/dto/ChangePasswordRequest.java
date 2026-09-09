package com.br.sistemainternoparaempresas.security.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dados necessários para alteração da senha do usuário autenticado.
 *
 * US-007: Alterar senha
 */
public class ChangePasswordRequest {

    @NotBlank(message = "Senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 8, message = "Nova senha deve possuir ao menos 8 caracteres")
    private String novaSenha;

    @NotBlank(message = "Confirmação da nova senha é obrigatória")
    private String confirmacaoNovaSenha;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String senhaAtual, String novaSenha, String confirmacaoNovaSenha) {
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
        this.confirmacaoNovaSenha = confirmacaoNovaSenha;
    }

    @AssertTrue(message = "Nova senha e confirmação devem ser iguais")
    public boolean isConfirmacaoDaNovaSenhaValida() {
        return novaSenha != null && novaSenha.equals(confirmacaoNovaSenha);
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmacaoNovaSenha() {
        return confirmacaoNovaSenha;
    }

    public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
        this.confirmacaoNovaSenha = confirmacaoNovaSenha;
    }
}
