package com.br.sistemainternoparaempresas.security.service;

import com.br.sistemainternoparaempresas.security.principal.UsuarioPrincipal;
import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import com.br.sistemainternoparaempresas.usuario.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Carrega o usuário pelo ID (subject do JWT) ou e-mail.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!usuario.podeAutenticar()) {
            throw new UsernameNotFoundException("Usuário não pode autenticar");
        }

        return new UsuarioPrincipal(usuario);
    }

    public UserDetails loadUserByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!usuario.podeAutenticar()) {
            throw new UsernameNotFoundException("Usuário não pode autenticar");
        }

        return new UsuarioPrincipal(usuario);
    }
}
