-- V3__bootstrap_admin.sql
-- Usuário admin temporário para testes da autenticação (S02)
-- Senha: Admin@123 (BCrypt)
-- REMOVER ou alterar após S04 (criação formal de usuários)

INSERT INTO usuario (id, funcionario_id, email, senha_hash, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    NULL,
    'admin@sistema.local',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ATIVO'
);
