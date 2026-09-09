package com.br.sistemainternoparaempresas.perfil.repository;

import com.br.sistemainternoparaempresas.perfil.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Persistência de perfis de acesso. */
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, String> {
}
