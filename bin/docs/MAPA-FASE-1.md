# MAPA DA FASE 1

## Sistema Interno para Empresas

**Versão:** 1.0
**Fase:** MVP / Fase 1
**Arquitetura:** Monólito Modular
**Linguagem:** Java 21
**Framework:** Spring Boot 4.1.1
**Banco:** MySQL 8

---

# 1. Objetivo

Este documento apresenta o mapa estrutural do projeto durante a **Fase 1 — MVP**.

O objetivo é definir:

* estrutura de diretórios;
* documentos;
* módulos;
* camadas;
* classes previstas;
* configurações;
* migrations;
* testes;
* arquivos de infraestrutura.

Este documento funciona como uma **planta do projeto**.

Ele não substitui a implementação e não significa que todos os arquivos previstos devem ser criados imediatamente.

---

# 2. Estrutura Atual do Repositório

A estrutura atualmente existente é semelhante a:

```text
Sistema-Interno-para-empresas/
│
├── .idea/
├── .mvn/
│
├── docs/
│   ├── ARQUITETURA.md
│   ├── BACKLOG.md
│   ├── MODELO-DE-DADOS.md
│   ├── REGRAS-DE-NEGOCIO.md
│   ├── REQUISITOS.md
│   └── SEGURANCA.md
│
├── src/
│
├── .gitattributes
├── .gitignore
├── HELP.md
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

A estrutura deverá crescer de forma incremental durante as Sprints.

---

# 3. Estrutura Final Esperada da Fase 1

```text
Sistema-Interno-para-empresas/
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── .idea/
│
├── .mvn/
│
├── docs/
│   ├── ARQUITETURA.md
│   ├── BACKLOG.md
│   ├── MODELO-DE-DADOS.md
│   ├── MAPA-FASE-1.md
│   ├── REGRAS-DE-NEGOCIO.md
│   ├── REQUISITOS.md
│   ├── SEGURANCA.md
│   ├── API.md
│   └── TESTES.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── br/
│   │   │           └── sistema/
│   │   │               │
│   │   │               ├── SistemaInternoApplication.java
│   │   │               │
│   │   │               ├── empresa/
│   │   │               ├── departamento/
│   │   │               ├── cargo/
│   │   │               ├── funcionario/
│   │   │               ├── usuario/
│   │   │               ├── perfil/
│   │   │               ├── permissao/
│   │   │               ├── projeto/
│   │   │               ├── tarefa/
│   │   │               ├── reuniao/
│   │   │               ├── cliente/
│   │   │               ├── fornecedor/
│   │   │               ├── endereco/
│   │   │               ├── categoria/
│   │   │               ├── produto/
│   │   │               ├── estoque/
│   │   │               ├── venda/
│   │   │               ├── compra/
│   │   │               ├── financeiro/
│   │   │               ├── auditoria/
│   │   │               ├── relatorio/
│   │   │               │
│   │   │               ├── security/
│   │   │               └── shared/
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-test.yml
│   │       │
│   │       └── db/
│   │           └── migration/
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── br/
│                   └── sistema/
│
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── LICENSE
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# 4. Organização dos Módulos

Os módulos representam responsabilidades de negócio.

## Empresa

```text
empresa/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* dados da empresa;
* status;
* informações cadastrais.

---

# 5. Departamento

```text
departamento/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* departamentos da empresa;
* status;
* organização interna.

---

# 6. Cargo

```text
cargo/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* cargos profissionais;
* descrição;
* status.

Cargo não controla permissões.

---

# 7. Funcionário

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

Arquivos previstos:

```text
Funcionario.java
FuncionarioRepository.java
FuncionarioService.java

FuncionarioCreateRequest.java
FuncionarioUpdateRequest.java
FuncionarioResponse.java

FuncionarioMapper.java
```

Responsabilidade:

* cadastro;
* situação funcional;
* departamento;
* cargo;
* vínculo com usuário.

---

# 8. Usuário

```text
usuario/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Arquivos previstos:

```text
Usuario.java
UsuarioRepository.java
UsuarioService.java

UsuarioCreateRequest.java
UsuarioUpdateRequest.java
UsuarioResponse.java

UsuarioMapper.java
```

Responsabilidade:

* conta;
* credenciais;
* status;
* último login.

---

# 9. Perfil

```text
perfil/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* perfis;
* associação de permissões;
* status.

---

# 10. Permissão

```text
permissao/
├── entity/
├── repository/
├── service/
└── exception/
```

Responsabilidade:

* módulos;
* ações;
* permissões disponíveis.

Não necessariamente será necessário expor CRUD completo de permissões pela API.

---

# 11. Projeto

```text
projeto/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* projetos;
* responsáveis;
* datas;
* prioridade;
* status;
* progresso derivado.

---

# 12. Tarefa

```text
tarefa/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Pode possuir internamente:

```text
tarefa/
├── historico/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
└── mapper/
```

Responsabilidade:

* atividades;
* responsáveis;
* prazo;
* prioridade;
* status;
* histórico.

---

# 13. Reunião

```text
reuniao/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
├── participante/
└── exception/
```

Responsabilidade:

* reuniões;
* organizador;
* participantes;
* data;
* local;
* projeto relacionado.

---

# 14. Cliente

```text
cliente/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* cadastro de clientes;
* documentos;
* contatos;
* status;
* histórico comercial.

---

# 15. Fornecedor

```text
fornecedor/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* cadastro de fornecedores;
* documentos;
* contatos;
* status;
* histórico de compras.

---

# 16. Endereço

O módulo de endereço poderá possuir estrutura própria:

```text
endereco/
├── entity/
├── repository/
├── service/
└── dto/
```

Associações:

```text
funcionario_endereco
cliente_endereco
fornecedor_endereco
```

Não será utilizado relacionamento polimórfico baseado em:

```text
tipo_entidade
entidade_id
```

---

# 17. Categoria

```text
categoria/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* categorias de produtos;
* status.

---

# 18. Produto

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

Responsabilidade:

* produtos;
* serviços;
* SKU;
* código de barras;
* preços;
* custo;
* unidade de medida;
* categoria.

---

# 19. Estoque

Como estoque possui regras mais complexas:

```text
estoque/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Responsabilidade:

* saldo;
* estoque mínimo;
* estoque máximo;
* movimentações;
* concorrência;
* idempotência.

Arquivos esperados:

```text
Estoque.java
EstoqueRepository.java
EstoqueService.java

MovimentacaoEstoque.java
MovimentacaoEstoqueRepository.java
MovimentacaoEstoqueService.java
```

---

# 20. Venda

```text
venda/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Estrutura:

```text
Venda
└── ItemVenda
```

Possíveis classes:

```text
Venda.java
ItemVenda.java

VendaRepository.java
ItemVendaRepository.java

VendaService.java

VendaCreateRequest.java
VendaUpdateRequest.java
VendaResponse.java

VendaMapper.java
```

---

# 21. Compra

```text
compra/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── mapper/
└── exception/
```

Estrutura:

```text
Compra
└── ItemCompra
```

---

# 22. Financeiro

Financeiro é o módulo mais complexo da Fase 1.

Estrutura prevista:

```text
financeiro/
│
├── conta/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│
├── categoria/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│
├── receber/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│
├── pagar/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│
├── parcela/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│
├── pagamento/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│
└── movimentacao/
    ├── controller/
    ├── dto/
    ├── entity/
    ├── repository/
    └── service/
```

Fluxo:

```text
Conta a Receber / Conta a Pagar
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

# 23. Auditoria

```text
auditoria/
├── controller/
├── dto/
├── entity/
├── repository/
└── service/
```

Responsabilidade:

* registrar operações;
* armazenar contexto;
* consultar histórico;
* manter rastreabilidade.

---

# 24. Relatórios

```text
relatorio/
├── controller/
├── dto/
├── service/
└── exception/
```

Responsabilidade:

* indicadores;
* consultas agregadas;
* relatórios.

Relatórios não devem possuir responsabilidade de alterar o domínio.

---

# 25. Security

O módulo de segurança possui natureza técnica.

```text
security/
├── config/
├── filter/
├── jwt/
├── principal/
├── authorization/
└── service/
```

Responsabilidades:

### config

Configurações do Spring Security.

### filter

Processamento de requisições autenticadas.

### jwt

Emissão e validação de tokens.

### principal

Representação do usuário autenticado.

### authorization

Autorização funcional e contextual.

### service

Serviços auxiliares relacionados à segurança.

---

# 26. Shared

```text
shared/
```

Deve permanecer pequeno.

Possíveis componentes:

```text
shared/
├── exception/
├── dto/
├── pagination/
└── util/
```

Somente componentes realmente compartilhados devem ficar aqui.

---

# 27. Resources

Estrutura:

```text
src/main/resources/
│
├── application.yml
├── application-dev.yml
├── application-test.yml
│
└── db/
    └── migration/
```

---

# 28. Configuração da Aplicação

## application.yml

Configuração comum da aplicação.

Deve conter somente configurações que podem ser comuns entre ambientes.

---

## application-dev.yml

Configurações específicas do ambiente de desenvolvimento.

Exemplos:

* banco local;
* logs;
* configurações de desenvolvimento.

---

## application-test.yml

Configurações específicas para testes.

Quando Testcontainers for utilizado, configurações dinâmicas do banco podem ser fornecidas pelo ambiente de teste.

---

# 29. Migrations

Estrutura:

```text
src/main/resources/db/migration/
```

Exemplo:

```text
V1__criacao_empresa.sql
V2__criacao_estrutura_organizacional.sql
V3__criacao_usuario_perfil_permissao.sql
V4__criacao_clientes_fornecedores.sql
V5__criacao_produtos_estoque.sql
V6__criacao_vendas_compras.sql
V7__criacao_financeiro.sql
V8__criacao_auditoria.sql
```

A sequência definitiva será definida conforme a implementação real.

---

# 30. Testes

Estrutura:

```text
src/test/java/com/br/sistema/
```

Deve acompanhar a organização dos módulos.

Exemplo:

```text
funcionario/
├── service/
├── repository/
└── controller/

produto/
├── service/
├── repository/
└── controller/

estoque/
├── service/
├── repository/
└── controller/

venda/
├── service/
└── controller/

financeiro/
├── service/
└── controller/

security/
└── ...
```

---

# 31. Testcontainers

Testes que dependam do banco devem utilizar:

```text
MySQL
```

via Testcontainers.

Não utilizar PostgreSQL nos testes, pois o banco oficial do projeto é MySQL.

---

# 32. Arquivos de Infraestrutura

## pom.xml

Dependências e configuração Maven.

## Dockerfile

Container da aplicação.

## docker-compose.yml

Ambiente local, principalmente:

```text
Aplicação
MySQL
```

quando necessário.

## .env.example

Exemplo das variáveis esperadas.

Nunca conter credenciais reais.

---

# 33. CI/CD

Arquivo previsto:

```text
.github/
└── workflows/
    └── ci.yml
```

Pipeline mínimo:

```text
Push
 ↓
Build
 ↓
Testes
 ↓
Análise
 ↓
Resultado
```

---

# 34. Documentação

A documentação da Fase 1 ficará:

```text
docs/
│
├── ARQUITETURA.md
├── BACKLOG.md
├── MAPA-FASE-1.md
├── MODELO-DE-DADOS.md
├── REGRAS-DE-NEGOCIO.md
├── REQUISITOS.md
├── SEGURANCA.md
├── API.md
└── TESTES.md
```

---

# 35. Ordem de Criação

Os arquivos serão criados conforme a necessidade das Sprints.

## S01

Criar:

```text
SistemaInternoApplication.java

empresa/
```

e infraestrutura inicial.

## S02

Criar:

```text
security/
usuario/
```

conforme o fluxo de autenticação definido.

## S03

Criar:

```text
perfil/
permissao/
```

## S04

Completar:

```text
funcionario/
departamento/
cargo/
usuario/
perfil/
security/
```

## S05

Criar:

```text
projeto/
tarefa/
```

## S06

Criar:

```text
reuniao/
cliente/
fornecedor/
endereco/
```

## S07

Criar:

```text
categoria/
produto/
estoque/
```

## S08

Criar:

```text
venda/
```

## S09

Criar:

```text
compra/
```

## S10

Criar:

```text
financeiro/conta/
financeiro/categoria/
```

## S11

Completar:

```text
financeiro/receber/
financeiro/pagar/
financeiro/parcela/
```

## S12

Completar:

```text
financeiro/pagamento/
financeiro/movimentacao/
```

e integrações.

## S13

Criar:

```text
auditoria/
relatorio/
```

e completar infraestrutura de consultas.

---

# 36. Arquivos que NÃO devem ser criados antecipadamente

O mapa não significa que todos os arquivos abaixo devem existir desde o primeiro commit.

Não criar antecipadamente:

```text
MarketingService.java
Campanha.java
CRMService.java
Lead.java
AIService.java
NotificationService.java
DocumentoService.java
IntegracaoExternaService.java
```

Esses componentes pertencem a fases posteriores.

---

# 37. Regra de Criação Incremental

A criação de um arquivo deve possuir pelo menos uma justificativa:

* requisito;
* regra de negócio;
* história do backlog;
* tarefa técnica;
* necessidade arquitetural.

Não criar classes vazias somente para "preencher" a estrutura.

---

# 38. Relação com o Backlog

O mapa estrutural está diretamente relacionado ao Backlog.

Exemplo:

```text
US-036
Cadastrar funcionário
        ↓
funcionario/
        ↓
Funcionario.java
FuncionarioRepository.java
FuncionarioService.java
FuncionarioController.java
DTOs
Mapper
Testes
```

Outro exemplo:

```text
US-159
Registrar pagamento
        ↓
financeiro/pagamento/
        ↓
Pagamento.java
PagamentoRepository.java
PagamentoService.java
PagamentoController.java
DTOs
Testes
```

---

# 39. Regra de Arquitetura

Nenhum módulo deve acessar diretamente o banco sem passar pela abstração de persistência definida.

Fluxo esperado:

```text
Controller
    ↓
Service / Use Case
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

---

# 40. Regra de Dependência

As dependências entre módulos devem permanecer controladas.

Exemplo:

```text
venda
  ↓
estoque
```

é aceitável quando existe necessidade de negócio.

Porém:

```text
venda
  ↓
estoque
  ↓
venda
```

deve ser evitado.

Integrações circulares devem ser analisadas antes da implementação.

---

# 41. Regra para DTOs

Cada endpoint deve utilizar DTOs apropriados.

Exemplo:

```text
ProdutoCreateRequest
ProdutoUpdateRequest
ProdutoResponse
```

Não utilizar:

```text
Produto
```

diretamente como contrato HTTP.

---

# 42. Regra para Entities

Entities pertencem ao domínio/persistência.

Não devem ser expostas diretamente pela API.

Exemplo:

```text
Produto
```

não deve ser retornado diretamente pelo Controller.

---

# 43. Regra para Services

Services devem concentrar:

* regras de negócio;
* orquestração;
* transações;
* autorização contextual;
* operações entre módulos.

Não utilizar Controller como camada de negócio.

---

# 44. Regra para Repository

Repositories são responsáveis pela persistência.

Não devem concentrar regras complexas do domínio.

Consultas específicas podem existir no Repository quando forem parte da necessidade de persistência.

---

# 45. Regra para Auditoria

A auditoria não deve ser espalhada de maneira inconsistente em todos os Controllers.

Deve existir mecanismo centralizado, como:

```text
AuditService
```

para padronizar os registros.

---

# 46. Regra para Segurança

A segurança transversal deve permanecer em:

```text
security/
```

Não criar implementações independentes de autenticação dentro de cada módulo.

---

# 47. Regra para Financeiro

O financeiro deve permanecer modular internamente.

Evitar criar uma única classe:

```text
FinanceiroService.java
```

com toda a lógica financeira.

A divisão deve respeitar:

```text
conta
categoria
receber
pagar
parcela
pagamento
movimentacao
```

---

# 48. Regra para Estoque

A mesma lógica vale para estoque.

Evitar colocar toda a lógica em um Controller.

O domínio deve manter:

```text
Estoque
MovimentacaoEstoque
EstoqueService
MovimentacaoEstoqueService
```

com responsabilidades claras.

---

# 49. Regra de Testes

Cada módulo deve possuir testes proporcionais à sua complexidade.

Operações críticas exigem testes específicos.

Especial atenção:

```text
estoque
venda
compra
pagamento
movimentacao_financeira
security
```

---

# 50. Estado do Mapa

**Documento:** Mapa da Fase 1
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Banco:** MySQL 8
**Arquitetura:** Monólito Modular

Este documento representa a estrutura planejada do projeto.

A árvore será atualizada conforme os arquivos forem efetivamente criados e novas decisões arquiteturais forem tomadas.
