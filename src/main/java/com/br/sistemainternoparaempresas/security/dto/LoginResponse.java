package com.br.sistemainternoparaempresas.security.dto;

/**
 * DTO de resposta do login.
 * US-005
 */
public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String userId;
    private String email;

    public LoginResponse() {
    }

    public LoginResponse(String accessToken, String userId, String email) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
