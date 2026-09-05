# Sistema Interno para Empresas

Sistema interno de gestão empresarial desenvolvido em **Java 21** com **Spring Boot 4.1.1**, destinado à centralização de processos administrativos, operacionais, comerciais e financeiros de uma empresa.

O projeto foi planejado como um **monólito modular**, com foco em organização de domínio, segurança, integridade de dados, rastreabilidade e possibilidade de evolução futura.

---

## Sobre o projeto

Empresas podem manter informações e processos distribuídos em planilhas, calendários, sistemas independentes e outros mecanismos. Isso dificulta a centralização dos dados, o acompanhamento das operações e a obtenção de informações confiáveis para tomada de decisão.

Este sistema busca centralizar essas operações em uma única aplicação.

Entre os principais recursos previstos estão:

* gestão da empresa;
* funcionários, departamentos e cargos;
* usuários, perfis e permissões;
* projetos e tarefas;
* reuniões;
* clientes e fornecedores;
* produtos e serviços;
* estoque;
* vendas e compras;
* contas a receber e contas a pagar;
* parcelas;
* pagamentos e recebimentos;
* movimentações financeiras;
* auditoria;
* consultas;
* relatórios;
* indicadores básicos.

---

## Objetivo

Desenvolver um sistema interno de gestão empresarial capaz de centralizar, integrar e disponibilizar informações relacionadas às operações administrativas, financeiras, comerciais e operacionais de uma empresa, proporcionando maior controle dos processos, rastreabilidade das atividades e suporte à tomada de decisões baseada em dados.

---

## Status do projeto

**Status:** Em desenvolvimento
**Versão:** `0.0.1-SNAPSHOT`
**Fase atual:** Fundação do projeto / MVP

O desenvolvimento seguirá um backlog estruturado em Sprints.

Consulte:

* [`BACKLOG.md`](BACKLOG.md)
* [`REQUISITOS.md`](REQUISITOS.md)
* [`REGRAS-DE-NEGOCIO.md`](REGRAS-DE-NEGOCIO.md)

---

# Arquitetura

O sistema utiliza inicialmente uma arquitetura de **Monólito Modular**.

A aplicação será executada como um único sistema, porém organizada em módulos de domínio independentes e com responsabilidades bem definidas.

Fluxo principal da aplicação:

```text
Cliente
   ↓
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

A autenticação e autorização são tratadas pela camada de segurança:

```text
Cliente
   ↓
Spring Security
   ↓
Autenticação
   ↓
Autorização
   ↓
Controller
```

Mais detalhes:

* [`ARQUITETURA.md`](ARQUITETURA.md)
* [`MODELO-DE-DADOS.md`](MODELO-DE-DADOS.md)
* [`SEGURANCA.md`](SEGURANCA.md)

---

# Tecnologias

## Backend

* Java 21
* Spring Boot 4.1.1
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* Bean Validation
* Spring Actuator

## Banco de dados

* MySQL 8
* MySQL Connector/J
* Flyway

## Testes

* JUnit
* Mockito
* Spring Boot Test
* Testcontainers
* Testcontainers MySQL

## Build e desenvolvimento

* Maven
* Docker
* Git

## Documentação de API

* OpenAPI / Swagger

---

# Principais módulos

A estrutura de domínio prevista inicialmente é:

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

Os módulos poderão possuir subdivisões internas quando a complexidade do domínio justificar.

---

# Funcionalidades do MVP

## Segurança

* autenticação;
* usuários;
* perfis;
* permissões;
* autorização funcional;
* autorização contextual;
* auditoria.

## Organização empresarial

* empresa;
* departamentos;
* cargos;
* funcionários;
* endereços.

## Gestão operacional

* projetos;
* tarefas;
* histórico de tarefas;
* reuniões;
* participantes.

## Gestão comercial

* clientes;
* fornecedores;
* categorias;
* produtos;
* serviços;
* vendas;
* compras.

## Estoque

* controle de estoque;
* entradas;
* saídas;
* ajustes;
* histórico de movimentações.

## Financeiro

* contas financeiras;
* categorias financeiras;
* contas a receber;
* contas a pagar;
* parcelas;
* pagamentos;
* recebimentos;
* movimentações financeiras.

## Gestão e análise

* consultas;
* filtros;
* paginação;
* relatórios;
* indicadores básicos.

---

# Regras arquiteturais importantes

O projeto seguirá alguns princípios desde o início:

### Controller

Responsável pela camada HTTP e pela comunicação com os serviços.

Não deve concentrar regras de negócio.

### Service / Use Case

Responsável pela orquestração e execução das regras de negócio.

### Repository

Responsável pela abstração de persistência.

Não será utilizado um DAO tradicional separado apenas para reproduzir o papel do Repository.

### Entity

Representa o modelo persistido.

### DTO

Representa dados de entrada e saída da API.

Entities não devem ser expostas diretamente pela API.

---

# Banco de dados

O banco utilizado pelo projeto é:

```text
MySQL 8
```

As alterações estruturais do banco serão controladas por migrations utilizando **Flyway**.

A aplicação não deve depender da criação manual das tabelas.

Fluxo esperado:

```text
Aplicação
   ↓
Flyway
   ↓
Migrations
   ↓
MySQL
```

---

# Configuração do ambiente

## Pré-requisitos

Antes de executar o projeto, é necessário possuir:

* JDK 21;
* Maven;
* MySQL 8;
* Git.

Docker é recomendado para facilitar a configuração do ambiente de desenvolvimento e dos testes.

---

## Configuração do banco

Crie um banco de dados para a aplicação no MySQL.

Exemplo:

```sql
CREATE DATABASE sistema_interno_empresas;
```

Depois configure as propriedades da aplicação de acordo com o ambiente.

Exemplo conceitual:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_interno_empresas
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

As credenciais reais não devem ser versionadas no repositório.

---

# Execução

Clone o projeto:

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre no diretório:

```bash
cd Sistema-Interno-para-empresas
```

Execute os testes:

```bash
mvn clean test
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

Também é possível executar a aplicação pela IDE utilizando a classe principal do Spring Boot.

---

# Migrations

As migrations do banco deverão seguir a convenção do Flyway.

Exemplo:

```text
src/
└── main/
    └── resources/
        └── db/
            └── migration/
                ├── V1__criacao_inicial.sql
                ├── V2__criacao_departamento.sql
                └── V3__criacao_funcionario.sql
```

A numeração e a ordem das migrations devem ser preservadas.

Uma migration aplicada em um ambiente compartilhado não deve ser editada posteriormente para alterar seu histórico.

---

# Testes

O projeto utilizará diferentes níveis de testes.

## Testes unitários

Utilizados principalmente para:

* regras de negócio;
* validações;
* serviços;
* cálculos;
* transições de estado.

## Testes de integração

Utilizados para validar:

* persistência;
* banco de dados;
* transações;
* segurança;
* endpoints;
* integração entre componentes.

## Testcontainers

Os testes de integração que dependem do banco utilizarão containers de **MySQL**, reduzindo diferenças entre o ambiente de testes e o banco utilizado pela aplicação.

Executar todos os testes:

```bash
mvn test
```

---

# Segurança

A segurança é um requisito estrutural do projeto.

Entre os controles previstos estão:

* autenticação;
* autorização baseada em permissões;
* autorização contextual;
* validação de entrada;
* DTOs;
* proteção contra acesso indevido a recursos;
* controle de privilégios;
* rate limiting quando aplicável;
* auditoria;
* tratamento seguro de erros;
* proteção de dados sensíveis;
* controle de concorrência;
* idempotência em operações críticas.

As decisões de segurança serão detalhadas em:

[`SEGURANCA.md`](SEGURANCA.md)

---

# Operações críticas

Algumas operações exigem controles adicionais.

### Estoque

```text
Entrada
Saída
Ajuste
```

Deve existir:

* controle de concorrência;
* histórico;
* validação;
* idempotência;
* integridade transacional.

### Financeiro

```text
Venda/Compra
      ↓
Obrigação
      ↓
Parcela
      ↓
Pagamento
      ↓
Movimentação
```

Pagamentos e movimentações financeiras devem possuir controles contra:

* duplicação;
* inconsistência;
* concorrência;
* alteração indevida de histórico.

---

# Roadmap

O MVP está dividido em Sprints de duas semanas.

| Sprint | Objetivo                                           |
| ------ | -------------------------------------------------- |
| S01    | Fundação técnica + Empresa                         |
| S02    | Autenticação                                       |
| S03    | Perfis + Permissões                                |
| S04    | Estrutura Organizacional + Funcionários + Usuários |
| S05    | Projetos + Tarefas                                 |
| S06    | Reuniões + Clientes + Fornecedores                 |
| S07    | Categorias + Produtos + Estoque                    |
| S08    | Vendas                                             |
| S09    | Compras                                            |
| S10    | Contas Financeiras + Categorias Financeiras        |
| S11    | Contas a Receber + Contas a Pagar + Parcelas       |
| S12    | Pagamentos + Movimentações + Integrações           |
| S13    | Auditoria + Consultas + Relatórios                 |

O detalhamento está disponível em:

[`BACKLOG.md`](BACKLOG.md)

---

# Pós-MVP

Os seguintes recursos estão planejados para fases posteriores:

* marketing;
* campanhas;
* relacionamento cliente/campanha;
* analytics avançado;
* CRM;
* leads;
* notificações;
* documentos;
* RH avançado;
* integrações externas;
* automações;
* inteligência artificial.

A adoção de uma arquitetura de microservices também não faz parte do MVP. Essa decisão poderá ser reavaliada futuramente caso exista necessidade técnica ou de negócio.

---

# Estrutura da documentação

A documentação do projeto será organizada da seguinte maneira:

```text
Docs
├── REQUISITOS.md
├── REGRAS-DE-NEGOCIO.md
├── ARQUITETURA.md
├── SEGURANCA.md
├── MODELO-DE-DADOS.md
└── BACKLOG.md
```

### README.md

Visão geral e entrada principal do projeto.

### REQUISITOS.md

Requisitos funcionais e não funcionais.

### REGRAS-DE-NEGOCIO.md

Comportamentos e restrições do domínio.

### ARQUITETURA.md

Estrutura técnica e decisões arquiteturais.

### SEGURANCA.md

Modelo de segurança, ameaças e controles.

### MODELO-DE-DADOS.md

Entidades, atributos, relacionamentos e regras de integridade.

### BACKLOG.md

Histórias de usuário, prioridades, dependências, Sprints e tarefas técnicas.

---

# Princípios do projeto

O desenvolvimento seguirá:

* separação de responsabilidades;
* baixo acoplamento;
* alta coesão;
* segurança por padrão;
* validação no servidor;
* integridade no banco;
* rastreabilidade;
* testes automatizados;
* evolução incremental;
* documentação contínua.

---

# Licença

A licença do projeto ainda não foi definida.
