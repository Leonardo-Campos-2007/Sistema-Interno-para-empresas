-- V1__criacao_empresa.sql
-- Criação da tabela EMPRESA
-- Sprint S01 - Fundação + Empresa
-- US-001: Cadastrar empresa

CREATE TABLE empresa (
                         id CHAR(36) PRIMARY KEY COMMENT 'UUID v4',
                         razao_social VARCHAR(255) NOT NULL COMMENT 'Razão social da empresa',
                         nome_fantasia VARCHAR(255) NOT NULL COMMENT 'Nome comercial',
                         cnpj VARCHAR(18) NOT NULL UNIQUE COMMENT 'CNPJ único por instalação',
                         email VARCHAR(255) NOT NULL COMMENT 'E-mail de contato',
                         telefone VARCHAR(20) COMMENT 'Telefone de contato',
                         status ENUM('ATIVA', 'INATIVA') NOT NULL DEFAULT 'ATIVA' COMMENT 'Status da empresa',
                         data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação (auditoria)',
                         data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data de última atualização (auditoria)',
                         INDEX idx_cnpj (cnpj),
                         INDEX idx_status (status),
                         INDEX idx_data_criacao (data_criacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela principal: dados da empresa';