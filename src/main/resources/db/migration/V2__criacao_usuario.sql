-- V2__criacao_usuario.sql
-- Criação da tabela USUARIO
-- Sprint S02 - Autenticação
-- US-005: Login

CREATE TABLE usuario (
    id CHAR(36) PRIMARY KEY COMMENT 'UUID v4',
    funcionario_id CHAR(36) NULL COMMENT 'FK para funcionario (1:0..1) - será NOT NULL após S04',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT 'E-mail de login',
    senha_hash VARCHAR(255) NOT NULL COMMENT 'Hash BCrypt da senha',
    status ENUM('ATIVO', 'BLOQUEADO', 'INATIVO') NOT NULL DEFAULT 'ATIVO' COMMENT 'Status da conta',
    ultimo_login DATETIME NULL COMMENT 'Data/hora do último login',
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação',
    data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data de última atualização',
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_funcionario (funcionario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Conta de acesso ao sistema';
