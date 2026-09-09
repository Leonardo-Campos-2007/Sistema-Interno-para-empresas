package com.br.sistemainternoparaempresas.security.service;

import com.br.sistemainternoparaempresas.security.dto.LoginRequest;
import com.br.sistemainternoparaempresas.security.dto.LoginResponse;
import com.br.sistemainternoparaempresas.security.jwt.JwtService;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
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
            throw new BusinessRuleException(
                    "USUARIO_INATIVO",
                    "Usuário não pode autenticar"
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

        String token = jwtService.generateToken(usuario.getId(), usuario.getEmail());

        return new LoginResponse(token, usuario.getId(), usuario.getEmail());
    }
}
