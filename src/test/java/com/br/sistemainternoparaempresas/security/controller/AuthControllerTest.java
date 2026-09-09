package com.br.sistemainternoparaempresas.security.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.br.sistemainternoparaempresas.security.dto.LoginResponse;
import com.br.sistemainternoparaempresas.security.service.AuthService;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
import com.br.sistemainternoparaempresas.shared.exception.GlobalExceptionHandler;

/**
 * Testes HTTP do endpoint de login.
 *
 * US-005: Login
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(authService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve retornar token para credenciais válidas")
    void deveRetornarTokenParaLoginValido() throws Exception {
        when(authService.login(any())).thenReturn(
                new LoginResponse("jwt-gerado", "usuario-id-123", "usuario@empresa.com")
        );

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"usuario@empresa.com\",\"senha\":\"Senha@123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("jwt-gerado"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.userId").value("usuario-id-123"))
                .andExpect(jsonPath("$.email").value("usuario@empresa.com"));

        verify(authService).login(any());
    }

    @Test
    @DisplayName("Deve rejeitar payload de login inválido")
    void deveRejeitarPayloadInvalido() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"email-invalido\",\"senha\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("Deve retornar erro padronizado para credenciais inválidas")
    void deveRetornarErroPadronizadoParaCredenciaisInvalidas() throws Exception {
        when(authService.login(any())).thenThrow(new BusinessRuleException(
                "CREDENCIAIS_INVALIDAS", "E-mail ou senha inválidos"
        ));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"usuario@empresa.com\",\"senha\":\"Senha@123\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value("CREDENCIAIS_INVALIDAS"))
                .andExpect(jsonPath("$.message").value("E-mail ou senha inválidos"));
    }
}
