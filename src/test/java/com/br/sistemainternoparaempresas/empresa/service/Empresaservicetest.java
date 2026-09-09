package com.br.sistemainternoparaempresas.empresa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sistemainternoparaempresas.empresa.dto.EmpresaCreateRequest;
import com.br.sistemainternoparaempresas.empresa.dto.EmpresaUpdateRequest;
import com.br.sistemainternoparaempresas.empresa.entity.Empresa;
import com.br.sistemainternoparaempresas.empresa.repository.EmpresaRepository;
import com.br.sistemainternoparaempresas.shared.exception.BusinessRuleException;
import com.br.sistemainternoparaempresas.shared.exception.ResourceNotFoundException;

/**
 * Testes unitários para EmpresaService.
 *
 * US-001: Cadastrar empresa
 * US-002: Visualizar empresa
 * US-003: Editar empresa
 * US-004: Ativar ou inativar empresa
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EmpresaService Tests")
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        empresaService = new EmpresaService(empresaRepository);
    }

    @Test
    @DisplayName("Deve criar empresa com dados válidos")
    void testCriarEmpresaComSucesso() {
        // Arrange
        EmpresaCreateRequest request = new EmpresaCreateRequest(
                "Empresa Teste Ltda",
                "Empresa Teste",
                "12345678000190",
                "contato@empresa.com"
        );

        when(empresaRepository.findByCnpj(request.getCnpj())).thenReturn(Optional.empty());

        Empresa empresaEsperada = new Empresa(
                request.getRazaoSocial(),
                request.getNomeFantasia(),
                request.getCnpj(),
                request.getEmail()
        );

        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresaEsperada);

        // Act
        Empresa empresa = empresaService.criar(request);

        // Assert
        assertNotNull(empresa);
        assertEquals("Empresa Teste Ltda", empresa.getRazaoSocial());
        assertEquals("12345678000190", empresa.getCnpj());
        assertTrue(empresa.isAtiva());
    }

    @Test
    @DisplayName("Deve rejeitar criação de empresa com CNPJ duplicado")
    void testCriarEmpresaComCnpjDuplicado() {
        // Arrange
        EmpresaCreateRequest request = new EmpresaCreateRequest(
                "Empresa Nova Ltda",
                "Empresa Nova",
                "12345678000190",
                "novo@empresa.com"
        );

        Empresa empresaExistente = new Empresa(
                "Empresa Antiga Ltda",
                "Empresa Antiga",
                "12345678000190",
                "antigo@empresa.com"
        );

        when(empresaRepository.findByCnpj(request.getCnpj()))
                .thenReturn(Optional.of(empresaExistente));

        // Act & Assert
        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> empresaService.criar(request)
        );

        assertEquals("CNPJ_JA_CADASTRADO", exception.getCode());
        assertTrue(exception.getMessage().contains("CNPJ"));
    }

    @Test
    @DisplayName("Deve consultar empresa por ID")
    void testObterEmpresaPorId() {
        // Arrange
        String empresaId = "uuid-test-123";
        Empresa empresaEsperada = new Empresa(
                "Empresa Teste Ltda",
                "Empresa Teste",
                "12345678000190",
                "contato@empresa.com"
        );
        empresaEsperada.setId(empresaId);

        when(empresaRepository.findById(empresaId))
                .thenReturn(Optional.of(empresaEsperada));

        // Act
        Empresa empresa = empresaService.obterPorId(empresaId);

        // Assert
        assertNotNull(empresa);
        assertEquals(empresaId, empresa.getId());
        assertEquals("Empresa Teste Ltda", empresa.getRazaoSocial());
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar empresa inexistente")
    void testObterEmpresaInexistente() {
        // Arrange
        String empresaId = "uuid-inexistente";

        when(empresaRepository.findById(empresaId))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> empresaService.obterPorId(empresaId)
        );

        assertTrue(exception.getMessage().contains("não encontrado"));
    }

    @Test
    @DisplayName("Deve atualizar dados da empresa")
    void testAtualizarEmpresa() {
        // Arrange
        String empresaId = "uuid-test-123";
        Empresa empresaExistente = new Empresa(
                "Empresa Antiga Ltda",
                "Empresa Antiga",
                "12345678000190",
                "antigo@empresa.com"
        );
        empresaExistente.setId(empresaId);

        EmpresaUpdateRequest request = new EmpresaUpdateRequest(
                "Empresa Nova Ltda",
                "Empresa Nova",
                "novo@empresa.com"
        );

        when(empresaRepository.findById(empresaId))
                .thenReturn(Optional.of(empresaExistente));

        when(empresaRepository.save(any(Empresa.class)))
                .thenReturn(empresaExistente);

        // Act
        Empresa empresa = empresaService.atualizar(empresaId, request);

        // Assert
        assertNotNull(empresa);
        assertEquals("Empresa Nova Ltda", empresa.getRazaoSocial());
        assertEquals("novo@empresa.com", empresa.getEmail());
    }

    @Test
    @DisplayName("Deve inativar empresa")
    void testInativarEmpresa() {
        // Arrange
        String empresaId = "uuid-test-123";
        Empresa empresa = new Empresa(
                "Empresa Teste Ltda",
                "Empresa Teste",
                "12345678000190",
                "contato@empresa.com"
        );
        empresa.setId(empresaId);

        when(empresaRepository.findById(empresaId))
                .thenReturn(Optional.of(empresa));

        when(empresaRepository.save(any(Empresa.class)))
                .thenReturn(empresa);

        // Act
        Empresa empresaAtualizada = empresaService.alterarStatus(empresaId, false);

        // Assert
        assertNotNull(empresaAtualizada);
    }

    @Test
    @DisplayName("Deve ativar empresa")
    void testAtivarEmpresa() {
        // Arrange
        String empresaId = "uuid-test-123";
        Empresa empresa = new Empresa(
                "Empresa Teste Ltda",
                "Empresa Teste",
                "12345678000190",
                "contato@empresa.com"
        );
        empresa.setId(empresaId);
        empresa.inativar();

        when(empresaRepository.findById(empresaId))
                .thenReturn(Optional.of(empresa));

        when(empresaRepository.save(any(Empresa.class)))
                .thenReturn(empresa);

        // Act
        Empresa empresaAtualizada = empresaService.alterarStatus(empresaId, true);

        // Assert
        assertNotNull(empresaAtualizada);
    }
}