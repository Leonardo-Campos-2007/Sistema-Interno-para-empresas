-- Sprint S03 - Perfis e permissões
-- Cria os conceitos de perfil, permissão e sua associação.

CREATE TABLE perfil (
    id CHAR(36) PRIMARY KEY COMMENT 'UUID v4',
    nome VARCHAR(100) NOT NULL COMMENT 'Nome do perfil',
    descricao VARCHAR(500) NULL COMMENT 'Descrição do perfil',
    status ENUM('ATIVO', 'INATIVO') NOT NULL DEFAULT 'ATIVO' COMMENT 'Estado do perfil',
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_perfil_nome (nome),
    INDEX idx_perfil_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Perfis de acesso';

CREATE TABLE permissao (
    id CHAR(36) PRIMARY KEY COMMENT 'UUID v4',
    nome VARCHAR(150) NOT NULL COMMENT 'Identificador legível da permissão',
    descricao VARCHAR(500) NULL COMMENT 'Descrição da permissão',
    modulo VARCHAR(100) NOT NULL COMMENT 'Módulo do sistema',
    acao VARCHAR(100) NOT NULL COMMENT 'Ação autorizada',
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_permissao_modulo_acao UNIQUE (modulo, acao),
    INDEX idx_permissao_modulo_acao (modulo, acao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Permissões funcionais';

CREATE TABLE perfil_permissao (
    perfil_id CHAR(36) NOT NULL,
    permissao_id CHAR(36) NOT NULL,
    PRIMARY KEY (perfil_id, permissao_id),
    CONSTRAINT fk_perfil_permissao_perfil
        FOREIGN KEY (perfil_id) REFERENCES perfil(id),
    CONSTRAINT fk_perfil_permissao_permissao
        FOREIGN KEY (permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Permissões associadas aos perfis';
