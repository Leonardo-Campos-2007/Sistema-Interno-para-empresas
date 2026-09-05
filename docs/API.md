# API

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Arquitetura:** API REST
**Framework:** Spring MVC
**Autenticação:** JWT
**Banco de dados:** MySQL 8

---

# 1. Objetivo

Este documento define o contrato inicial da API REST do Sistema Interno para Empresas.

A API será a principal interface de comunicação entre clientes e o backend.

Ela deverá:

* expor recursos do domínio;
* validar entradas;
* autenticar usuários;
* autorizar operações;
* aplicar regras de negócio;
* retornar respostas padronizadas;
* proteger dados sensíveis;
* suportar paginação e filtros;
* permitir rastreamento das operações.

---

# 2. Princípios da API

A API deverá seguir:

* REST;
* HTTP;
* JSON;
* DTOs;
* autenticação;
* autorização;
* validação no servidor;
* tratamento padronizado de erros;
* paginação;
* filtros controlados;
* idempotência em operações críticas.

---

# 3. Base URL

O caminho definitivo será definido de acordo com o ambiente.

Exemplo:

```text
/api/v1
```

Desenvolvimento:

```text
http://localhost:8080/api/v1
```

Produção deverá utilizar domínio e HTTPS apropriados.

---

# 4. Versionamento

A API será versionada pela URL:

```text
/api/v1
```

Exemplo:

```http
GET /api/v1/clientes
```

Uma alteração incompatível deverá resultar em nova versão da API.

---

# 5. Formato

Formato padrão:

```http
Content-Type: application/json
```

As respostas de sucesso utilizarão JSON, exceto endpoints que posteriormente forem definidos para arquivos ou outros formatos.

---

# 6. Autenticação

Endpoints protegidos deverão utilizar autenticação baseada em JWT.

Formato esperado:

```http
Authorization: Bearer <token>
```

Fluxo:

```text
Cliente
   ↓
POST /auth/login
   ↓
Credenciais
   ↓
JWT
   ↓
Authorization: Bearer ...
   ↓
Recurso protegido
```

---

# 7. Endpoints Públicos

O conjunto inicial de endpoints públicos será restrito.

## POST /auth/login

Realiza autenticação.

### Request

```json
{
  "email": "usuario@empresa.com",
  "senha": "********"
}
```

### Response — sucesso

```json
{
  "accessToken": "<token>",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

Os campos definitivos do token e da resposta serão definidos durante a implementação do módulo Security.

---

# 8. Endpoints de Autenticação

## POST /auth/login

Autentica o usuário.

**Autenticação:** Não

**Rate limiting:** Sim

---

## POST /auth/logout

Encerra o contexto de autenticação conforme a estratégia de tokens adotada.

**Autenticação:** Sim

---

## POST /auth/change-password

Altera a senha do usuário autenticado.

**Autenticação:** Sim

---

## POST /auth/recovery

Inicia fluxo de recuperação de acesso.

**Autenticação:** Não

**Rate limiting:** Sim

O fluxo definitivo de recuperação será especificado antes da implementação.

---

# 9. Empresa

## GET /empresa

Consulta os dados da empresa.

**Autenticação:** Sim

**Permissão:** `EMPRESA:VISUALIZAR`

---

## POST /empresa

Cadastra a empresa.

**Autenticação:** Sim

**Permissão:** `EMPRESA:CRIAR`

No cenário de instalação inicial, o fluxo de bootstrap pode ser utilizado em vez de uma operação pública convencional.

---

## PUT /empresa

Atualiza os dados da empresa.

**Autenticação:** Sim

**Permissão:** `EMPRESA:EDITAR`

---

## PATCH /empresa/status

Ativa ou inativa a empresa.

**Autenticação:** Sim

**Permissão:** `EMPRESA:EDITAR`

**Auditoria:** Sim

---

# 10. Usuários

## GET /usuarios

Lista usuários.

Suporta:

```text
page
size
sort
status
nome
email
```

**Autenticação:** Sim

**Permissão:** `USUARIOS:VISUALIZAR`

---

## GET /usuarios/{id}

Consulta usuário específico.

**Autenticação:** Sim

**Permissão:** `USUARIOS:VISUALIZAR`

**Autorização contextual:** Sim

---

## POST /usuarios

Cria usuário vinculado a funcionário.

**Autenticação:** Sim

**Permissão:** `USUARIOS:CRIAR`

---

## PUT /usuarios/{id}

Atualiza dados permitidos do usuário.

**Autenticação:** Sim

**Permissão:** `USUARIOS:EDITAR`

**Autorização contextual:** Sim

---

## PATCH /usuarios/{id}/status

Altera estado do usuário.

**Autenticação:** Sim

**Permissão:** `USUARIOS:EDITAR`

**Auditoria:** Sim

---

# 11. Perfis

## GET /perfis

Lista perfis.

**Permissão:** `PERFIS:VISUALIZAR`

---

## GET /perfis/{id}

Consulta perfil.

**Permissão:** `PERFIS:VISUALIZAR`

---

## POST /perfis

Cria perfil.

**Permissão:** `PERFIS:CRIAR`

---

## PUT /perfis/{id}

Edita perfil.

**Permissão:** `PERFIS:EDITAR`

---

## PATCH /perfis/{id}/status

Ativa ou inativa perfil.

**Permissão:** `PERFIS:EDITAR`

**Auditoria:** Sim

---

## GET /perfis/{id}/permissoes

Consulta permissões do perfil.

**Permissão:** `PERMISSOES:VISUALIZAR`

---

## PUT /perfis/{id}/permissoes

Substitui a associação de permissões do perfil.

**Permissão:** `PERMISSOES:EDITAR`

**Auditoria:** Sim

---

# 12. Permissões

As permissões do sistema são controladas pelo domínio de segurança.

## GET /permissoes

Consulta permissões disponíveis.

**Permissão:** `PERMISSOES:VISUALIZAR`

A criação de permissões poderá ser restrita à configuração administrativa do sistema, evitando que administradores de negócio criem permissões arbitrárias.

---

# 13. Associação Usuário / Perfil

## GET /usuarios/{id}/perfis

Consulta perfis de um usuário.

**Permissão:** `PERFIS:VISUALIZAR`

---

## PUT /usuarios/{id}/perfis

Define os perfis do usuário.

**Permissão:** `PERFIS:EDITAR`

**Auditoria:** Sim

**Proteção contra escalonamento:** Sim

**Proteção do último administrador:** Sim

---

# 14. Departamentos

## GET /departamentos

Lista departamentos.

Suporta paginação e filtros.

---

## GET /departamentos/{id}

Consulta departamento.

---

## POST /departamentos

Cria departamento.

---

## PUT /departamentos/{id}

Atualiza departamento.

---

## PATCH /departamentos/{id}/status

Ativa ou inativa departamento.

---

# 15. Cargos

## GET /cargos

Lista cargos.

---

## GET /cargos/{id}

Consulta cargo.

---

## POST /cargos

Cria cargo.

---

## PUT /cargos/{id}

Atualiza cargo.

---

## PATCH /cargos/{id}/status

Ativa ou inativa cargo.

---

# 16. Funcionários

## GET /funcionarios

Lista funcionários.

Filtros previstos:

```text
page
size
sort
nome
matricula
cpf
status
departamentoId
cargoId
```

**Paginação:** Sim

---

## GET /funcionarios/{id}

Consulta funcionário.

**Autorização contextual:** Sim

---

## POST /funcionarios

Cadastra funcionário.

---

## PUT /funcionarios/{id}

Atualiza funcionário.

---

## PATCH /funcionarios/{id}/status

Altera status funcional.

---

## GET /funcionarios/{id}/enderecos

Lista endereços do funcionário.

---

## POST /funcionarios/{id}/enderecos

Cadastra endereço.

---

## PUT /funcionarios/{id}/enderecos/{enderecoId}

Atualiza endereço.

---

# 17. Projetos

## GET /projetos

Lista projetos.

Filtros:

```text
page
size
status
prioridade
responsavelId
```

---

## GET /projetos/{id}

Consulta projeto.

---

## POST /projetos

Cria projeto.

---

## PUT /projetos/{id}

Atualiza projeto.

---

## PATCH /projetos/{id}/status

Altera estado do projeto.

---

## PATCH /projetos/{id}/responsavel

Atribui responsável.

---

## GET /projetos/{id}/progresso

Consulta progresso calculado.

O progresso deve ser derivado das tarefas, não informado diretamente pelo cliente.

---

# 18. Tarefas

## GET /tarefas

Lista tarefas.

Filtros:

```text
page
size
status
prioridade
responsavelId
projetoId
dataPrazo
```

---

## GET /tarefas/{id}

Consulta tarefa.

**Autorização contextual:** Sim

---

## POST /tarefas

Cria tarefa.

---

## PUT /tarefas/{id}

Atualiza tarefa.

---

## PATCH /tarefas/{id}/responsavel

Altera responsável.

---

## PATCH /tarefas/{id}/status

Altera status.

O servidor deve validar a transição permitida.

---

## POST /tarefas/{id}/cancelamento

Cancela tarefa conforme regras do domínio.

---

## GET /tarefas/{id}/historico

Consulta histórico de status.

---

# 19. Reuniões

## GET /reunioes

Lista reuniões acessíveis ao usuário.

Filtros:

```text
page
size
dataInicio
dataFim
status
organizadorId
projetoId
```

---

## GET /reunioes/{id}

Consulta reunião.

---

## POST /reunioes

Cria reunião.

---

## PUT /reunioes/{id}

Atualiza reunião.

---

## PATCH /reunioes/{id}/status

Altera status.

---

## POST /reunioes/{id}/participantes

Adiciona participante.

---

## DELETE /reunioes/{id}/participantes/{funcionarioId}

Remove participante quando permitido pelo estado da reunião.

---

## PATCH /reunioes/{id}/participantes/{funcionarioId}/resposta

Aceita ou recusa convite.

---

# 20. Clientes

## GET /clientes

Lista clientes.

Filtros:

```text
page
size
nome
documento
status
```

---

## GET /clientes/{id}

Consulta cliente.

---

## POST /clientes

Cria cliente.

---

## PUT /clientes/{id}

Atualiza cliente.

---

## PATCH /clientes/{id}/status

Ativa ou inativa cliente.

---

## GET /clientes/{id}/enderecos

Lista endereços.

---

## POST /clientes/{id}/enderecos

Cria endereço.

---

## PUT /clientes/{id}/enderecos/{enderecoId}

Atualiza endereço.

---

## GET /clientes/{id}/historico-comercial

Consulta histórico comercial.

O histórico será baseado nas operações comerciais existentes e não será armazenado como duplicação desnecessária.

---

# 21. Fornecedores

## GET /fornecedores

Lista fornecedores.

Filtros:

```text
page
size
nome
documento
status
```

---

## GET /fornecedores/{id}

Consulta fornecedor.

---

## POST /fornecedores

Cria fornecedor.

---

## PUT /fornecedores/{id}

Atualiza fornecedor.

---

## PATCH /fornecedores/{id}/status

Ativa ou inativa fornecedor.

---

## GET /fornecedores/{id}/enderecos

Lista endereços.

---

## POST /fornecedores/{id}/enderecos

Cria endereço.

---

## PUT /fornecedores/{id}/enderecos/{enderecoId}

Atualiza endereço.

---

## GET /fornecedores/{id}/historico-compras

Consulta histórico de compras.

---

# 22. Categorias

## GET /categorias

Lista categorias.

---

## GET /categorias/{id}

Consulta categoria.

---

## POST /categorias

Cria categoria.

---

## PUT /categorias/{id}

Atualiza categoria.

---

## PATCH /categorias/{id}/status

Ativa ou inativa categoria.

---

# 23. Produtos

## GET /produtos

Lista produtos.

Filtros:

```text
page
size
nome
sku
codigoBarras
categoriaId
tipo
status
```

---

## GET /produtos/{id}

Consulta produto.

---

## POST /produtos

Cria produto ou serviço.

---

## PUT /produtos/{id}

Atualiza produto.

---

## PATCH /produtos/{id}/status

Ativa ou inativa produto.

---

# 24. Estoque

## GET /estoques

Lista posições de estoque.

Filtros:

```text
page
size
produtoId
abaixoDoMinimo
```

---

## GET /estoques/{id}

Consulta posição de estoque.

---

## POST /estoques

Cria posição de estoque.

---

## PATCH /estoques/{id}/limites

Atualiza estoque mínimo e máximo.

---

## POST /estoques/{id}/entradas

Registra entrada.

### Headers obrigatórios

```http
Idempotency-Key: <chave>
```

**Autorização:** Sim

**Auditoria:** Sim

**Concorrência:** Sim

---

## POST /estoques/{id}/saidas

Registra saída.

### Headers

```http
Idempotency-Key: <chave>
```

**Auditoria:** Sim

**Concorrência:** Sim

---

## POST /estoques/{id}/ajustes

Registra ajuste.

### Headers

```http
Idempotency-Key: <chave>
```

**Auditoria:** Sim

---

## GET /estoques/{id}/movimentacoes

Consulta histórico de movimentações.

---

# 25. Vendas

## GET /vendas

Lista vendas.

Filtros:

```text
page
size
numero
clienteId
status
dataInicio
dataFim
responsavelId
```

---

## GET /vendas/{id}

Consulta venda.

---

## POST /vendas

Cria venda em rascunho.

---

## PUT /vendas/{id}

Edita venda em estado permitido.

---

## POST /vendas/{id}/itens

Adiciona item.

---

## PUT /vendas/{id}/itens/{itemId}

Atualiza item.

---

## DELETE /vendas/{id}/itens/{itemId}

Remove item enquanto a venda estiver em estado compatível.

---

## POST /vendas/{id}/confirmacao

Confirma venda.

### Headers

```http
Idempotency-Key: <chave>
```

**Controles:**

* autenticação;
* autorização;
* validação;
* estoque;
* cálculo;
* idempotência;
* transação;
* auditoria.

---

## POST /vendas/{id}/cancelamento

Cancela venda conforme o estado atual.

**Auditoria:** Sim

---

# 26. Compras

## GET /compras

Lista compras.

Filtros:

```text
page
size
numero
fornecedorId
status
dataInicio
dataFim
responsavelId
```

---

## GET /compras/{id}

Consulta compra.

---

## POST /compras

Cria compra em rascunho.

---

## PUT /compras/{id}

Edita compra.

---

## POST /compras/{id}/itens

Adiciona item.

---

## PUT /compras/{id}/itens/{itemId}

Atualiza item.

---

## DELETE /compras/{id}/itens/{itemId}

Remove item em estado permitido.

---

## POST /compras/{id}/confirmacao

Confirma compra.

### Headers

```http
Idempotency-Key: <chave>
```

**Controles:**

* autorização;
* validação;
* estoque;
* idempotência;
* transação;
* auditoria.

---

## POST /compras/{id}/cancelamento

Cancela compra.

---

# 27. Contas Financeiras

## GET /contas-financeiras

Lista contas.

Filtros:

```text
page
size
tipo
status
```

---

## GET /contas-financeiras/{id}

Consulta conta.

---

## POST /contas-financeiras

Cria conta.

---

## PUT /contas-financeiras/{id}

Atualiza conta.

---

## PATCH /contas-financeiras/{id}/status

Ativa ou inativa conta.

---

## GET /contas-financeiras/{id}/saldo

Consulta saldo atual.

O saldo é controlado pelo servidor.

---

## GET /contas-financeiras/{id}/movimentacoes

Consulta histórico.

---

# 28. Categorias Financeiras

## GET /categorias-financeiras

Lista categorias.

Filtros:

```text
tipo
status
```

---

## GET /categorias-financeiras/{id}

Consulta categoria.

---

## POST /categorias-financeiras

Cria categoria.

---

## PUT /categorias-financeiras/{id}

Atualiza categoria.

---

## PATCH /categorias-financeiras/{id}/status

Inativa categoria.

---

# 29. Contas a Receber

## GET /contas-a-receber

Lista contas.

Filtros:

```text
page
size
status
clienteId
vendaId
dataInicio
dataFim
vencimentoInicio
vencimentoFim
```

---

## GET /contas-a-receber/{id}

Consulta conta.

---

## POST /contas-a-receber

Cria conta a receber.

---

## PUT /contas-a-receber/{id}

Atualiza conta quando permitido.

---

## PATCH /contas-a-receber/{id}/cancelamento

Cancela conta.

**Auditoria:** Sim

---

## GET /contas-a-receber/{id}/parcelas

Lista parcelas.

---

# 30. Contas a Pagar

## GET /contas-a-pagar

Lista contas.

Filtros:

```text
page
size
status
fornecedorId
compraId
dataInicio
dataFim
vencimentoInicio
vencimentoFim
```

---

## GET /contas-a-pagar/{id}

Consulta conta.

---

## POST /contas-a-pagar

Cria conta a pagar.

---

## PUT /contas-a-pagar/{id}

Atualiza conta quando permitido.

---

## PATCH /contas-a-pagar/{id}/cancelamento

Cancela conta.

---

## GET /contas-a-pagar/{id}/parcelas

Lista parcelas.

---

# 31. Parcelas

As operações de parcela podem ser subordinadas à obrigação correspondente.

## POST /contas-a-receber/{id}/parcelas

Cria parcelas da conta a receber.

---

## POST /contas-a-pagar/{id}/parcelas

Cria parcelas da conta a pagar.

---

## GET /parcelas

Consulta parcelas.

Filtros:

```text
page
size
status
dataVencimento
tipoObrigacao
```

---

## GET /parcelas/{id}

Consulta parcela.

---

# 32. Pagamentos e Recebimentos

Como a mesma entidade `Pagamento` pode representar liquidação de contas a receber ou a pagar, a API deverá manter um conceito unificado de liquidação.

## POST /parcelas/{id}/pagamentos

Registra pagamento ou recebimento.

### Headers

```http
Idempotency-Key: <chave>
```

### Request

```json
{
  "contaFinanceiraId": "uuid",
  "valor": 100.00,
  "dataPagamento": "2026-09-05T10:30:00",
  "formaPagamento": "PIX",
  "observacoes": "Pagamento"
}
```

### Controles obrigatórios

* autenticação;
* autorização;
* validação;
* verificação de saldo;
* concorrência;
* idempotência;
* transação;
* auditoria.

---

## GET /pagamentos

Consulta pagamentos.

Filtros:

```text
page
size
parcelaId
contaFinanceiraId
formaPagamento
dataInicio
dataFim
```

---

## GET /pagamentos/{id}

Consulta pagamento.

---

## POST /pagamentos/{id}/estorno

Estorna pagamento.

### Headers

```http
Idempotency-Key: <chave>
```

**Auditoria:** Sim

**Autorização:** Sim

---

# 33. Movimentações Financeiras

## GET /movimentacoes-financeiras

Consulta movimentações.

Filtros:

```text
page
size
contaFinanceiraId
tipo
dataInicio
dataFim
```

---

## GET /movimentacoes-financeiras/{id}

Consulta movimentação.

Movimentações históricas não devem possuir endpoint de edição convencional.

---

# 34. Auditoria

## GET /auditoria

Consulta registros de auditoria.

Filtros possíveis:

```text
page
size
usuarioId
acao
entidade
entidadeId
resultado
requestId
dataInicio
dataFim
```

**Permissão:** `AUDITORIA:VISUALIZAR`

---

## GET /auditoria/{id}

Consulta registro específico.

**Permissão:** `AUDITORIA:VISUALIZAR`

---

# 35. Relatórios

Os relatórios serão protegidos conforme seu nível de sensibilidade.

## GET /relatorios/vendas

Parâmetros:

```text
dataInicio
dataFim
```

---

## GET /relatorios/compras

Parâmetros:

```text
dataInicio
dataFim
```

---

## GET /relatorios/produtos-mais-vendidos

Parâmetros:

```text
dataInicio
dataFim
limite
```

---

## GET /relatorios/clientes-maior-receita

Parâmetros:

```text
dataInicio
dataFim
limite
```

---

## GET /relatorios/indicadores

Retorna indicadores gerenciais básicos.

---

# 36. Paginação

Endpoints de listagem devem utilizar paginação quando o volume puder crescer significativamente.

Parâmetros:

```text
page
size
sort
```

Exemplo:

```http
GET /api/v1/clientes?page=0&size=20
```

O servidor deve impor limite máximo para `size`.

---

# 37. Resposta Paginada

Formato conceitual:

```json
{
  "content": [],
  "page": 0,
  "size": 20,
  "totalElements": 120,
  "totalPages": 6,
  "first": true,
  "last": false
}
```

O formato definitivo será padronizado durante a implementação.

---

# 38. Filtros

Filtros aceitos devem ser explicitamente definidos pelo endpoint.

O cliente não deve poder escolher arbitrariamente:

* campos SQL;
* joins;
* expressões;
* ordenações não autorizadas.

---

# 39. Ordenação

Exemplo:

```http
GET /clientes?sort=nome,asc
```

Somente propriedades autorizadas podem ser utilizadas.

A API deve rejeitar campos de ordenação desconhecidos.

---

# 40. Códigos HTTP

A API deverá utilizar códigos HTTP coerentes.

| Código | Uso                                        |
| ------ | ------------------------------------------ |
| 200    | Operação concluída                         |
| 201    | Recurso criado                             |
| 202    | Processamento assíncrono, quando aplicável |
| 204    | Operação concluída sem corpo               |
| 400    | Requisição inválida                        |
| 401    | Não autenticado                            |
| 403    | Não autorizado                             |
| 404    | Recurso não encontrado                     |
| 409    | Conflito                                   |
| 422    | Regra de negócio não atendida              |
| 429    | Rate limit                                 |
| 500    | Erro interno                               |
| 503    | Serviço indisponível                       |

O uso exato de `409` e `422` será padronizado durante a implementação.

---

# 41. Formato de Erro

Formato conceitual:

```json
{
  "timestamp": "2026-09-05T12:00:00Z",
  "status": 422,
  "code": "BUSINESS_RULE_VIOLATION",
  "message": "A operação não pode ser realizada no estado atual.",
  "path": "/api/v1/vendas/..."
}
```

Não devem aparecer:

* stack trace;
* SQL;
* classes internas;
* credenciais;
* tokens;
* informações internas desnecessárias.

---

# 42. Erros de Validação

Exemplo:

```json
{
  "timestamp": "2026-09-05T12:00:00Z",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Dados inválidos.",
  "fields": [
    {
      "field": "email",
      "message": "E-mail inválido."
    }
  ]
}
```

---

# 43. Idempotência

Operações críticas deverão aceitar:

```http
Idempotency-Key: <chave>
```

Operações previstas:

* confirmação de venda;
* confirmação de compra;
* entrada de estoque;
* saída de estoque;
* ajustes críticos;
* pagamentos;
* estornos.

A mesma chave processada no mesmo contexto não deve gerar segunda operação efetiva.

---

# 44. Request ID

Cada requisição deverá possuir identificador de correlação.

Exemplo:

```http
X-Request-Id: 8f4e...
```

Caso o cliente não forneça um identificador válido, a aplicação poderá gerar um.

Esse identificador deverá ser utilizado para:

* logs;
* auditoria quando aplicável;
* diagnóstico;
* rastreamento.

---

# 45. Autorização por Endpoint

A autorização deve considerar dois níveis.

### Funcional

Exemplo:

```text
VENDAS:CRIAR
```

### Contextual

Exemplo:

```text
Usuário possui VENDAS:EDITAR
```

mas o recurso ainda precisa pertencer ao escopo que ele pode administrar.

---

# 46. Endpoints Sensíveis

Os seguintes grupos possuem controles reforçados:

```text
/auth/*
/usuarios/*
/perfis/*
/permissoes/*
/estoques/*/entradas
/estoques/*/saidas
/estoques/*/ajustes
/vendas/*/confirmacao
/compras/*/confirmacao
/parcelas/*/pagamentos
/pagamentos/*/estorno
/contas-financeiras/*
/auditoria/*
```

Devem ser avaliados quanto a:

* autenticação;
* autorização;
* auditoria;
* idempotência;
* concorrência;
* rate limiting;
* exposição de dados.

---

# 47. Operação de Venda

O endpoint de confirmação deve validar no servidor:

```text
Venda
  ↓
Cliente
  ↓
Itens
  ↓
Produtos
  ↓
Estoque
  ↓
Valores
  ↓
Estado
```

Depois aplicar os efeitos de domínio correspondentes.

O cliente não deve determinar:

* total final;
* saldo de estoque;
* estado final;
* permissões;
* obrigações financeiras sem validação.

---

# 48. Operação de Compra

A confirmação de compra deverá validar:

```text
Compra
  ↓
Fornecedor
  ↓
Itens
  ↓
Produtos
  ↓
Valores
  ↓
Estado
```

e executar os efeitos necessários.

---

# 49. Operação de Pagamento

O endpoint de pagamento deverá:

1. autenticar;
2. autorizar;
3. validar parcela;
4. validar conta financeira;
5. validar valor;
6. verificar saldo pendente;
7. proteger concorrência;
8. registrar pagamento;
9. atualizar obrigação;
10. gerar movimentação;
11. atualizar saldo;
12. registrar auditoria.

As etapas devem permanecer consistentes em caso de falha.

---

# 50. Operação de Estoque

O endpoint de movimentação deverá:

1. autenticar;
2. autorizar;
3. validar produto;
4. validar tipo;
5. validar quantidade;
6. verificar estado;
7. controlar concorrência;
8. atualizar saldo;
9. registrar histórico;
10. registrar auditoria.

---

# 51. Dados que nunca devem ser retornados

A API não deve retornar:

* senha;
* senha hash;
* JWT;
* refresh token em endpoints que não tenham finalidade explícita para isso;
* credenciais;
* segredos;
* dados administrativos que o usuário não possa consultar.

---

# 52. Dados que devem ser calculados no servidor

O cliente não deve ser autoridade para:

* total de venda;
* total de compra;
* saldo financeiro;
* quantidade atual do estoque;
* progresso do projeto;
* situação de pagamento;
* permissões efetivas.

---

# 53. DTOs

Os endpoints deverão utilizar DTOs específicos.

Exemplos:

```text
FuncionarioCreateRequest
FuncionarioUpdateRequest
FuncionarioResponse

ProdutoCreateRequest
ProdutoUpdateRequest
ProdutoResponse

VendaCreateRequest
VendaResponse

PagamentoCreateRequest
PagamentoResponse
```

DTOs administrativos não devem ser reutilizados indiscriminadamente em operações diferentes.

---

# 54. Convenção de Nomes

Recursos serão representados preferencialmente com substantivos no plural.

Exemplos:

```text
/clientes
/fornecedores
/produtos
/vendas
/compras
/projetos
/tarefas
```

Ações de domínio relevantes podem utilizar endpoints específicos.

Exemplo:

```text
/vendas/{id}/confirmacao
/pagamentos/{id}/estorno
/estoques/{id}/entradas
```

---

# 55. DELETE

DELETE não deve ser utilizado para apagar fisicamente fatos históricos críticos.

Exemplos:

* vendas concluídas;
* compras concluídas;
* pagamentos;
* movimentações;
* auditoria.

Quando o domínio exigir retirada de operação, deverá ser utilizado:

* cancelamento;
* inativação;
* estorno;
* operação compensatória.

---

# 56. Concorrência

Quando ocorrer conflito de atualização, a API deverá retornar erro de conflito apropriado.

Exemplo:

```text
HTTP 409 Conflict
```

O cliente deverá recarregar o recurso antes de tentar novamente quando a política do endpoint exigir.

---

# 57. Rate Limiting

Rate limiting deverá possuir atenção especial para:

* login;
* recuperação de acesso;
* operações de autenticação;
* endpoints críticos;
* endpoints potencialmente custosos.

A política definitiva será configurada durante a implementação de segurança.

---

# 58. CORS

CORS deve ser configurado por ambiente.

Não utilizar:

```text
*
```

como configuração geral de produção sem justificativa.

---

# 59. OpenAPI

A API deverá ser documentada utilizando OpenAPI.

Cada endpoint deve informar, quando aplicável:

* método;
* caminho;
* autenticação;
* autorização;
* parâmetros;
* request body;
* response;
* códigos HTTP;
* erros;
* paginação;
* filtros;
* headers.

---

# 60. Relação com o Backlog

Os endpoints devem possuir rastreabilidade para histórias de usuário.

Exemplo:

```text
US-117
Confirmar venda
    ↓
POST /vendas/{id}/confirmacao
    ↓
RF-127
    ↓
RN-130
RN-131
RN-133
RN-136
```

Outro exemplo:

```text
US-159
Registrar pagamento
    ↓
POST /parcelas/{id}/pagamentos
    ↓
RF-172
    ↓
RN-188
RN-191
RN-192
RN-198
RN-199
```

---

# 61. Relação com Segurança

A API deve seguir `SEGURANCA.md`.

Em especial:

* autenticação;
* autorização funcional;
* autorização contextual;
* validação;
* BOLA/IDOR;
* mass assignment;
* rate limiting;
* logs seguros;
* auditoria;
* idempotência;
* controle de concorrência.

---

# 62. Relação com Modelo de Dados

Os recursos expostos pela API devem respeitar o modelo definido em:

`MODELO-DE-DADOS.md`

A API não deve criar relações artificiais que não existam no domínio.

---

# 63. Testes da API

Cada grupo de endpoints deverá possuir testes adequados.

Exemplos:

### Autenticação

* login válido;
* credencial inválida;
* token inválido;
* token expirado;
* usuário bloqueado.

### Autorização

* usuário sem permissão;
* permissão insuficiente;
* acesso contextual negado;
* escalonamento de privilégio.

### Estoque

* entrada válida;
* saída válida;
* saída acima do saldo;
* duplicidade;
* concorrência.

### Vendas

* venda sem itens;
* produto inativo;
* estoque insuficiente;
* confirmação duplicada;
* operação fora de ordem.

### Financeiro

* pagamento válido;
* pagamento parcial;
* pagamento excedente;
* pagamento duplicado;
* pagamento concorrente;
* estorno.

---

# 64. Estado do Documento

**Documento:** API
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Arquitetura:** REST
**Autenticação:** JWT
**Banco:** MySQL 8
**Estado:** Contrato inicial da API

Este documento deve ser atualizado quando um endpoint for criado, alterado ou removido, ou quando houver alteração relevante no domínio.
