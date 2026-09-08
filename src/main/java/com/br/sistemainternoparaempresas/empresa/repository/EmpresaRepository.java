package com.br.sistemainternoparaempresas.empresa.repository;

import java.util.Optional;

import com.br.sistemainternoparaempresas.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Repository para Empresa.
 *
 * Abstração de persistência usando Spring Data JPA.
 *
 * TEC-006: Padrão arquitetural
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {

    /**
     * Busca empresa por CNPJ.
     *
     * RN-009: Unicidade do CNPJ
     */
    Optional<Empresa> findByCnpj(String cnpj);
}