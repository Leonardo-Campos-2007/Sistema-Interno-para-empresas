package com.br.sistemainternoparaempresas.empresa.controller;

import com.br.sistemainternoparaempresas.empresa.dto.EmpresaCreateRequest;
import com.br.sistemainternoparaempresas.empresa.dto.EmpresaResponse;
import com.br.sistemainternoparaempresas.empresa.dto.EmpresaUpdateRequest;
import com.br.sistemainternoparaempresas.empresa.entity.Empresa;
import com.br.sistemainternoparaempresas.empresa.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;

/**
 * Controller para endpoints de Empresa.
 *
 * Responsável por:
 * - Receber requisições HTTP
 * - Validar entrada
 * - Encaminhar para serviços
 * - Transformar resultados em respostas
 *
 * Endpoints:
 * - POST /empresa (US-001)
 * - GET /empresa (US-002)
 * - PUT /empresa (US-003)
 * - PATCH /empresa/status (US-004)
 *
 * TEC-006: Padrão arquitetural
 */
@RestController
@RequestMapping("/empresa")
@Validated
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * POST /empresa
     *
     * Cria nova empresa.
     *
     * US-001: Cadastrar empresa
     * RF-001: O sistema deve permitir o cadastro da empresa
     *
     * @param request dados da empresa
     * @return empresa criada com HTTP 201
     */
    @PostMapping
    public ResponseEntity<EmpresaResponse> criar(@Valid @RequestBody EmpresaCreateRequest request) {
        Empresa empresa = empresaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EmpresaResponse(empresa));
    }

    /**
     * GET /empresa
     *
     * Consulta os dados da empresa.
     *
     * US-002: Visualizar empresa
     * RF-002: O sistema deve permitir consultar os dados da empresa
     *
     * @return dados da empresa com HTTP 200
     */
    @GetMapping
    public ResponseEntity<EmpresaResponse> consultar() {
        // Nota: Na Phase 1, existe uma única empresa por instalação
        // ID padrão seria definido por configuração ou primeira instalação
        // Aqui usamos um placeholder que será ajustado após bootstrap
        String empresaId = "default"; // TODO: Implementar bootstrap

        Empresa empresa = empresaService.obterPorId(empresaId);
        return ResponseEntity.ok(new EmpresaResponse(empresa));
    }

    /**
     * PUT /empresa
     *
     * Atualiza os dados da empresa.
     *
     * US-003: Editar empresa
     * RF-003: O sistema deve permitir atualizar os dados da empresa
     *
     * @param request dados atualizados
     * @return empresa atualizada com HTTP 200
     */
    @PutMapping
    public ResponseEntity<EmpresaResponse> atualizar(@Valid @RequestBody EmpresaUpdateRequest request) {
        String empresaId = "default"; // TODO: Implementar bootstrap

        Empresa empresa = empresaService.atualizar(empresaId, request);
        return ResponseEntity.ok(new EmpresaResponse(empresa));
    }

    /**
     * PATCH /empresa/status
     *
     * Ativa ou inativa a empresa.
     *
     * US-004: Ativar ou inativar empresa
     * RF-004: O sistema deve permitir ativar ou inativar a empresa
     *
     * @param ativa true para ativar, false para inativar
     * @return empresa com status alterado e HTTP 200
     */
    @PatchMapping("/status")
    public ResponseEntity<EmpresaResponse> alterarStatus(@RequestParam boolean ativa) {
        String empresaId = "default"; // TODO: Implementar bootstrap

        Empresa empresa = empresaService.alterarStatus(empresaId, ativa);
        return ResponseEntity.ok(new EmpresaResponse(empresa));
    }
}