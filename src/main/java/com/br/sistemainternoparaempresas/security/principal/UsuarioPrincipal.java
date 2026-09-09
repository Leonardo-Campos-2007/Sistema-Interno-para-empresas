package com.br.sistemainternoparaempresas.security.principal;

import java.util.Collection;
import java.util.Collections;

import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Representação do usuário autenticado no contexto de segurança.
 */
public class UsuarioPrincipal implements UserDetails {

    private final String id;
    private final String email;
    private final String senhaHash;
    private final boolean ativo;
    private final long tokenVersion;

    public UsuarioPrincipal(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.senhaHash = usuario.getSenhaHash();
        this.ativo = usuario.isAtivo();
        this.tokenVersion = usuario.getTokenVersion();
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Perfis/permissões serão adicionados na S03/S04
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return id; // usamos ID como username no token
    }

    public String getEmail() {
        return email;
    }

    public long getTokenVersion() {
        return tokenVersion;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
