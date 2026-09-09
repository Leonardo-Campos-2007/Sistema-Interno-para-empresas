package com.br.sistemainternoparaempresas.security.controller;

import com.br.sistemainternoparaempresas.security.dto.LoginRequest;
import com.br.sistemainternoparaempresas.security.dto.LoginResponse;
import com.br.sistemainternoparaempresas.security.dto.ChangePasswordRequest;
import com.br.sistemainternoparaempresas.security.principal.UsuarioPrincipal;
import com.br.sistemainternoparaempresas.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de autenticação.
 *
 * POST /auth/login
 * US-005: Login
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /auth/change-password
     * US-007: Alterar senha
     */
    @PostMapping("/change-password")
    public ResponseEntity<Void> alterarSenha(
            @AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.alterarSenha(usuarioPrincipal.getId(), request);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /auth/logout
     * US-006: Encerrar sessão
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal) {
        authService.logout(usuarioPrincipal.getId());
        return ResponseEntity.noContent().build();
    }
}
