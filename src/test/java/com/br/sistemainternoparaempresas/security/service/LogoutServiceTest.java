package com.br.sistemainternoparaempresas.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import com.br.sistemainternoparaempresas.usuario.repository.UsuarioRepository;

/** Testes unitários do logout com invalidação de token (US-006). */
@ExtendWith(MockitoExtension.class)
@DisplayName("Logout Tests")
class LogoutServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(usuarioRepository, passwordEncoder, null);
    }

    @Test
    @DisplayName("Deve invalidar tokens existentes no logout")
    void deveInvalidarTokensNoLogout() {
        Usuario usuario = new Usuario("usuario@empresa.com", "hash");
        usuario.setId("usuario-id-123");
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        authService.logout(usuario.getId());

        assertEquals(1, usuario.getTokenVersion());
        verify(usuarioRepository).save(usuario);
    }
}
