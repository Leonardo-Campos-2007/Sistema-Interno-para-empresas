package com.br.sistemainternoparaempresas.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import com.br.sistemainternoparaempresas.security.dto.ChangePasswordRequest;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import com.br.sistemainternoparaempresas.usuario.repository.UsuarioRepository;

/** Testes unitários da alteração de senha (US-007). */
@ExtendWith(MockitoExtension.class)
@DisplayName("Alteração de senha Tests")
class ChangePasswordServiceTest {

    private static final String USUARIO_ID = "usuario-id-123";
    private static final String SENHA_ATUAL = "Senha@123";
    private static final String NOVA_SENHA = "NovaSenha@123";
    private static final String HASH_ATUAL = "$2a$10$hashAtual";
    private static final String NOVO_HASH = "$2a$10$novoHash";

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
    @DisplayName("Deve alterar senha persistindo somente o novo hash")
    void deveAlterarSenhaComSucesso() {
        Usuario usuario = usuarioAtivo();
        ChangePasswordRequest request = requestValido();
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(SENHA_ATUAL, HASH_ATUAL)).thenReturn(true);
        when(passwordEncoder.encode(NOVA_SENHA)).thenReturn(NOVO_HASH);

        authService.alterarSenha(USUARIO_ID, request);

        assertEquals(NOVO_HASH, usuario.getSenhaHash());
        assertEquals(1, usuario.getTokenVersion());
        verify(passwordEncoder).encode(NOVA_SENHA);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Deve rejeitar alteração quando senha atual é inválida")
    void deveRejeitarQuandoSenhaAtualForInvalida() {
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuarioAtivo()));
        when(passwordEncoder.matches(SENHA_ATUAL, HASH_ATUAL)).thenReturn(false);

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> authService.alterarSenha(USUARIO_ID, requestValido())
        );

        assertEquals("SENHA_ATUAL_INVALIDA", exception.getCode());
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve rejeitar alteração de senha para usuário inativo")
    void deveRejeitarUsuarioInativo() {
        Usuario usuario = usuarioAtivo();
        usuario.inativar();
        when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> authService.alterarSenha(USUARIO_ID, requestValido())
        );

        assertEquals("USUARIO_NAO_PODE_AUTENTICAR", exception.getCode());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    private ChangePasswordRequest requestValido() {
        return new ChangePasswordRequest(SENHA_ATUAL, NOVA_SENHA, NOVA_SENHA);
    }

    private Usuario usuarioAtivo() {
        Usuario usuario = new Usuario("usuario@empresa.com", HASH_ATUAL);
        usuario.setId(USUARIO_ID);
        return usuario;
    }
}
