package com.br.sistemainternoparaempresas.security.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import com.br.sistemainternoparaempresas.security.jwt.JwtService;
import com.br.sistemainternoparaempresas.security.principal.UsuarioPrincipal;
import com.br.sistemainternoparaempresas.security.service.CustomUserDetailsService;
import com.br.sistemainternoparaempresas.usuario.entity.Usuario;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;

/**
 * Testes do processamento de JWT em requisições protegidas.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("JwtAuthenticationFilter Tests")
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @AfterEach
    void limparContextoDeSeguranca() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve continuar requisição sem token sem autenticar usuário")
    void deveContinuarRequisicaoSemToken() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);

        filter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve autenticar usuário quando JWT é válido")
    void deveAutenticarComTokenValido() throws Exception {
        String token = "jwt-valido";
        String usuarioId = "usuario-id-123";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        when(jwtService.extractUserId(token)).thenReturn(usuarioId);
        Usuario usuario = new Usuario("usuario@empresa.com", "hash");
        usuario.setId(usuarioId);
        UsuarioPrincipal userDetails = new UsuarioPrincipal(usuario);
        when(userDetailsService.loadUserByUsername(usuarioId)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, usuarioId, 0)).thenReturn(true);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        filter.doFilter(request, new MockHttpServletResponse(), filterChain);

        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve responder 401 para JWT inválido")
    void deveRejeitarTokenInvalido() throws Exception {
        String token = "jwt-invalido";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtService.extractUserId(token)).thenThrow(new MalformedJwtException("JWT inválido"));

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        filter.doFilter(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
