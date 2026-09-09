-- Versão de token para invalidação de JWT em logout e alteração de senha.
-- Sprint S02 - US-006 / US-007

ALTER TABLE usuario
    ADD COLUMN token_version BIGINT NOT NULL DEFAULT 0
    COMMENT 'Versão usada para invalidar tokens emitidos anteriormente'
    AFTER senha_hash;
