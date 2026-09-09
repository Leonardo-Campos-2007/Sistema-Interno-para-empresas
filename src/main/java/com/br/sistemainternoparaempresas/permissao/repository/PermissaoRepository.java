package com.br.sistemainternoparaempresas.permissao.repository;

import java.util.Optional;

import com.br.sistemainternoparaempresas.permissao.entity.AcaoPermissao;
import com.br.sistemainternoparaempresas.permissao.entity.ModuloPermissao;
import com.br.sistemainternoparaempresas.permissao.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Persistência de permissões funcionais. */
@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, String> {

    Optional<Permissao> findByModuloAndAcao(ModuloPermissao modulo, AcaoPermissao acao);
}
