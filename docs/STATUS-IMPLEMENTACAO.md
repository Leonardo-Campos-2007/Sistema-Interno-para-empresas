# Status da Implementação

Este arquivo registra, de forma simples, o que já foi implementado no projeto.

Ele deve ser atualizado sempre que uma parte relevante de uma Sprint ou uma Sprint inteira for concluída.

---

## S01 — Fundação e Empresa

### Implementado

* estrutura inicial do projeto Spring Boot;
* configuração de MySQL e Flyway;
* migrations para a tabela `empresa`;
* padrão Controller → Service → Repository;
* DTOs, validação de entrada e tratamento global de erros;
* cadastro de empresa;
* consulta da empresa principal da instalação;
* edição de empresa;
* ativação e inativação de empresa;
* validação de CNPJ duplicado;
* regra de apenas uma empresa por instalação;
* testes unitários de `EmpresaService`.

### Observação

Os endpoints de empresa não usam mais o ID fixo `"default"`. Eles localizam a empresa principal salva no banco.

### Situação

Parte funcional concluída e coberta por testes unitários. Ainda podem ser adicionados testes HTTP e de integração com banco.

---

## S02 — Autenticação

### Implementado

* entidade e migration de `usuario`;
* usuário administrador temporário para testes locais;
* login por e-mail e senha;
* hash de senha com BCrypt;
* geração e validação de JWT;
* filtro de autenticação JWT;
* proteção das rotas que não são públicas;
* registro de último login após autenticação bem-sucedida;
* tratamento de usuário inativo e bloqueado;
* alteração de senha em `POST /api/v1/auth/change-password`;
* logout em `POST /api/v1/auth/logout`;
* invalidação de tokens após logout ou alteração de senha;
* migration `V4__adiciona_versao_token_usuario.sql` para controle da versão do token;
* testes unitários para login, alteração de senha e logout;
* testes HTTP do endpoint de login;
* testes do filtro JWT, incluindo token inválido.

### Situação

A base técnica da autenticação está implementada.

### Ainda pendente na S02

* consulta administrativa do último acesso do usuário;
* recuperação de acesso/senha. Esta funcionalidade depende da definição da política de recuperação antes de ser desenvolvida;
* testes completos de integração com banco MySQL/Testcontainers.

---

## Validação atual

Comando executado:

```bash
mvn test
```

Resultado atual:

```text
24 testes executados
0 falhas
0 erros
```

---

## Próxima etapa sugerida

Seguir para a S03: criação de perfis e permissões.

Antes disso, caso desejado, podem ser concluídos os testes de integração pendentes da S01 e S02.

---

## S03 — Perfis e Permissões

### Implementado

* migration para as tabelas `perfil`, `permissao` e `perfil_permissao`;
* entidades de perfil e permissão;
* enums para status de perfil, módulos e ações de permissão;
* repositórios de perfil e permissão;
* unicidade de permissão por módulo e ação no banco.

### Próxima parte

Implementar o cadastro e a consulta de permissões.
