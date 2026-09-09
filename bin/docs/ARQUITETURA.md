# ARQUITETURA

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Arquitetura:** Monólito Modular
**Linguagem:** Java 21
**Framework:** Spring Boot 4.1.1
**Banco de Dados:** MySQL 8

---

# 1. Objetivo

Este documento define a arquitetura técnica do Sistema Interno para Empresas.

A arquitetura tem como objetivos:

* organizar o sistema em módulos de domínio;
* reduzir acoplamento;
* manter responsabilidades bem definidas;
* facilitar testes;
* facilitar manutenção;
* estabelecer padrões para desenvolvimento;
* garantir segurança e integridade;
* permitir evolução futura da aplicação.

---

# 2. Decisão Arquitetural

O sistema será desenvolvido inicialmente como um:

> **Monólito Modular**

A aplicação será executada como uma única unidade, porém internamente será dividida em módulos com responsabilidades específicas.

Isso permite obter organização semelhante à de sistemas distribuídos sem introduzir prematuramente a complexidade operacional de microservices.

---

# 3. Por que Monólito Modular?

A escolha foi feita porque o projeto:

* está em fase inicial;
* possui domínio ainda em evolução;
* será desenvolvido inicialmente por uma equipe pequena;
* possui módulos fortemente relacionados;
* possui necessidade de transações entre módulos;
* não possui requisitos atuais que justifiquem distribuição física.

Microservices não serão utilizados no MVP.

Uma futura divisão em serviços independentes poderá ser avaliada quando houver justificativa técnica real.

---

# 4. Visão Geral

Arquitetura de alto nível:

```text
                        CLIENTE
                           |
                           v
                    +--------------+
                    |   API REST   |
                    +--------------+
                           |
                           v
                 +--------------------+
                 | Spring Security    |
                 | Authentication     |
                 | Authorization      |
                 +--------------------+
                           |
                           v
              +--------------------------+
              | Controllers / Endpoints  |
              +--------------------------+
                           |
                           v
              +--------------------------+
              | Services / Use Cases     |
              +--------------------------+
                           |
              +------------+-------------+
              |            |             |
              v            v             v
        +---------+   +---------+   +---------+
        | Domain  |   | Domain  |   | Domain  |
        | Module  |   | Module  |   | Module  |
        +---------+   +---------+   +---------+
              |
              v
       +---------------+
       | Repositories  |
       +---------------+
              |
              v
       +---------------+
       | JPA/Hibernate |
       +---------------+
              |
              v
       +---------------+
       |    MySQL 8    |
       +---------------+
```

---

# 5. Estrutura Modular

Os principais módulos do MVP são:

```text
empresa
usuario
perfil
permissao
funcionario
departamento
cargo
projeto
tarefa
reuniao
cliente
fornecedor
endereco
categoria
produto
estoque
venda
compra
financeiro
auditoria
relatorio
security
```

Cada módulo deve possuir responsabilidade própria.

---

# 6. Estrutura Interna dos Módulos

Para módulos tradicionais:

```text
funcionario/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Exemplo:

```text
produto/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Módulos mais complexos podem possuir subdivisões internas.

Exemplo:

```text
financeiro/
├── conta/
├── categoria/
├── receber/
├── pagar/
├── parcela/
├── pagamento/
└── movimentacao/
```

A estrutura poderá evoluir conforme o domínio crescer.

---

# 7. Camadas da Aplicação

## 7.1 Controller

Responsabilidades:

* receber requisições HTTP;
* validar entrada;
* receber DTOs;
* encaminhar para serviços;
* transformar resultados em respostas HTTP.

Controllers não devem implementar regras de negócio complexas.

Exemplo:

```text
HTTP Request
     ↓
Controller
     ↓
Service
```

---

# 8. Service / Use Case

A camada de serviço concentra a execução das regras de negócio.

Responsabilidades:

* orquestrar operações;
* validar regras de domínio;
* controlar transações;
* chamar repositories;
* chamar outros serviços quando necessário;
* aplicar autorização contextual;
* coordenar operações compostas.

Exemplo:

```text
VendaController
       ↓
VendaService
       ↓
EstoqueService
       ↓
FinanceiroService
```

---

# 9. Repository

Repositories representam a abstração de persistência.

A aplicação utilizará:

**Spring Data JPA**

Exemplo conceitual:

```text
Service
   ↓
Repository
   ↓
JPA
   ↓
Hibernate
   ↓
MySQL
```

Não será criado um DAO tradicional separado apenas para reproduzir a função do Repository.

---

# 10. Entity

Entities representam objetos persistidos no banco.

Exemplo:

```java
@Entity
public class Funcionario {
    // atributos e comportamento do domínio
}
```

Entities não devem ser utilizadas diretamente como objetos de entrada ou saída HTTP.

---

# 11. DTO

DTOs serão utilizados para controlar os dados que entram e saem da API.

Exemplo:

```text
FuncionarioRequest
FuncionarioResponse
```

Fluxo:

```text
Request DTO
    ↓
Controller
    ↓
Service
    ↓
Entity
    ↓
Repository
```

E no retorno:

```text
Entity
   ↓
Mapper
   ↓
Response DTO
   ↓
Controller
   ↓
HTTP Response
```

---

# 12. Mapeamento

O projeto poderá utilizar **MapStruct** para reduzir código repetitivo de conversão entre DTOs e Entities.

A adoção definitiva deverá ocorrer quando o número de DTOs justificar a dependência.

O mapper não deve conter regras de negócio.

---

# 13. Segurança

A segurança ficará em módulo próprio:

```text
security/
├── config/
├── filter/
├── jwt/
├── service/
├── authorization/
└── principal/
```

Responsabilidades:

* autenticação;
* JWT;
* filtros;
* principal autenticado;
* autorização;
* integração com Spring Security.

---

# 14. Fluxo de Autenticação

Fluxo conceitual:

```text
Cliente
   ↓
POST /auth/login
   ↓
AuthenticationManager
   ↓
UserDetails / Principal
   ↓
Validação de credenciais
   ↓
JWT
   ↓
Cliente
```

As credenciais devem ser verificadas no servidor.

Senhas não serão armazenadas em texto puro.

---

# 15. Fluxo de Autorização

Depois da autenticação:

```text
JWT
 ↓
Security Filter
 ↓
Principal
 ↓
Permissões
 ↓
Autorização funcional
 ↓
Autorização contextual
 ↓
Service
```

A autorização contextual deverá verificar o objeto solicitado.

Exemplo:

```text
Usuário possui:
TAREFAS:VISUALIZAR

Isso não significa automaticamente:

"pode visualizar qualquer tarefa".
```

A regra contextual determinará se aquele objeto específico pode ser acessado.

---

# 16. Persistência

A camada de persistência utilizará:

* Spring Data JPA;
* Hibernate;
* MySQL 8.

Fluxo:

```text
Service
   ↓
Repository
   ↓
EntityManager / JPA
   ↓
Hibernate
   ↓
JDBC
   ↓
MySQL
```

---

# 17. Banco de Dados

Banco principal:

**MySQL 8**

O banco deverá possuir:

* chaves primárias;
* foreign keys;
* índices;
* constraints;
* valores únicos;
* controle de integridade;
* controle de concorrência quando necessário.

---

# 18. Migrations

As alterações estruturais do banco serão controladas pelo:

**Flyway**

Estrutura:

```text
src/main/resources/db/migration/
```

Exemplo:

```text
V1__criacao_empresa.sql
V2__criacao_departamento.sql
V3__criacao_cargo.sql
V4__criacao_funcionario.sql
```

Migrations já aplicadas não devem ser alteradas arbitrariamente.

---

# 19. Transações

Operações críticas deverão utilizar transações.

Exemplo:

```text
Confirmar Venda
      |
      +── validar venda
      |
      +── validar estoque
      |
      +── atualizar estoque
      |
      +── gerar obrigação
      |
      +── registrar operação
      |
      +── commit
```

Caso uma etapa obrigatória falhe, a operação deverá ser revertida ou tratada por mecanismo de compensação apropriado.

---

# 20. Separação entre fatos comerciais e financeiros

A arquitetura não deve assumir:

```text
Venda = pagamento imediato
```

Em vez disso:

```text
Venda
  ↓
Conta a Receber
  ↓
Parcela
  ↓
Pagamento
  ↓
Movimentação
  ↓
Conta Financeira
```

O mesmo princípio vale para compras:

```text
Compra
  ↓
Conta a Pagar
  ↓
Parcela
  ↓
Pagamento
  ↓
Movimentação
```

Essa separação é fundamental para manter o domínio financeiro correto.

---

# 21. Integração entre módulos

Os módulos podem se comunicar por serviços da aplicação.

Exemplo:

```text
VendaService
     |
     +---- ProdutoService
     |
     +---- EstoqueService
     |
     +---- FinanceiroService
```

O acoplamento deve permanecer controlado.

Uma integração entre módulos deve existir somente quando houver necessidade de negócio.

---

# 22. Estoque

O módulo de estoque deve possuir controle específico para concorrência.

Fluxo:

```text
Solicitação
    ↓
Validar produto
    ↓
Validar estoque
    ↓
Atualizar quantidade
    ↓
Registrar movimentação
```

O registro de estoque utilizará controle de versão para evitar perda de atualização.

Exemplo conceitual:

```java
@Version
private Long version;
```

---

# 23. Concorrência Financeira

O mesmo princípio deverá ser aplicado ao saldo das contas financeiras.

Uma operação não deve simplesmente fazer:

```text
saldo = saldo + valor
```

sem considerar concorrência.

O processo deverá proteger:

* saldo anterior;
* saldo posterior;
* operação concorrente;
* duplicação.

---

# 24. Idempotência

Operações críticas devem possuir mecanismo de idempotência.

Principalmente:

* confirmação de venda;
* confirmação de compra;
* operações de estoque;
* pagamentos;
* recebimentos.

Fluxo:

```text
Request
   ↓
Idempotency Key
   ↓
Já processado?
   ├── SIM → retornar resultado anterior
   └── NÃO
          ↓
       processar
          ↓
       registrar
```

---

# 25. Auditoria

Auditoria será implementada como componente próprio.

Estrutura conceitual:

```text
Operação
   ↓
AuditService
   ↓
Auditoria
   ↓
MySQL
```

A auditoria não deverá armazenar:

* senhas;
* tokens;
* refresh tokens;
* segredos;
* credenciais.

---

# 26. Tratamento Global de Exceções

A aplicação deverá possuir mecanismo centralizado para tratamento de erros HTTP.

Exemplo:

```java
@RestControllerAdvice
```

A camada deve converter exceções internas em respostas apropriadas.

Exemplo:

```text
EntityNotFoundException
        ↓
HTTP 404
```

ou:

```text
BusinessRuleException
        ↓
HTTP 422
```

Os códigos definitivos serão padronizados durante a implementação da API.

---

# 27. Validação

A validação deverá ocorrer em diferentes níveis.

### API

Bean Validation.

### Serviço

Regras de negócio.

### Banco

Constraints e integridade.

Fluxo:

```text
Cliente
 ↓
DTO Validation
 ↓
Business Validation
 ↓
Database Constraints
```

Nenhuma dessas camadas deve ser considerada suficiente isoladamente para todas as regras.

---

# 28. API REST

A API será construída utilizando Spring MVC.

Princípios:

* recursos orientados ao domínio;
* métodos HTTP adequados;
* status HTTP coerentes;
* DTOs;
* paginação;
* filtros;
* autorização;
* documentação OpenAPI.

---

# 29. Paginação

Consultas com potencial de grande volume devem utilizar paginação.

Exemplo conceitual:

```text
GET /funcionarios?page=0&size=20
```

O tamanho máximo da página deverá ser controlado pelo servidor.

O cliente não poderá solicitar quantidade arbitrariamente grande de registros.

---

# 30. Filtros

Filtros deverão ser implementados de maneira controlada.

Exemplo:

```text
GET /funcionarios?status=ATIVO&departamento=TI
```

Somente campos de filtro explicitamente permitidos devem ser processados.

Não deverá existir construção insegura de queries baseada diretamente em entrada do cliente.

---

# 31. Observabilidade

A aplicação utilizará:

* Spring Actuator;
* logs;
* requestId/correlationId;
* métricas;
* health checks.

Endpoints de observabilidade não devem expor informações sensíveis.

---

# 32. Configuração

As configurações específicas do ambiente não devem ficar diretamente no código.

Exemplos:

* URL do banco;
* usuário;
* senha;
* chaves criptográficas;
* segredos;
* configurações externas.

Devem ser utilizadas configurações externas e variáveis de ambiente.

---

# 33. Ambientes

O projeto deverá suportar, no mínimo:

```text
development
test
production
```

Cada ambiente poderá possuir configurações específicas.

---

# 34. Docker

A aplicação possuirá suporte a containerização.

Componentes esperados:

```text
Dockerfile
docker-compose.yml
```

O Compose poderá ser utilizado para fornecer infraestrutura local, principalmente MySQL.

---

# 35. Testes

A estratégia de testes possuirá:

```text
           TESTES
              |
     +--------+--------+
     |        |        |
   Unit    Integration Security
```

### Unitários

JUnit + Mockito.

### Integração

Spring Boot Test + Testcontainers.

### Banco

Testcontainers + MySQL.

### Segurança

Testes específicos de autenticação e autorização.

---

# 36. Dependências entre módulos

Dependências devem ser direcionadas.

Exemplo válido:

```text
Venda
  ↓
Estoque
  ↓
Produto
```

Exemplo problemático:

```text
Venda
  ↓
Estoque
  ↓
Venda
```

O segundo caso deve ser evitado.

---

# 37. Prevenção de dependências circulares

Dependências circulares entre módulos não devem ser introduzidas.

Caso dois módulos necessitem compartilhar uma informação:

* avaliar abstração;
* utilizar serviço apropriado;
* utilizar evento interno quando justificado;
* mover regra para o módulo responsável;
* evitar referência bidirecional desnecessária.

A decisão deve preservar o domínio e reduzir acoplamento.

---

# 38. Regras arquiteturais para integrações

Integrações críticas devem possuir:

* fronteira transacional clara;
* validação;
* autorização;
* idempotência quando aplicável;
* auditoria;
* tratamento de falhas;
* controle de concorrência.

---

# 39. Arquitetura do Financeiro

O módulo financeiro será organizado aproximadamente assim:

```text
financeiro/
├── conta/
├── categoria/
├── receber/
├── pagar/
├── parcela/
├── pagamento/
└── movimentacao/
```

Fluxo:

```text
Conta a Receber
       ↓
     Parcela
       ↓
    Pagamento
       ↓
Movimentação
       ↓
Conta Financeira
```

e:

```text
Conta a Pagar
       ↓
     Parcela
       ↓
    Pagamento
       ↓
Movimentação
       ↓
Conta Financeira
```

---

# 40. Arquitetura do Estoque

```text
estoque/
├── entity/
├── repository/
├── service/
├── controller/
└── dto/
```

Modelo:

```text
Produto
   ↓
Estoque
   ↓
MovimentacaoEstoque
```

O saldo atual é mantido no estoque e o histórico é mantido nas movimentações.

---

# 41. Arquitetura Comercial

```text
venda/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
└── mapper/
```

Estrutura:

```text
Venda
   ↓
ItemVenda
   ↓
Produto
```

O mesmo modelo é utilizado para compras:

```text
Compra
   ↓
ItemCompra
   ↓
Produto
```

---

# 42. Segurança contra exposição de Entity

O seguinte padrão é proibido:

```text
@GetMapping
public Funcionario getFuncionario() {
    return repository.findById(...);
}
```

O padrão esperado é:

```text
Controller
    ↓
Service
    ↓
Entity
    ↓
Mapper
    ↓
DTO
    ↓
Controller
```

Isso reduz exposição acidental de dados.

---

# 43. Segurança contra Mass Assignment

Objetos recebidos da API não devem permitir que o cliente altere campos administrativos sem autorização.

Exemplo:

O cliente não deve conseguir enviar:

```json
{
    "perfil": "ADMINISTRADOR"
}
```

para alterar arbitrariamente seu próprio perfil.

Campos sensíveis devem ser controlados por casos de uso específicos.

---

# 44. Segurança contra BOLA/IDOR

Nunca assumir que possuir um ID significa ter direito ao recurso.

Fluxo:

```text
GET /tarefas/{id}
        ↓
Autenticar usuário
        ↓
Verificar permissão
        ↓
Carregar tarefa
        ↓
Verificar acesso contextual
        ↓
Retornar recurso
```

Essa verificação deve ocorrer no servidor.

---

# 45. Auditoria x Logs

Logs e auditoria possuem funções diferentes.

### Logs

Servem principalmente para diagnóstico e observabilidade.

### Auditoria

Serve para rastrear operações de negócio relevantes.

Portanto, não se deve utilizar logs como substitutos de auditoria.

---

# 46. Shared

O pacote `shared` deve ser pequeno e controlado.

Pode conter componentes realmente compartilhados, como:

* exceções base;
* paginação;
* resposta de erro;
* utilidades claramente genéricas.

Não deve virar um diretório para classes que não possuem local definido.

---

# 47. Dependências Técnicas

Stack prevista:

```text
Java 21

Spring Boot 4.1.1
├── Spring MVC
├── Spring Data JPA
├── Spring Security
├── Validation
├── Actuator
└── Flyway

MySQL 8

Testes
├── JUnit
├── Mockito
└── Testcontainers

Build
└── Maven
```

---

# 48. Princípios Arquiteturais

O projeto seguirá:

## Segurança por padrão

Recursos devem ser protegidos por padrão quando apropriado.

## Menor privilégio

Usuários devem receber somente os acessos necessários.

## Separação de responsabilidades

Cada camada possui responsabilidade específica.

## Baixo acoplamento

Módulos devem depender somente do necessário.

## Alta coesão

Responsabilidades relacionadas devem permanecer juntas.

## Integridade

Regras importantes devem ser protegidas pela aplicação e pelo banco.

## Evolução incremental

Novos recursos devem ser adicionados sem comprometer o núcleo existente.

## Simplicidade

Tecnologias adicionais somente devem ser introduzidas quando resolverem problemas reais.

---

# 49. Evolução para Microservices

A arquitetura inicial não utiliza microservices.

Uma eventual migração deverá considerar fatores como:

* necessidade de escala independente;
* limites de domínio bem definidos;
* carga desigual entre módulos;
* necessidade de deploy independente;
* requisitos de disponibilidade;
* autonomia de equipes;
* complexidade operacional.

A simples existência de vários módulos não é motivo suficiente para dividir o sistema em microservices.

---

# 50. Estado da Arquitetura

**Documento:** Arquitetura
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Estado:** Arquitetura inicial consolidada

A arquitetura deverá ser atualizada sempre que uma decisão estrutural importante modificar a forma de construção ou integração dos módulos.
