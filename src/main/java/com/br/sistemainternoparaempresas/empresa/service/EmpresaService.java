package com.br.sistemainternoparaempresas.empresa.service;

import java.util.Optional;

import com.br.sistemainternoparaempresas.empresa.dto.EmpresaCreateRequest;
import com.br.sistemainternoparaempresas.empresa.dto.EmpresaUpdateRequest;
import com.br.sistemainternoparaempresas.empresa.entity.Empresa;
import com.br.sistemainternoparaempresas.empresa.repository.EmpresaRepository;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
import com.br.sistemainternoparaempresas.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




/**
 * Service para Empresa.
 *
 * Responsável por:
 * - Orquestração de operações
 * - Validação de regras de negócio
 * - Transações
 * - Chamadas ao Repository
 *
 * TEC-006: Padrão arquitetural
 */
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /**
     * Cria nova empresa.
     *
     * US-001: Cadastrar empresa
     * RN-008: Cadastro da empresa
     * RN-009: Unicidade do CNPJ
     */
    @Transactional
    public Empresa criar(EmpresaCreateRequest request) {

        // Valida CNPJ único
        Optional<Empresa> empresaExistente = empresaRepository.findByCnpj(request.getCnpj());
        if (empresaExistente.isPresent()) {
            throw new BusinessRuleException(
                    "CNPJ_JA_CADASTRADO",
                    "Já existe empresa cadastrada com este CNPJ"
            );
        }

        // Cria entity a partir do request
        Empresa empresa = new Empresa(
                request.getRazaoSocial(),
                request.getNomeFantasia(),
                request.getCnpj(),
                request.getEmail()
        );
        empresa.setTelefone(request.getTelefone());

        // Persiste
        return empresaRepository.save(empresa);
    }

    /**
     * Busca empresa por ID.
     *
     * US-002: Visualizar empresa
     */
    @Transactional(readOnly = true)
    public Empresa obterPorId(String id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", id));
    }

    /**
     * Atualiza dados da empresa.
     *
     * US-003: Editar empresa
     * RN-012: Dados cadastrais
     *
     * Nota: CNPJ não pode ser alterado
     */
    @Transactional
    public Empresa atualizar(String id, EmpresaUpdateRequest request) {

        Empresa empresa = obterPorId(id);

        // Atualiza somente os campos permitidos
        empresa.setRazaoSocial(request.getRazaoSocial());
        empresa.setNomeFantasia(request.getNomeFantasia());
        empresa.setEmail(request.getEmail());
        empresa.setTelefone(request.getTelefone());

        return empresaRepository.save(empresa);
    }

    /**
     * Ativa ou inativa a empresa.
     *
     * US-004: Ativar ou inativar empresa
     * RN-010: Empresa ativa
     * RN-011: Empresa inativa
     */
    @Transactional
    public Empresa alterarStatus(String id, boolean ativa) {

        Empresa empresa = obterPorId(id);

        if (ativa) {
            empresa.ativar();
        } else {
            empresa.inativar();
        }

        return empresaRepository.save(empresa);
    }
}