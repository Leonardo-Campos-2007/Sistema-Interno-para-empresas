package com.br.sistemainternoparaempresas.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.sistemainternoparaempresas.security.dto.LoginRequest;
import com.br.sistemainternoparaempresas.security.dto.LoginResponse;
import com.br.sistemainternoparaempresas.security.jwt.JwtService;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import com.br.sistemainternoparaempresas.usuario.repository.UsuarioRepository;

/**
 * Testes unitários para o fluxo de autenticação.
 *
 * US-005: Login
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    private static final String USUARIO_ID = "usuario-id-123";
    private static final String EMAIL = "usuario@empresa.com";
    private static final String SENHA = "Senha@123";
    private static final String SENHA_HASH = "$2a$10$senhaHash";
    private static final String TOKEN = "jwt-gerado";

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usuarioRepository, passwordEncoder, jwtService);
    }

    @Test
    @DisplayName("Deve autenticar usuário ativo, registrar login e gerar token")
    void deveAutenticarUsuarioComCredenciaisValidas() {
        Usuario usuario = usuarioAtivo();
        LoginRequest request = new LoginRequest(EMAIL, SENHA);

        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(SENHA, SENHA_HASH)).thenReturn(true);
        when(jwtService.generateToken(USUARIO_ID, EMAIL, 0)).thenReturn(TOKEN);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(TOKEN, response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(USUARIO_ID, response.getUserId());
        assertEquals(EMAIL, response.getEmail());
        assertNotNull(usuario.getUltimoLogin());
        verify(usuarioRepository).save(usuario);
        verify(jwtService).generateToken(USUARIO_ID, EMAIL, 0);
    }

    @Test
    @DisplayName("Deve rejeitar login quando usuário não existe")
    void deveRejeitarLoginComEmailInexistente() {
        LoginRequest request = new LoginRequest(EMAIL, SENHA);
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> authService.login(request)
        );

        assertEquals("CREDENCIAIS_INVALIDAS", exception.getCode());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(jwtService, never()).generateToken(any(), any(), any(Long.class));
    }

    @Test
    @DisplayName("Deve rejeitar login quando senha é inválida")
    void deveRejeitarLoginComSenhaInvalida() {
        Usuario usuario = usuarioAtivo();
        LoginRequest request = new LoginRequest(EMAIL, SENHA);
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(SENHA, SENHA_HASH)).thenReturn(false);

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> authService.login(request)
        );

        assertEquals("CREDENCIAIS_INVALIDAS", exception.getCode());
        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(jwtService, never()).generateToken(any(), any(), any(Long.class));
    }

    @Test
    @DisplayName("Deve rejeitar login de usuário inativo")
    void deveRejeitarLoginDeUsuarioInativo() {
        Usuario usuario = usuarioAtivo();
        usuario.inativar();
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> authService.login(new LoginRequest(EMAIL, SENHA))
        );

        assertEquals("USUARIO_INATIVO", exception.getCode());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve rejeitar login de usuário bloqueado")
    void deveRejeitarLoginDeUsuarioBloqueado() {
        Usuario usuario = usuarioAtivo();
        usuario.bloquear();
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> authService.login(new LoginRequest(EMAIL, SENHA))
        );

        assertEquals("USUARIO_BLOQUEADO", exception.getCode());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    private Usuario usuarioAtivo() {
        Usuario usuario = new Usuario(EMAIL, SENHA_HASH);
        usuario.setId(USUARIO_ID);
        usuario.setUltimoLogin(LocalDateTime.now().minusDays(1));
        return usuario;
    }
}
