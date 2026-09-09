package com.br.sistemainternoparaempresas.usuario.repository;

import java.util.Optional;

import com.br.sistemainternoparaempresas.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository para Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
