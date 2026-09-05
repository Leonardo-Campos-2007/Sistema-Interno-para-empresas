# TESTES

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Linguagem:** Java 21
**Framework:** Spring Boot 4.1.1
**Banco:** MySQL 8
**Arquitetura:** Monólito Modular

---

# 1. Objetivo

Este documento define a estratégia de testes do Sistema Interno para Empresas.

O objetivo é garantir:

* correção das regras de negócio;
* integridade dos dados;
* funcionamento dos endpoints;
* segurança;
* consistência transacional;
* comportamento correto sob concorrência;
* prevenção de regressões;
* confiabilidade das operações críticas.

Os testes fazem parte do processo de desenvolvimento e devem acompanhar a implementação das funcionalidades.

---

# 2. Princípios

A estratégia de testes seguirá os seguintes princípios:

### Testar comportamento

Os testes devem validar comportamento observável e regras do sistema, não detalhes internos sem necessidade.

### Testar regras críticas com maior profundidade

Quanto maior o impacto de uma funcionalidade, maior deve ser seu nível de cobertura e variedade de testes.

### Testes automatizados

Funcionalidades do MVP devem possuir testes automatizados proporcionais à sua complexidade.

### Banco compatível

Testes de integração que dependam de persistência devem utilizar **MySQL**, o mesmo banco adotado pela aplicação.

### Segurança como parte do teste

Autenticação e autorização não serão tratadas apenas como configuração. Devem possuir testes específicos.

---

# 3. Pirâmide de Testes

A estratégia seguirá uma estrutura aproximada:

```text
              E2E / API
                 /\
                /  \
               /    \
              / Integração \
             /--------------\
            /                \
           /   Unitários     \
          /------------------\
```

A maior quantidade de testes deve ser unitária.

Testes de integração devem validar a interação real entre componentes.

Testes de API devem validar o comportamento externo da aplicação.

---

# 4. Níveis de Teste

Serão utilizados principalmente:

1. testes unitários;
2. testes de integração;
3. testes de API;
4. testes de segurança;
5. testes de concorrência;
6. testes de idempotência;
7. testes de migração;
8. testes de regressão.

---

# 5. Ferramentas

## Testes

* JUnit;
* Mockito;
* Spring Boot Test;
* Spring Security Test;
* Testcontainers.

## Banco

* MySQL 8;
* Testcontainers MySQL.

---

# 6. Testes Unitários

Os testes unitários validarão componentes isolados sempre que possível.

Principais alvos:

* Services;
* Use Cases;
* validações;
* regras de negócio;
* cálculos;
* transições de estado;
* autorização contextual quando isolável;
* mapeamentos críticos.

---

# 7. O que não deve ser excessivamente mockado

Não devemos transformar os testes unitários em testes do comportamento do próprio mock.

Exemplo inadequado:

```java
when(repository.findById(id)).thenReturn(...)
verify(repository).save(...)
```

sem testar nenhuma regra relevante.

O objetivo é testar o comportamento do sistema.

---

# 8. Exemplo de Teste de Regra

Para uma saída de estoque:

```text
Dado um estoque com 10 unidades
Quando for solicitada saída de 4 unidades
Então o saldo deve passar para 6
E deve ser criada uma movimentação
```

Outro caso:

```text
Dado um estoque com 10 unidades
Quando for solicitada saída de 11 unidades
Então a operação deve ser rejeitada
E o saldo deve continuar em 10
```

---

# 9. Testes de Integração

Testes de integração verificarão a interação real entre componentes.

Exemplos:

* Service + Repository;
* JPA + MySQL;
* transações;
* migrations;
* Security + Controller;
* múltiplos módulos.

---

# 10. Testcontainers

Quando o teste depender do banco, deverá ser utilizado um container MySQL.

Fluxo:

```text
Teste
  ↓
Testcontainers
  ↓
MySQL
  ↓
Aplicação
```

Objetivo:

* testar queries reais;
* testar constraints;
* testar migrations;
* reduzir diferenças entre teste e produção.

---

# 11. Teste de Banco

Os testes de integração devem validar:

* criação de tabelas;
* constraints;
* foreign keys;
* unicidade;
* persistência;
* relacionamentos;
* atualização;
* rollback;
* concorrência quando aplicável.

---

# 12. Testes de Migration

As migrations devem ser testadas durante a inicialização do ambiente de integração.

O teste deve verificar que:

```text
Aplicação
   ↓
Flyway
   ↓
Migrations
   ↓
MySQL
```

produz um banco válido.

Migrations inconsistentes devem impedir a inicialização do ambiente de teste.

---

# 13. Testes de Controller / API

Os endpoints devem possuir testes verificando:

* HTTP method;
* status code;
* request;
* response;
* validação;
* autenticação;
* autorização;
* tratamento de erros.

---

# 14. Autenticação

Testes obrigatórios:

### Login válido

```text
Credenciais corretas
        ↓
HTTP 200
        ↓
Token
```

### Senha incorreta

```text
Senha inválida
        ↓
Acesso negado
```

### Usuário bloqueado

```text
Usuário BLOQUEADO
        ↓
Login rejeitado
```

### Usuário inativo

```text
Usuário INATIVO
        ↓
Login rejeitado
```

### Token inválido

```text
Token inválido
        ↓
HTTP 401
```

### Token expirado

```text
Token expirado
        ↓
HTTP 401
```

---

# 15. Testes de Autorização

Devem ser testados pelo menos:

* usuário sem permissão;
* usuário com permissão insuficiente;
* usuário com permissão correta;
* acesso ao próprio recurso;
* tentativa de acesso a recurso de outro usuário;
* usuário gestor;
* usuário administrador;
* tentativa de escalonamento.

---

# 16. Testes contra BOLA / IDOR

Cenário:

```text
Usuário A
   ↓
solicita /tarefas/{id-da-tarefa-do-usuário-B}
```

O sistema deve verificar:

* autenticação;
* permissão;
* contexto;
* proprietário/responsável;
* escopo organizacional.

Se o acesso não for permitido:

```text
HTTP 403
```

ou resposta equivalente definida pela política de segurança.

---

# 17. Testes contra Mass Assignment

Enviar propriedades que não pertencem ao caso de uso.

Exemplo:

```json
{
  "nome": "Usuário",
  "perfil": "ADMINISTRADOR"
}
```

O endpoint de atualização comum não deve permitir alteração arbitrária do perfil.

---

# 18. Testes de Validação

Devem ser testados:

* campo obrigatório ausente;
* tamanho inválido;
* formato inválido;
* e-mail inválido;
* valor negativo;
* quantidade zero;
* quantidade negativa;
* datas inválidas;
* enum inválido;
* ID inexistente.

---

# 19. Testes de Estado

Cada workflow deverá possuir testes para transições válidas e inválidas.

## Venda

Válido:

```text
RASCUNHO → CONFIRMADA
CONFIRMADA → CONCLUIDA
RASCUNHO → CANCELADA
```

Inválido:

```text
CANCELADA → CONCLUIDA
CONCLUIDA → RASCUNHO
```

## Compra

Aplicar a mesma lógica.

## Tarefa

Testar transições permitidas e inválidas.

## Projeto

Testar estados e restrições após conclusão/cancelamento.

---

# 20. Testes de Estoque

Devem validar:

### Entrada

```text
10 + 5 = 15
```

### Saída

```text
10 - 5 = 5
```

### Saída acima do saldo

```text
10 - 11
```

deve ser rejeitada quando estoque negativo não for permitido.

### Ajuste

Deve atualizar corretamente o saldo e gerar histórico.

### Serviço

Produto do tipo `SERVICO` não deve possuir operação de estoque.

---

# 21. Testes de Histórico de Estoque

Depois de uma movimentação, verificar:

* saldo anterior;
* quantidade movimentada;
* saldo posterior;
* tipo;
* responsável;
* data.

Exemplo:

```text
Saldo anterior: 20
Entrada:        5
Saldo posterior:25
```

---

# 22. Testes de Concorrência no Estoque

Cenário:

```text
Estoque = 10

Thread A → saída de 7
Thread B → saída de 7
```

O sistema não pode aceitar ambas de forma que o saldo final fique inválido.

O comportamento esperado é:

* uma operação ser concluída;
* a outra ser rejeitada ou tratada conforme a política de concorrência.

---

# 23. Testes de Idempotência do Estoque

Executar duas vezes:

```text
Idempotency-Key: ABC123
```

A operação deve produzir apenas um efeito de negócio.

Exemplo:

```text
Entrada = 10

Primeira chamada → +10
Segunda chamada   → nenhum segundo efeito
```

---

# 24. Testes de Vendas

Devem validar:

* venda vazia;
* cliente inválido;
* produto inexistente;
* produto inativo;
* quantidade inválida;
* desconto inválido;
* cálculo de subtotal;
* cálculo de total;
* confirmação;
* cancelamento;
* estoque insuficiente;
* confirmação duplicada;
* operação fora de ordem.

---

# 25. Testes de Compra

Devem validar:

* compra sem itens;
* fornecedor inválido;
* produto inexistente;
* produto inativo;
* quantidade inválida;
* desconto;
* cálculo;
* confirmação;
* cancelamento;
* entrada de estoque quando aplicável;
* duplicação;
* estado inválido.

---

# 26. Testes Financeiros

O módulo financeiro deve possuir uma das maiores prioridades de teste.

Devem ser testados:

* criação de conta;
* saldo;
* conta inativa;
* conta a receber;
* conta a pagar;
* parcelas;
* pagamentos;
* pagamentos parciais;
* quitação;
* pagamento excedente;
* vencimento;
* cancelamento;
* estorno;
* movimentação;
* saldo anterior;
* saldo posterior.

---

# 27. Teste de Pagamento Parcial

Exemplo:

```text
Parcela = R$ 1.000,00

Pagamento 1 = R$ 300,00
Saldo = R$ 700,00

Pagamento 2 = R$ 700,00
Saldo = R$ 0,00
```

Ao atingir zero:

```text
Parcela → PAGA
```

---

# 28. Teste de Pagamento Excedente

Exemplo:

```text
Parcela = R$ 1.000,00
Pagamento = R$ 1.001,00
```

A operação deve ser rejeitada.

O saldo não deve ser corrompido.

---

# 29. Teste de Pagamento Concorrente

Cenário:

```text
Parcela = R$ 1.000,00

Thread A → R$ 700
Thread B → R$ 700
```

O sistema não deve permitir que o valor total pago ultrapasse:

```text
R$ 1.000,00
```

---

# 30. Teste de Idempotência Financeira

Executar duas requisições com a mesma chave:

```text
Idempotency-Key: PAG-123
```

O sistema deve efetivar somente uma operação.

---

# 31. Teste de Movimentação Financeira

Para entrada:

```text
Saldo anterior = 1.000
Entrada = 300
Saldo posterior = 1.300
```

Para saída:

```text
Saldo anterior = 1.000
Saída = 300
Saldo posterior = 700
```

Os valores devem permanecer consistentes.

---

# 32. Teste de Estorno

Ao estornar:

```text
Pagamento original
        ↓
Estorno
        ↓
Operação compensatória
```

O sistema não deve apagar o pagamento original.

Deve permanecer possível rastrear:

```text
Operação original
        ↓
Estorno
```

---

# 33. Testes de Integração Comercial

## Venda

Verificar se a operação integrada mantém consistência entre:

```text
Venda
 ↓
Estoque
 ↓
Conta a Receber
```

quando aplicável.

## Compra

Verificar:

```text
Compra
 ↓
Estoque
 ↓
Conta a Pagar
```

quando aplicável.

---

# 34. Atomicidade

Testar cenários em que uma etapa da operação integrada falha.

Exemplo:

```text
Venda
 ↓
Estoque atualizado
 ↓
Erro financeiro
```

O teste deve verificar que o estado final não ficou parcialmente aplicado.

---

# 35. Rollback

Exemplo:

```text
BEGIN

Venda criada
Estoque atualizado
Erro

ROLLBACK
```

O teste deve confirmar que o banco não ficou com apenas parte da operação.

---

# 36. Auditoria

Devem existir testes verificando:

* criação de auditoria;
* usuário;
* ação;
* entidade;
* entidadeId;
* timestamp;
* requestId;
* resultado.

Também deve ser testado que dados sensíveis não sejam armazenados.

---

# 37. Testes de Dados Sensíveis

Enviar:

```text
senha
token
refresh token
credencial
```

e verificar que nenhum desses dados aparece:

* na resposta;
* em logs;
* na auditoria.

---

# 38. Testes de Paginação

Verificar:

* `page`;
* `size`;
* tamanho máximo;
* primeira página;
* última página;
* quantidade total;
* filtros combinados.

Exemplo:

```http
GET /api/v1/clientes?page=0&size=20
```

---

# 39. Testes de Filtros

Verificar:

* filtro por status;
* filtro por nome;
* filtro por data;
* filtros combinados;
* valores inválidos;
* campos não permitidos.

---

# 40. Testes de Ordenação

Verificar somente campos explicitamente permitidos.

Exemplo:

```http
GET /api/v1/clientes?sort=nome,asc
```

Tentativas com campos não permitidos devem ser rejeitadas.

---

# 41. Testes de Error Handling

Verificar respostas para:

* recurso inexistente;
* validação inválida;
* autenticação ausente;
* autorização negada;
* conflito;
* regra de negócio;
* erro inesperado.

Nenhuma resposta deve expor:

* stack trace;
* SQL;
* classes internas;
* credenciais.

---

# 42. Testes de Banco

Devem validar:

### Unicidade

* CPF;
* matrícula;
* CNPJ;
* e-mail quando aplicável;
* SKU;
* código de barras;
* números de venda/compra;
* associações N:N.

### Integridade

* FK;
* NOT NULL;
* constraints;
* relacionamentos.

---

# 43. Testes de UUID

Todos os endpoints que recebem UUID devem testar:

* UUID válido;
* UUID malformado;
* UUID inexistente;
* UUID de recurso sem autorização.

---

# 44. Testes de Regressão

Toda correção de bug relevante deve gerar um teste automatizado que reproduza o problema.

Fluxo:

```text
Bug encontrado
     ↓
Teste reproduz o bug
     ↓
Correção
     ↓
Teste passa
     ↓
Teste permanece no projeto
```

---

# 45. Testes de Segurança

Os testes de segurança devem verificar:

```text
Sem autenticação
       ↓
Negado

Autenticado sem permissão
       ↓
Negado

Autenticado com permissão
       ↓
Permitido

Com permissão, mas sem acesso contextual
       ↓
Negado
```

---

# 46. Testes de Workflow Bypass

Enviar diretamente um estado final:

```json
{
  "status": "CONCLUIDA"
}
```

não deve ser suficiente para contornar o workflow.

O servidor deve validar a transição.

---

# 47. Testes de Parameter Tampering

Modificar valores críticos enviados pelo cliente.

Exemplo:

```json
{
  "subtotal": 10,
  "total": 10,
  "itens": [
    {
      "quantidade": 1,
      "precoUnitario": 1000
    }
  ]
}
```

O servidor deve recalcular ou validar os valores.

O cliente não é autoridade sobre:

* total;
* saldo;
* estoque;
* progresso;
* permissões.

---

# 48. Testes de Resource Consumption

Devem ser testados:

* `size` excessivo;
* requisições repetidas;
* filtros custosos;
* payloads muito grandes quando aplicável.

O servidor deve impor limites.

---

# 49. Testes de Rate Limiting

Principalmente:

* login;
* recuperação de acesso;
* endpoints sensíveis.

Exemplo conceitual:

```text
muitas requisições
       ↓
HTTP 429
```

---

# 50. Testes do Actuator

Verificar:

* health check;
* status da aplicação;
* disponibilidade de componentes.

Endpoints administrativos do Actuator não devem expor informações sensíveis.

---

# 51. Cobertura

Não será definida uma porcentagem única como critério absoluto.

A prioridade será:

> **cobertura de comportamento crítico em vez de cobertura numérica indiscriminada.**

Entretanto, como referência de projeto:

* regras de negócio críticas: cobertura elevada;
* financeiro: cobertura elevada;
* estoque: cobertura elevada;
* segurança: cobertura elevada;
* Controllers simples: cobertura proporcional;
* getters/setters triviais: não devem orientar a métrica.

---

# 52. Critérios mínimos por módulo

| Módulo      | Testes prioritários                             |
| ----------- | ----------------------------------------------- |
| Empresa     | CRUD + validação                                |
| Usuário     | autenticação + estado                           |
| Perfil      | permissões                                      |
| Permissão   | autorização                                     |
| Funcionário | regras cadastrais                               |
| Projeto     | workflow + progresso                            |
| Tarefa      | workflow + histórico                            |
| Reunião     | participantes + estados                         |
| Cliente     | cadastro + consultas                            |
| Fornecedor  | cadastro + consultas                            |
| Produto     | validação + tipo                                |
| Estoque     | saldo + concorrência + idempotência             |
| Venda       | cálculo + estado + integração                   |
| Compra      | cálculo + estado + integração                   |
| Financeiro  | saldo + obrigações + parcelas                   |
| Pagamento   | parcial + excesso + concorrência + idempotência |
| Auditoria   | rastreabilidade + sanitização                   |
| Relatórios  | consistência dos agregados                      |
| Security    | autenticação + autorização                      |

---

# 53. Testes por Sprint

## S01

* aplicação;
* contexto Spring;
* empresa;
* banco;
* migrations.

## S02

* autenticação;
* senha;
* JWT;
* estados do usuário.

## S03

* perfis;
* permissões;
* RBAC.

## S04

* funcionários;
* usuários;
* autorização contextual.

## S05

* projetos;
* tarefas;
* workflow;
* histórico.

## S06

* reuniões;
* clientes;
* fornecedores.

## S07

* produtos;
* categorias;
* estoque;
* concorrência.

## S08

* vendas;
* cálculo;
* estado;
* estoque.

## S09

* compras;
* cálculo;
* estado;
* estoque.

## S10

* contas financeiras;
* categorias;
* saldo.

## S11

* contas a receber;
* contas a pagar;
* parcelas.

## S12

* pagamentos;
* movimentações;
* idempotência;
* concorrência;
* integração;
* transações.

## S13

* auditoria;
* consultas;
* filtros;
* paginação;
* relatórios;
* indicadores.

---

# 54. Testes automatizados obrigatórios antes de concluir o MVP

Antes de considerar o MVP pronto, devem existir testes para:

### Segurança

* login;
* token;
* autorização;
* BOLA/IDOR;
* privilégio.

### Estoque

* entrada;
* saída;
* saldo;
* concorrência;
* idempotência.

### Comercial

* venda;
* compra;
* cálculo;
* estados;
* integração.

### Financeiro

* obrigação;
* parcela;
* pagamento;
* pagamento parcial;
* pagamento excedente;
* concorrência;
* idempotência;
* estorno;
* saldo.

### Integridade

* transações;
* rollback;
* constraints.

### API

* validações;
* erros;
* paginação;
* filtros;
* autenticação.

---

# 55. Execução dos testes

Executar:

```bash
mvn test
```

Para testes de integração, o ambiente deverá iniciar automaticamente os containers necessários quando configurado com Testcontainers.

---

# 56. Testes antes do Commit

Antes de criar um commit:

```bash
mvn test
```

Deve concluir sem falhas.

Para alterações que envolvam:

* banco;
* segurança;
* financeiro;
* estoque;
* workflows;

também devem ser executados os testes específicos relacionados.

---

# 57. Testes antes de Pull Request

Antes do Pull Request:

* testes unitários aprovados;
* testes de integração aprovados;
* testes de segurança pertinentes aprovados;
* análise de qualidade aprovada;
* nenhuma regressão conhecida;
* migrations verificadas quando aplicável.

---

# 58. Testes antes de Release

Antes de uma versão de release:

```text
Build
 ↓
Testes unitários
 ↓
Testes de integração
 ↓
Testes de segurança
 ↓
Testes de regressão
 ↓
Análise de dependências
 ↓
Build final
```

---

# 59. Falha de Teste

Uma falha de teste não deve ser ignorada simplesmente para permitir o build.

Toda falha deve ser classificada como:

* erro do teste;
* erro da implementação;
* mudança de requisito;
* mudança de regra;
* problema de ambiente.

A decisão deve ser registrada quando houver alteração relevante.

---

# 60. Dados de Teste

Os testes devem utilizar dados controlados.

Não utilizar dados reais de produção.

Dados sensíveis reais não devem ser utilizados no ambiente de teste.

---

# 61. Isolamento

Testes devem ser independentes sempre que possível.

Um teste não deve depender do estado deixado por outro.

O banco utilizado em testes de integração deve possuir mecanismos para garantir isolamento e previsibilidade.

---

# 62. Testes e Transações

Testes devem verificar que operações compostas:

```text
A + B + C
```

não resultem em:

```text
A + B
```

quando `C` falhar, salvo quando houver mecanismo de compensação explicitamente definido.

---

# 63. Testes de Auditoria em Falhas

Operações malsucedidas relevantes devem ser avaliadas quanto à necessidade de auditoria.

O teste deve verificar se o registro contém somente dados permitidos e suficientes para rastreamento.

---

# 64. Testes de Observabilidade

Devem ser verificadas condições como:

* requestId disponível;
* logs úteis para diagnóstico;
* ausência de dados sensíveis;
* health check funcionando.

---

# 65. Critério de Qualidade

Uma funcionalidade não será considerada tecnicamente concluída apenas porque:

```text
"o endpoint funciona"
```

Ela deverá também possuir:

```text
Implementação
+
Validação
+
Segurança
+
Testes
+
Integridade
+
Documentação
```

quando aplicável.

---

# 66. Definition of Test Done

Um conjunto de testes será considerado concluído quando:

* testes executarem;
* cenários positivos forem cobertos;
* cenários negativos relevantes forem cobertos;
* regras críticas forem verificadas;
* integração necessária estiver validada;
* não houver falhas conhecidas não tratadas;
* dados sensíveis não forem expostos.

---

# 67. Estado do Documento

**Documento:** Testes
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Banco:** MySQL 8
**Estado:** Estratégia de testes consolidada inicial

Este documento deve evoluir conforme novas regras, módulos, riscos e comportamentos forem introduzidos.
