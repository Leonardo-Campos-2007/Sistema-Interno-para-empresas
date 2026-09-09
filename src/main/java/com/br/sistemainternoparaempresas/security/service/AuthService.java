package com.br.sistemainternoparaempresas.security.service;

import com.br.sistemainternoparaempresas.security.dto.LoginRequest;
import com.br.sistemainternoparaempresas.security.dto.LoginResponse;
import com.br.sistemainternoparaempresas.security.dto.ChangePasswordRequest;
import com.br.sistemainternoparaempresas.security.jwt.JwtService;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
import com.br.sistemainternoparaempresas.shared.exception.ResourceNotFoundException;
import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import com.br.sistemainternoparaempresas.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de autenticação.
 * US-005: Login
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessRuleException(
                        "CREDENCIAIS_INVALIDAS",
                        "E-mail ou senha inválidos"
                ));

        if (!usuario.podeAutenticar()) {
            boolean usuarioBloqueado = usuario.getStatus() == Usuario.StatusUsuario.BLOQUEADO;
            throw new BusinessRuleException(
                    usuarioBloqueado ? "USUARIO_BLOQUEADO" : "USUARIO_INATIVO",
                    usuarioBloqueado ? "Usuário está bloqueado" : "Usuário não pode autenticar"
            );
        }

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenhaHash())) {
            throw new BusinessRuleException(
                    "CREDENCIAIS_INVALIDAS",
                    "E-mail ou senha inválidos"
            );
        }

        usuario.registrarLogin();
        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario.getId(), usuario.getEmail(), usuario.getTokenVersion());

        return new LoginResponse(token, usuario.getId(), usuario.getEmail());
    }

    /**
     * Altera a senha do usuário autenticado depois de validar a senha atual.
     *
     * US-007: Alterar senha
     * RN-028: Senha nunca é persistida em texto puro.
     */
    @Transactional
    public void alterarSenha(String usuarioId, ChangePasswordRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", usuarioId));

        if (!usuario.podeAutenticar()) {
            throw new BusinessRuleException("USUARIO_NAO_PODE_AUTENTICAR", "Usuário não pode alterar a senha");
        }

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenhaHash())) {
            throw new BusinessRuleException("SENHA_ATUAL_INVALIDA", "Senha atual inválida");
        }

        usuario.alterarSenhaHash(passwordEncoder.encode(request.getNovaSenha()));
        usuario.invalidarTokensAtuais();
        usuarioRepository.save(usuario);
    }

    /**
     * Invalida todos os tokens emitidos para o usuário autenticado.
     *
     * US-006: Encerrar sessão
     */
    @Transactional
    public void logout(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", usuarioId));

        usuario.invalidarTokensAtuais();
        usuarioRepository.save(usuario);
    }
}
