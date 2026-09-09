# BACKLOG

## Sistema Interno para Empresas

**Versão:** 1.3
**Escopo:** MVP / Fase 1
**Arquitetura:** Monólito Modular
**Metodologia:** Sprints de 2 semanas
**Total de histórias:** 200
**Sprints planejadas:** 13

---

# 1. Objetivo

Este documento representa o backlog de desenvolvimento do Sistema Interno para Empresas.

O backlog define:

* histórias de usuário;
* prioridades;
* dependências;
* Sprint planejada;
* tarefas técnicas relacionadas;
* critérios de aceitação;
* Definition of Ready;
* Definition of Done.

O backlog deve ser utilizado como instrumento de planejamento e execução do desenvolvimento.

---

# 2. Prioridades

| Código | Significado |
| ------ | ----------- |
| **P0** | Crítica     |
| **P1** | Alta        |
| **P2** | Média       |
| **P3** | Futura      |

### P0

Funcionalidade necessária para segurança, integridade ou funcionamento essencial do MVP.

### P1

Funcionalidade importante para completar o MVP.

### P2

Funcionalidade relevante, mas que pode ser desenvolvida depois do núcleo principal.

### P3

Funcionalidade planejada para evolução futura.

---

# 3. Status

Estados possíveis de uma história:

```text
BACKLOG
READY
IN_PROGRESS
IN_REVIEW
BLOCKED
DONE
```

Todas as histórias iniciam em:

```text
BACKLOG
```

---

# 4. Regra de Dependência

Uma dependência significa:

> A história dependente precisa que a história referenciada esteja concluída ou disponível para que sua implementação funcional possa ser concluída.

Dependências devem apontar para histórias ou tarefas técnicas específicas.

Não devem ser utilizados como dependência:

* nome de Sprint;
* nome genérico de épico;
* "módulo do MVP";
* requisito genérico;
* referência circular.

Dependências técnicas podem utilizar `TEC-XXX`.

---

# 5. Roadmap

| Sprint  | Objetivo                                 |
| ------- | ---------------------------------------- |
| **S01** | Fundação + Empresa                       |
| **S02** | Autenticação                             |
| **S03** | Perfis + Permissões                      |
| **S04** | Organização + Usuários + Autorização     |
| **S05** | Projetos + Tarefas                       |
| **S06** | Reuniões + Clientes + Fornecedores       |
| **S07** | Categorias + Produtos + Estoque          |
| **S08** | Vendas                                   |
| **S09** | Compras                                  |
| **S10** | Contas Financeiras                       |
| **S11** | Obrigações + Parcelas                    |
| **S12** | Pagamentos + Movimentações + Integrações |
| **S13** | Auditoria + Consultas + Relatórios       |

---

# 6. EP-01 — Empresa

## US-001 — Cadastrar empresa

**Prioridade:** P0
**Sprint:** S01
**Dependências:** —

Como administrador, quero cadastrar a empresa para configurar o sistema.

**Tarefas técnicas:**

* criar Entity;
* criar migration;
* criar DTOs;
* criar Repository;
* criar Service;
* criar endpoint;
* validar CNPJ;
* criar testes.

---

## US-002 — Visualizar empresa

**Prioridade:** P0
**Sprint:** S01
**Dependências:** US-001

Como administrador, quero visualizar os dados da empresa para conferi-los.

---

## US-003 — Editar empresa

**Prioridade:** P1
**Sprint:** S01
**Dependências:** US-001

Como administrador, quero editar os dados da empresa para mantê-los atualizados.

---

## US-004 — Ativar ou inativar empresa

**Prioridade:** P0
**Sprint:** S01
**Dependências:** US-001

Como administrador, quero ativar ou inativar a empresa para controlar seu funcionamento.

---

# 7. EP-02 — Autenticação e Usuários

## US-005 — Login

**Prioridade:** P0
**Sprint:** S02
**Dependências:** US-001

Como usuário, quero realizar login para acessar o sistema.

**Tarefas técnicas:**

* configurar Spring Security;
* configurar AuthenticationManager;
* implementar PasswordEncoder;
* implementar JWT;
* implementar filtro;
* validar credenciais;
* criar testes de autenticação.

---

## US-006 — Encerrar sessão

**Prioridade:** P1
**Sprint:** S02
**Dependências:** US-005

Como usuário, quero encerrar minha sessão para impedir utilização posterior da autenticação.

---

## US-007 — Alterar senha

**Prioridade:** P0
**Sprint:** S02
**Dependências:** US-005

Como usuário, quero alterar minha senha para manter minha conta segura.

---

## US-008 — Bloquear usuário

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-010

Como administrador, quero bloquear um usuário para impedir seu acesso.

---

## US-009 — Desbloquear usuário

**Prioridade:** P1
**Sprint:** S04
**Dependências:** US-008

Como administrador, quero desbloquear um usuário para permitir seu acesso novamente.

---

## US-010 — Criar usuário

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-036

Como administrador, quero criar uma conta de usuário vinculada a um funcionário.

**Tarefas técnicas:**

* relacionamento 1:0..1;
* unicidade do funcionário;
* hash de senha;
* validação de e-mail;
* testes.

---

## US-011 — Consultar usuários

**Prioridade:** P1
**Sprint:** S04
**Dependências:** US-010

Como administrador, quero consultar usuários para administrar os acessos.

---

## US-012 — Editar usuário

**Prioridade:** P1
**Sprint:** S04
**Dependências:** US-010

Como administrador, quero editar dados de um usuário para mantê-los corretos.

---

## US-013 — Inativar usuário

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-010

Como administrador, quero inativar um usuário para retirar seu acesso sem apagar seu histórico.

---

## US-014 — Consultar último acesso

**Prioridade:** P2
**Sprint:** S02
**Dependências:** US-005

Como administrador, quero consultar o último acesso de um usuário.

---

## US-015 — Recuperar acesso

**Prioridade:** P1
**Sprint:** S02
**Dependências:** US-005

Como usuário, quero recuperar o acesso à minha conta conforme a política de segurança.

**Observação:** a implementação definitiva do mecanismo de recuperação deverá ser definida antes do desenvolvimento.

---

# 8. EP-03 — Perfis, Permissões e Controle de Acesso

## US-016 — Criar perfil

**Prioridade:** P0
**Sprint:** S03
**Dependências:** —

Como administrador, quero criar perfis para organizar níveis de acesso.

---

## US-017 — Visualizar perfis

**Prioridade:** P1
**Sprint:** S03
**Dependências:** US-016

Como administrador, quero visualizar perfis.

---

## US-018 — Editar perfil

**Prioridade:** P1
**Sprint:** S03
**Dependências:** US-016

Como administrador, quero editar perfis.

---

## US-019 — Inativar perfil

**Prioridade:** P0
**Sprint:** S03
**Dependências:** US-016

Como administrador, quero inativar perfis.

---

## US-020 — Criar permissão

**Prioridade:** P0
**Sprint:** S03
**Dependências:** —

Como administrador, quero criar permissões para representar ações do sistema.

---

## US-021 — Consultar permissões

**Prioridade:** P1
**Sprint:** S03
**Dependências:** US-020

Como administrador, quero consultar permissões disponíveis.

---

## US-022 — Associar permissões a perfil

**Prioridade:** P0
**Sprint:** S03
**Dependências:** US-016, US-020

Como administrador, quero associar permissões aos perfis.

---

## US-023 — Associar perfil a usuário

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-010, US-016

Como administrador, quero associar perfis a usuários.

---

## US-024 — Remover perfil de usuário

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-023

Como administrador, quero remover um perfil de um usuário.

---

## US-025 — Validar autorização funcional

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-022, US-023

Como sistema, quero verificar se o usuário possui a permissão funcional necessária.

**Tarefas técnicas:**

* AuthorizationService;
* method security;
* PermissionEvaluator quando aplicável;
* testes de autorização.

---

## US-026 — Validar autorização contextual

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-025

Como sistema, quero verificar se o usuário pode acessar o recurso solicitado.

---

## US-027 — Consultar perfis do usuário

**Prioridade:** P1
**Sprint:** S04
**Dependências:** US-023

Como administrador, quero consultar os perfis de um usuário.

---

## US-028 — Consultar permissões do perfil

**Prioridade:** P1
**Sprint:** S03
**Dependências:** US-022

Como administrador, quero consultar as permissões de um perfil.

---

## US-029 — Proteger último administrador

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-023

Como administrador, quero impedir que o sistema fique sem administrador válido.

---

## US-030 — Impedir escalonamento de privilégios

**Prioridade:** P0
**Sprint:** S04
**Dependências:** US-025

Como sistema, quero impedir que um usuário conceda privilégios superiores aos permitidos.

---

# 9. EP-04 — Estrutura Organizacional

## US-031 — Criar departamento

**P1 | S04 | Dependência:** US-001

Como administrador, quero cadastrar departamentos.

---

## US-032 — Visualizar departamentos

**P1 | S04 | Dependência:** US-031

Como administrador, quero visualizar departamentos.

---

## US-033 — Editar departamento

**P1 | S04 | Dependência:** US-031

Como administrador, quero editar departamentos.

---

## US-034 — Inativar departamento

**P1 | S04 | Dependência:** US-031

Como administrador, quero inativar departamentos.

---

## US-035 — Criar cargo

**P1 | S04 | Dependência:** US-001

Como administrador, quero cadastrar cargos.

---

## US-036 — Criar funcionário

**P0 | S04 | Dependências:** US-031, US-035

Como administrador, quero cadastrar funcionários.

---

## US-037 — Visualizar funcionários

**P0 | S04 | Dependência:** US-036

Como administrador, quero visualizar funcionários.

---

## US-038 — Editar funcionário

**P1 | S04 | Dependência:** US-036

Como administrador, quero editar funcionários.

---

## US-039 — Alterar status do funcionário

**P0 | S04 | Dependência:** US-036

Como administrador, quero alterar o status do funcionário.

---

## US-040 — Vincular funcionário

**P0 | S04 | Dependências:** US-036, US-031, US-035

Como administrador, quero vincular funcionários a departamentos e cargos.

---

## US-041 — Consultar por departamento

**P1 | S04 | Dependência:** US-040

Como administrador, quero consultar funcionários por departamento.

---

## US-042 — Consultar por cargo

**P1 | S04 | Dependência:** US-040

Como administrador, quero consultar funcionários por cargo.

---

## US-043 — Criar endereço de funcionário

**P2 | S04 | Dependência:** US-036

Como administrador, quero cadastrar endereço de funcionário.

---

## US-044 — Editar endereço

**P2 | S04 | Dependência:** US-043

Como administrador, quero editar endereço de funcionário.

---

## US-045 — Consultar funcionário completo

**P1 | S04 | Dependências:** US-036, US-043

Como administrador, quero visualizar os dados completos do funcionário.

---

# 10. EP-05 — Projetos

## US-046 — Criar projeto

**P1 | S05 | Dependência:** US-036

Como gestor, quero criar projetos.

## US-047 — Visualizar projetos

**P1 | S05 | Dependência:** US-046

Como gestor, quero visualizar projetos.

## US-048 — Editar projeto

**P1 | S05 | Dependência:** US-046

Como gestor, quero editar projetos.

## US-049 — Definir prioridade

**P2 | S05 | Dependência:** US-046

Como gestor, quero definir prioridade do projeto.

## US-050 — Atribuir responsável

**P1 | S05 | Dependências:** US-046, US-036

Como gestor, quero atribuir responsável ao projeto.

## US-051 — Alterar status

**P1 | S05 | Dependência:** US-046

Como gestor, quero alterar o status do projeto.

## US-052 — Definir datas

**P1 | S05 | Dependência:** US-046

Como gestor, quero definir datas do projeto.

## US-053 — Concluir projeto

**P1 | S05 | Dependência:** US-051

Como gestor, quero concluir um projeto.

## US-054 — Cancelar projeto

**P2 | S05 | Dependência:** US-051

Como gestor, quero cancelar um projeto.

## US-055 — Visualizar progresso

**P1 | S05 | Dependências:** US-057, US-066

Como gestor, quero visualizar o progresso do projeto calculado a partir das tarefas.

## US-056 — Restringir alterações após conclusão

**P1 | S05 | Dependência:** US-053

Como sistema, quero impedir alterações incompatíveis após a conclusão do projeto.

---

# 11. EP-06 — Tarefas e Atividades

## US-057 — Criar tarefa

**P0 | S05 | Dependência:** US-036

Como funcionário, quero criar uma tarefa.

## US-058 — Vincular tarefa a projeto

**P1 | S05 | Dependências:** US-046, US-057

Como funcionário, quero vincular uma tarefa a um projeto.

## US-059 — Criar tarefa independente

**P1 | S05 | Dependência:** US-057

Como funcionário, quero criar uma tarefa independente de projeto.

## US-060 — Assumir tarefa

**P0 | S05 | Dependência:** US-057

Como responsável, quero assumir uma tarefa.

## US-061 — Editar tarefa

**P1 | S05 | Dependência:** US-057

Como responsável, quero editar uma tarefa.

## US-062 — Definir prazo

**P1 | S05 | Dependência:** US-057

Como responsável, quero definir prazo para uma tarefa.

## US-063 — Definir prioridade

**P2 | S05 | Dependência:** US-057

Como responsável, quero definir prioridade.

## US-064 — Iniciar tarefa

**P0 | S05 | Dependência:** US-057

Como responsável, quero iniciar uma tarefa.

## US-065 — Enviar para revisão

**P1 | S05 | Dependência:** US-064

Como responsável, quero colocar uma tarefa em revisão.

## US-066 — Concluir tarefa

**P0 | S05 | Dependência:** US-064

Como responsável, quero concluir uma tarefa.

## US-067 — Cancelar tarefa

**P2 | S05 | Dependência:** US-057

Como responsável, quero cancelar uma tarefa.

## US-068 — Consultar tarefas

**P1 | S05 | Dependência:** US-057

Como gestor, quero consultar tarefas por responsável, status e prazo.

## US-069 — Histórico de tarefa

**P1 | S05 | Dependência:** US-064

Como gestor, quero visualizar o histórico de alterações de status.

---

# 12. EP-07 — Reuniões e Agenda

## US-070 — Criar reunião

**P1 | S06 | Dependência:** US-036

Como funcionário, quero criar uma reunião.

## US-071 — Adicionar participantes

**P1 | S06 | Dependência:** US-070

Como organizador, quero adicionar participantes.

## US-072 — Aceitar convite

**P1 | S06 | Dependência:** US-071

Como participante, quero aceitar um convite.

## US-073 — Recusar convite

**P2 | S06 | Dependência:** US-071

Como participante, quero recusar um convite.

## US-074 — Alterar reunião

**P1 | S06 | Dependência:** US-070

Como organizador, quero alterar uma reunião.

## US-075 — Cancelar reunião

**P1 | S06 | Dependência:** US-070

Como organizador, quero cancelar uma reunião.

## US-076 — Consultar minhas reuniões

**P1 | S06 | Dependência:** US-070

Como funcionário, quero consultar minhas reuniões.

## US-077 — Vincular reunião a projeto

**P2 | S06 | Dependências:** US-070, US-046

Como gestor, quero vincular uma reunião a um projeto.

---

# 13. EP-08 — Clientes

## US-078 — Criar cliente

**P0 | S06 | Dependência:** US-001

Como funcionário autorizado, quero cadastrar clientes.

## US-079 — Visualizar clientes

**P0 | S06 | Dependência:** US-078

Como funcionário autorizado, quero visualizar clientes.

## US-080 — Editar cliente

**P1 | S06 | Dependência:** US-078

Como funcionário autorizado, quero editar clientes.

## US-081 — Inativar cliente

**P1 | S06 | Dependência:** US-078

Como funcionário autorizado, quero inativar clientes.

## US-082 — Criar endereço de cliente

**P1 | S06 | Dependência:** US-078

Como funcionário autorizado, quero cadastrar endereço de cliente.

## US-083 — Editar endereço de cliente

**P2 | S06 | Dependência:** US-082

Como funcionário autorizado, quero editar endereço de cliente.

## US-084 — Pesquisar cliente

**P1 | S06 | Dependência:** US-078

Como funcionário autorizado, quero pesquisar clientes por nome ou documento.

## US-085 — Histórico comercial do cliente

**P1 | S08 | Dependências:** US-078, US-117

Como gestor, quero visualizar o histórico comercial do cliente.

---

# 14. EP-09 — Fornecedores

## US-086 — Criar fornecedor

**P0 | S06 | Dependência:** US-001

Como funcionário autorizado, quero cadastrar fornecedores.

## US-087 — Visualizar fornecedores

**P0 | S06 | Dependência:** US-086

Como funcionário autorizado, quero visualizar fornecedores.

## US-088 — Editar fornecedor

**P1 | S06 | Dependência:** US-086

Como funcionário autorizado, quero editar fornecedores.

## US-089 — Inativar fornecedor

**P1 | S06 | Dependência:** US-086

Como funcionário autorizado, quero inativar fornecedores.

## US-090 — Criar endereço de fornecedor

**P1 | S06 | Dependência:** US-086

Como funcionário autorizado, quero cadastrar endereço de fornecedor.

## US-091 — Editar endereço de fornecedor

**P2 | S06 | Dependência:** US-090

Como funcionário autorizado, quero editar endereço de fornecedor.

## US-092 — Pesquisar fornecedor

**P1 | S06 | Dependência:** US-086

Como funcionário autorizado, quero pesquisar fornecedores por nome ou documento.

## US-093 — Histórico de compras do fornecedor

**P1 | S09 | Dependências:** US-086, US-127

Como gestor, quero visualizar o histórico de compras do fornecedor.

---

# 15. EP-10 — Categorias e Produtos

## US-094 — Criar produto ou serviço

**P0 | S07 | Dependência:** US-001

Como funcionário autorizado, quero cadastrar produtos e serviços.

## US-095 — Visualizar produtos

**P0 | S07 | Dependência:** US-094

Como funcionário autorizado, quero visualizar produtos.

## US-096 — Editar produto

**P1 | S07 | Dependência:** US-094

Como funcionário autorizado, quero editar produtos.

## US-097 — Inativar produto

**P1 | S07 | Dependência:** US-094

Como funcionário autorizado, quero inativar produtos.

## US-098 — Criar categoria

**P1 | S07 | Dependência:** US-001

Como administrador, quero cadastrar categorias.

## US-099 — Vincular produto a categoria

**P1 | S07 | Dependências:** US-094, US-098

Como administrador, quero vincular produtos a categorias.

## US-100 — Pesquisar produto

**P1 | S07 | Dependência:** US-094

Como funcionário autorizado, quero pesquisar produtos por SKU ou código de barras.

## US-101 — Criar estoque

**P0 | S07 | Dependência:** US-094

Como responsável pelo estoque, quero criar o estoque de um produto.

## US-102 — Definir estoque mínimo

**P1 | S07 | Dependência:** US-101

Como responsável pelo estoque, quero definir estoque mínimo.

## US-103 — Definir estoque máximo

**P2 | S07 | Dependência:** US-101

Como responsável pelo estoque, quero definir estoque máximo.

## US-104 — Consultar estoque

**P0 | S07 | Dependência:** US-101

Como funcionário autorizado, quero visualizar o estoque atual.

## US-105 — Produtos abaixo do mínimo

**P1 | S07 | Dependência:** US-102

Como gestor, quero identificar produtos abaixo do estoque mínimo.

---

# 16. EP-11 — Estoque

## US-106 — Entrada de estoque

**P0 | S07 | Dependência:** US-101

Como responsável pelo estoque, quero registrar entrada de estoque.

## US-107 — Saída de estoque

**P0 | S07 | Dependência:** US-101

Como responsável pelo estoque, quero registrar saída de estoque.

## US-108 — Ajuste de estoque

**P1 | S07 | Dependência:** US-101

Como responsável pelo estoque, quero ajustar o estoque.

## US-109 — Histórico de movimentações

**P0 | S07 | Dependência:** US-106

Como responsável pelo estoque, quero visualizar o histórico de movimentações.

## US-110 — Origem da movimentação

**P1 | S07 | Dependência:** US-109

Como gestor, quero identificar a origem de uma movimentação.

---

# 17. EP-12 — Vendas

## US-111 — Criar venda

**P0 | S08 | Dependências:** US-078, US-094

Como vendedor, quero criar uma venda.

## US-112 — Adicionar item à venda

**P0 | S08 | Dependência:** US-111

Como vendedor, quero adicionar produtos à venda.

## US-113 — Definir quantidade

**P0 | S08 | Dependência:** US-112

Como vendedor, quero definir a quantidade dos itens.

## US-114 — Aplicar desconto

**P1 | S08 | Dependência:** US-112

Como vendedor, quero registrar desconto.

## US-115 — Calcular totais

**P0 | S08 | Dependência:** US-112

Como vendedor, quero visualizar subtotal e total.

## US-116 — Editar venda em rascunho

**P0 | S08 | Dependência:** US-111

Como vendedor, quero editar uma venda em rascunho.

## US-117 — Confirmar venda

**P0 | S08 | Dependência:** US-112

Como vendedor, quero confirmar a venda.

**Controles técnicos:**

* validação de estoque;
* transação;
* idempotência;
* autorização;
* cálculo no servidor.

## US-118 — Cancelar venda

**P1 | S08 | Dependência:** US-111

Como vendedor, quero cancelar uma venda.

## US-119 — Vendas por período

**P1 | S08 | Dependência:** US-117

Como gestor, quero consultar vendas por período.

## US-120 — Vendas por cliente

**P1 | S08 | Dependência:** US-117

Como gestor, quero consultar vendas por cliente.

---

# 18. EP-13 — Compras

## US-121 — Criar compra

**P0 | S09 | Dependências:** US-086, US-094

Como comprador, quero criar uma compra.

## US-122 — Adicionar item à compra

**P0 | S09 | Dependência:** US-121

Como comprador, quero adicionar produtos à compra.

## US-123 — Definir quantidade da compra

**P0 | S09 | Dependência:** US-122

Como comprador, quero definir quantidade dos itens.

## US-124 — Aplicar desconto

**P1 | S09 | Dependência:** US-122

Como comprador, quero registrar desconto.

## US-125 — Calcular totais

**P0 | S09 | Dependência:** US-122

Como comprador, quero visualizar subtotal e total.

## US-126 — Editar compra em rascunho

**P0 | S09 | Dependência:** US-121

Como comprador, quero editar uma compra em rascunho.

## US-127 — Confirmar compra

**P0 | S09 | Dependência:** US-122

Como comprador, quero confirmar uma compra.

## US-128 — Cancelar compra

**P1 | S09 | Dependência:** US-121

Como comprador, quero cancelar uma compra.

## US-129 — Compras por período

**P1 | S09 | Dependência:** US-127

Como gestor, quero consultar compras por período.

---

# 19. EP-14 — Contas Financeiras

## US-130 — Criar conta financeira

**P0 | S10 | Dependência:** US-001

Como financeiro, quero criar uma conta financeira.

## US-131 — Visualizar contas financeiras

**P0 | S10 | Dependência:** US-130

Como financeiro, quero visualizar contas financeiras.

## US-132 — Editar conta financeira

**P1 | S10 | Dependência:** US-130

Como financeiro, quero editar uma conta financeira.

## US-133 — Inativar conta financeira

**P1 | S10 | Dependência:** US-130

Como financeiro, quero inativar uma conta financeira.

## US-134 — Consultar saldo

**P0 | S10 | Dependência:** US-130

Como financeiro, quero consultar o saldo atual.

## US-135 — Histórico financeiro

**P0 | S10 | Dependência:** US-134

Como financeiro, quero consultar o histórico financeiro.

## US-136 — Bloquear conta inativa

**P0 | S10 | Dependência:** US-133

Como sistema, quero impedir operações em contas inativas.

## US-137 — Filtrar contas por tipo

**P2 | S10 | Dependência:** US-130

Como financeiro, quero consultar contas por tipo.

## US-138 — Criar categoria financeira

**P0 | S10 | Dependência:** US-001

Como financeiro, quero criar categorias financeiras.

## US-139 — Definir tipo da categoria

**P0 | S10 | Dependência:** US-138

Como financeiro, quero classificar a categoria como receita ou despesa.

## US-140 — Inativar categoria financeira

**P1 | S10 | Dependência:** US-138

Como financeiro, quero inativar uma categoria financeira.

---

# 20. EP-15 — Contas a Receber

## US-141 — Criar conta a receber

**P0 | S11 | Dependências:** US-130, US-138

Como financeiro, quero registrar uma conta a receber.

## US-142 — Vincular a venda

**P0 | S11 | Dependências:** US-141, US-117

Como financeiro, quero vincular uma conta a receber a uma venda quando aplicável.

## US-143 — Vincular cliente

**P0 | S11 | Dependência:** US-078

Como financeiro, quero vincular a conta a receber a um cliente.

## US-144 — Consultar contas em aberto

**P0 | S11 | Dependência:** US-141

Como financeiro, quero consultar contas a receber em aberto.

## US-145 — Consultar contas vencidas

**P0 | S11 | Dependência:** US-141

Como financeiro, quero consultar contas a receber vencidas.

## US-146 — Cancelar conta a receber

**P1 | S11 | Dependência:** US-141

Como financeiro, quero cancelar uma conta a receber.

---

# 21. EP-16 — Contas a Pagar

## US-147 — Criar conta a pagar

**P0 | S11 | Dependências:** US-130, US-138

Como financeiro, quero registrar uma conta a pagar.

## US-148 — Vincular a compra

**P0 | S11 | Dependências:** US-147, US-127

Como financeiro, quero vincular uma conta a pagar a uma compra quando aplicável.

## US-149 — Vincular fornecedor

**P0 | S11 | Dependência:** US-086

Como financeiro, quero vincular a conta a pagar a um fornecedor.

## US-150 — Consultar contas em aberto

**P0 | S11 | Dependência:** US-147

Como financeiro, quero consultar contas a pagar em aberto.

## US-151 — Consultar contas vencidas

**P0 | S11 | Dependência:** US-147

Como financeiro, quero consultar contas a pagar vencidas.

## US-152 — Cancelar conta a pagar

**P1 | S11 | Dependência:** US-147

Como financeiro, quero cancelar uma conta a pagar.

---

# 22. EP-17 — Parcelas

## US-153 — Criar parcelas

**P0 | S11 | Dependências:** US-141, US-147

Como financeiro, quero dividir uma obrigação em parcelas.

## US-154 — Definir vencimento

**P0 | S11 | Dependência:** US-153

Como financeiro, quero definir o vencimento das parcelas.

## US-155 — Consultar parcelas pendentes

**P0 | S11 | Dependência:** US-153

Como financeiro, quero consultar parcelas pendentes.

## US-156 — Identificar parcelas atrasadas

**P0 | S11 | Dependência:** US-153

Como financeiro, quero identificar parcelas atrasadas.

## US-157 — Controlar pagamentos parciais

**P0 | S11 | Dependência:** US-153

Como financeiro, quero controlar pagamentos parciais.

## US-158 — Validar soma das parcelas

**P0 | S11 | Dependência:** US-153

Como sistema, quero impedir que o valor das parcelas ultrapasse ou fique incompatível com a obrigação.

---

# 23. EP-18 — Pagamentos e Recebimentos

## US-159 — Registrar pagamento/recebimento

**P0 | S12 | Dependências:** US-153, US-130

Como financeiro, quero registrar um pagamento ou recebimento.

**Controles técnicos:**

* transação;
* idempotência;
* concorrência;
* auditoria.

## US-160 — Informar forma de pagamento

**P1 | S12 | Dependência:** US-159

Como financeiro, quero informar a forma de pagamento.

## US-161 — Registrar pagamento parcial

**P0 | S12 | Dependência:** US-159

Como financeiro, quero registrar um pagamento parcial.

## US-162 — Quitar parcela

**P0 | S12 | Dependência:** US-159

Como financeiro, quero quitar uma parcela totalmente.

## US-163 — Impedir pagamento excedente

**P0 | S12 | Dependência:** US-159

Como sistema, quero impedir pagamento superior ao saldo devido.

## US-164 — Consultar pagamentos

**P1 | S12 | Dependência:** US-159

Como financeiro, quero consultar pagamentos realizados.

## US-165 — Estornar pagamento

**P0 | S12 | Dependência:** US-159

Como financeiro, quero estornar um pagamento de forma controlada.

## US-166 — Identificar responsável

**P1 | S12 | Dependência:** US-159

Como financeiro, quero identificar o responsável pelo pagamento.

---

# 24. EP-19 — Movimentações Financeiras e Integrações

## US-167 — Registrar movimentação financeira

**P0 | S12 | Dependência:** US-159

Como sistema, quero registrar a movimentação financeira gerada pela operação.

## US-168 — Consultar movimentações

**P0 | S12 | Dependência:** US-167

Como financeiro, quero consultar movimentações.

## US-169 — Identificar entrada ou saída

**P0 | S12 | Dependência:** US-167

Como financeiro, quero identificar se a movimentação é entrada ou saída.

## US-170 — Registrar saldo anterior/posterior

**P0 | S12 | Dependência:** US-167

Como sistema, quero registrar o saldo anterior e posterior da operação.

## US-171 — Integrar venda, estoque e financeiro

**P0 | S12 | Dependências:** US-117, US-141, US-106, US-107

Como sistema, quero garantir que a operação de venda seja refletida corretamente no estoque e no financeiro.

**Tarefas técnicas:**

* transação;
* idempotência;
* locking;
* validação de estado;
* rollback;
* testes de falha.

## US-172 — Integrar compra, estoque e financeiro

**P0 | S12 | Dependências:** US-127, US-147, US-106, US-107

Como sistema, quero garantir consistência entre compra, estoque e financeiro.

## US-173 — Movimentações por período

**P1 | S12 | Dependência:** US-167

Como financeiro, quero consultar movimentações por período.

## US-174 — Entradas e saídas por categoria

**P1 | S12 | Dependências:** US-138, US-167

Como financeiro, quero analisar entradas e saídas por categoria.

## US-175 — Saldo consolidado

**P1 | S12 | Dependências:** US-130, US-167

Como financeiro, quero visualizar o saldo consolidado.

## US-176 — Origem da movimentação

**P1 | S12 | Dependência:** US-167

Como financeiro, quero identificar a origem da movimentação.

## US-177 — Impedir duplicação financeira

**P0 | S12 | Dependência:** US-159

Como sistema, quero impedir que uma operação financeira seja processada duas vezes.

**Tarefa técnica principal:** TEC-019.

## US-178 — Impedir operação financeira fora de ordem

**P0 | S12 | Dependências:** US-159, US-153

Como sistema, quero impedir operações incompatíveis com o estado atual da obrigação.

## US-179 — Garantir consistência após falha

**P0 | S12 | Dependências:** US-159, US-167

Como sistema, quero garantir que uma operação financeira não deixe dados parcialmente atualizados.

**Tarefas técnicas:**

* `@Transactional`;
* rollback;
* testes de falha;
* teste de concorrência.

---

# 25. EP-20 — Auditoria

## US-180 — Consultar auditoria

**P0 | S13 | Dependência:** TEC-013

Como administrador, quero consultar o histórico de operações relevantes.

## US-181 — Identificar responsável

**P0 | S13 | Dependência:** US-180

Como administrador, quero identificar quem executou a operação.

## US-182 — Identificar data/hora

**P0 | S13 | Dependência:** US-180

Como administrador, quero identificar quando uma operação aconteceu.

## US-183 — Identificar entidade

**P1 | S13 | Dependência:** US-180

Como administrador, quero identificar qual entidade foi afetada.

## US-184 — Filtrar auditoria

**P1 | S13 | Dependência:** US-180

Como administrador, quero filtrar registros de auditoria.

## US-185 — Impedir dados sensíveis

**P0 | S13 | Dependência:** TEC-013

Como sistema, quero impedir o armazenamento de senhas, tokens e segredos na auditoria.

## US-186 — Identificar resultado

**P1 | S13 | Dependência:** US-180

Como administrador, quero identificar se a operação foi bem-sucedida ou malsucedida.

## US-187 — Rastrear por requestId

**P1 | S13 | Dependência:** US-180

Como administrador, quero rastrear uma operação por requestId/correlationId.

---

# 26. EP-21 — Consultas, Filtros e Paginação

## US-188 — Paginação

**P0 | S13 | Dependência:** TEC-016

Como usuário, quero receber resultados paginados.

## US-189 — Filtros

**P1 | S13 | Dependência:** TEC-017

Como usuário, quero aplicar filtros às consultas.

## US-190 — Pesquisar funcionários

**P1 | S13 | Dependência:** US-037

Como gestor, quero pesquisar funcionários por nome, matrícula e status.

## US-191 — Pesquisar clientes e fornecedores

**P1 | S13 | Dependências:** US-079, US-087

Como gestor, quero pesquisar clientes e fornecedores.

## US-192 — Pesquisar produtos

**P1 | S13 | Dependência:** US-095

Como gestor, quero pesquisar produtos.

## US-193 — Consultar projetos e tarefas

**P1 | S13 | Dependências:** US-047, US-068

Como gestor, quero consultar projetos e tarefas por status.

## US-194 — Consultar operações comerciais

**P1 | S13 | Dependências:** US-119, US-129

Como gestor, quero consultar operações comerciais por período.

## US-195 — Consultar obrigações

**P0 | S13 | Dependências:** US-144, US-150

Como financeiro, quero consultar obrigações por status e vencimento.

---

# 27. EP-22 — Relatórios e Indicadores

## US-196 — Total de vendas

**P1 | S13 | Dependência:** US-119

Como gestor, quero visualizar o total de vendas por período.

## US-197 — Total de compras

**P1 | S13 | Dependência:** US-129

Como gestor, quero visualizar o total de compras por período.

## US-198 — Produtos mais vendidos

**P1 | S13 | Dependência:** US-117

Como gestor, quero visualizar os produtos mais vendidos.

## US-199 — Clientes que mais geram receita

**P1 | S13 | Dependência:** US-085

Como gestor, quero visualizar os clientes que mais geram receita.

## US-200 — Indicadores gerenciais

**P1 | S13 | Dependências:** US-175, US-196, US-197, US-198, US-199

Como gestor, quero visualizar indicadores básicos financeiros e operacionais.

---

# 28. Tarefas Técnicas Transversais

## TEC-001 — Estrutura do monólito modular

Criar estrutura base dos módulos.

**Sprint:** S01

---

## TEC-002 — Configuração Spring Boot

Configurar aplicação, profiles e estrutura inicial.

**Sprint:** S01

---

## TEC-003 — Configuração MySQL

Configurar conexão com MySQL.

**Sprint:** S01

---

## TEC-004 — Configuração Flyway

Configurar migrations.

**Sprint:** S01

---

## TEC-005 — Padrão DTO

Definir padrão de Requests e Responses.

**Sprint:** S01

---

## TEC-006 — Padrão arquitetural

Estabelecer:

```text
Controller
    ↓
Service / Use Case
    ↓
Repository
    ↓
JPA
    ↓
MySQL
```

**Sprint:** S01

---

## TEC-007 — Tratamento global de exceções

Implementar mecanismo centralizado de erros.

**Sprint:** S01

---

## TEC-008 — Bean Validation

Configurar validação de entrada.

**Sprint:** S01

---

## TEC-009 — Spring Security

Configurar infraestrutura de segurança.

**Sprint:** S02

---

## TEC-010 — JWT

Implementar emissão, validação e processamento de JWT.

**Sprint:** S02

---

## TEC-011 — Autorização funcional

Implementar autorização baseada em permissões.

**Sprint:** S03/S04

---

## TEC-012 — Autorização contextual

Implementar validação de acesso ao objeto.

**Sprint:** S04

---

## TEC-013 — Auditoria

Implementar `AuditService` e persistência.

**Sprint:** S13

---

## TEC-014 — Request ID

Implementar correlation/request ID.

**Sprint:** S13

---

## TEC-015 — Logging seguro

Impedir exposição de dados sensíveis.

**Sprint:** Todos

---

## TEC-016 — Paginação

Implementar padrão de paginação.

**Sprint:** S13

---

## TEC-017 — Filtros

Implementar filtros controlados.

**Sprint:** S13

---

## TEC-018 — Optimistic Locking

Implementar controle de concorrência.

**Aplicação:**

* estoque;
* contas financeiras;
* operações críticas.

---

## TEC-019 — Idempotência

Implementar chave de idempotência.

**Aplicação:**

* vendas;
* compras;
* estoque;
* pagamentos;
* recebimentos.

---

## TEC-020 — Transações

Padronizar fronteiras transacionais.

**Aplicação:**

* estoque;
* financeiro;
* comercial.

---

## TEC-021 — Workflows

Implementar validação de transições de estado.

**Aplicação:**

* vendas;
* compras;
* tarefas;
* projetos;
* financeiro.

---

## TEC-022 — Testes unitários

Criar testes unitários para regras e serviços.

---

## TEC-023 — Testes de integração

Criar testes de integração.

---

## TEC-024 — Testcontainers

Configurar MySQL em containers para testes.

---

## TEC-025 — OpenAPI

Documentar endpoints.

---

## TEC-026 — Política de erros HTTP

Padronizar respostas de erro.

---

## TEC-027 — Proteção BOLA/IDOR

Criar testes e controles de autorização contextual.

---

## TEC-028 — Proteção contra Mass Assignment

Validar DTOs e campos autorizados.

---

## TEC-029 — Enum e ID Manipulation

Validar IDs e enums recebidos.

---

## TEC-030 — Rate Limiting

Implementar proteção contra excesso de requisições em endpoints sensíveis.

---

## TEC-031 — Revisão de DTOs

Verificar exposição de dados.

---

## TEC-032 — Índices

Criar e revisar índices de banco.

---

## TEC-033 — Backup

Definir estratégia de backup.

---

## TEC-034 — Health Checks

Configurar Spring Actuator.

---

## TEC-035 — Observabilidade

Criar métricas e mecanismos de rastreabilidade.

---

## TEC-036 — Profiles

Configurar:

```text
dev
test
prod
```

---

## TEC-037 — Dockerfile

Criar imagem da aplicação.

---

## TEC-038 — Docker Compose

Criar ambiente local com MySQL quando necessário.

---

## TEC-039 — CI

Criar pipeline de integração contínua.

---

## TEC-040 — Qualidade de código

Integrar análise estática e verificação de qualidade.

---

# 29. Matriz de Sprints

## S01 — Fundação + Empresa

**Histórias:**

```text
US-001
US-002
US-003
US-004
```

**Tarefas técnicas principais:**

```text
TEC-001
TEC-002
TEC-003
TEC-004
TEC-005
TEC-006
TEC-007
TEC-008
```

---

## S02 — Autenticação

**Histórias:**

```text
US-005
US-006
US-007
US-014
US-015
```

**Tarefas técnicas:**

```text
TEC-009
TEC-010
TEC-030
```

---

## S03 — Perfis e Permissões

**Histórias:**

```text
US-016
US-017
US-018
US-019
US-020
US-021
US-022
US-028
```

**Tarefas técnicas:**

```text
TEC-011
```

---

## S04 — Organização + Usuários + Autorização

**Histórias:**

```text
US-008
US-009
US-010
US-011
US-012
US-013
US-023
US-024
US-025
US-026
US-027
US-029
US-030

US-031
US-032
US-033
US-034
US-035
US-036
US-037
US-038
US-039
US-040
US-041
US-042
US-043
US-044
US-045
```

**Tarefas técnicas:**

```text
TEC-011
TEC-012
```

---

## S05 — Projetos + Tarefas

```text
US-046
US-047
US-048
US-049
US-050
US-051
US-052
US-053
US-054
US-055
US-056

US-057
US-058
US-059
US-060
US-061
US-062
US-063
US-064
US-065
US-066
US-067
US-068
US-069
```

---

## S06 — Reuniões + Clientes + Fornecedores

```text
US-070
US-071
US-072
US-073
US-074
US-075
US-076
US-077

US-078
US-079
US-080
US-081
US-082
US-083
US-084

US-086
US-087
US-088
US-089
US-090
US-091
US-092
```

---

## S07 — Produtos + Estoque

```text
US-094
US-095
US-096
US-097
US-098
US-099
US-100
US-101
US-102
US-103
US-104
US-105

US-106
US-107
US-108
US-109
US-110
```

**Tarefas técnicas críticas:**

```text
TEC-018
TEC-019
```

---

## S08 — Vendas

```text
US-111
US-112
US-113
US-114
US-115
US-116
US-117
US-118
US-119
US-120
US-085
```

**Tarefas técnicas:**

```text
TEC-019
TEC-020
TEC-021
```

---

## S09 — Compras

```text
US-121
US-122
US-123
US-124
US-125
US-126
US-127
US-128
US-129
US-093
```

**Tarefas técnicas:**

```text
TEC-019
TEC-020
TEC-021
```

---

## S10 — Base Financeira

```text
US-130
US-131
US-132
US-133
US-134
US-135
US-136
US-137
US-138
US-139
US-140
```

**Tarefas técnicas:**

```text
TEC-018
TEC-020
```

---

## S11 — Obrigações + Parcelas

```text
US-141
US-142
US-143
US-144
US-145
US-146

US-147
US-148
US-149
US-150
US-151
US-152

US-153
US-154
US-155
US-156
US-157
US-158
```

**Tarefas técnicas:**

```text
TEC-020
TEC-021
```

---

## S12 — Pagamentos + Movimentações + Integrações

```text
US-159
US-160
US-161
US-162
US-163
US-164
US-165
US-166

US-167
US-168
US-169
US-170
US-171
US-172
US-173
US-174
US-175
US-176
US-177
US-178
US-179
```

**Tarefas técnicas críticas:**

```text
TEC-018
TEC-019
TEC-020
TEC-021
```

---

## S13 — Auditoria + Consultas + Relatórios

```text
US-180
US-181
US-182
US-183
US-184
US-185
US-186
US-187

US-188
US-189
US-190
US-191
US-192
US-193
US-194
US-195

US-196
US-197
US-198
US-199
US-200
```

**Tarefas técnicas:**

```text
TEC-013
TEC-014
TEC-016
TEC-017
TEC-025
TEC-032
TEC-034
TEC-035
```

---

# 30. Definition of Ready

Uma história somente poderá entrar em desenvolvimento quando:

* o objetivo estiver claro;
* a regra de negócio estiver definida;
* os critérios de aceitação estiverem definidos;
* as dependências estiverem resolvidas;
* o modelo de dados necessário estiver conhecido;
* as permissões necessárias estiverem definidas;
* não existir bloqueio técnico relevante desconhecido.

---

# 31. Definition of Done

Uma história somente será considerada `DONE` quando:

* código implementado;
* regras de negócio implementadas;
* validações implementadas;
* autorização implementada quando aplicável;
* tratamento de erros implementado;
* testes concluídos;
* auditoria implementada quando aplicável;
* migration criada quando necessária;
* documentação atualizada;
* revisão de código concluída;
* critérios de aceitação atendidos.

---

# 32. Critérios de Aceitação Transversais

## CT-001 — Autenticação

Recursos protegidos devem exigir usuário autenticado.

## CT-002 — Autorização

Recursos protegidos devem verificar permissão.

## CT-003 — Autorização contextual

Recursos individuais devem validar o contexto do usuário.

## CT-004 — Validação

Dados externos devem ser validados.

## CT-005 — Erros

Erros não devem expor informações internas.

## CT-006 — DTO

Entities não devem ser expostas diretamente.

## CT-007 — Auditoria

Operações críticas devem ser auditadas.

## CT-008 — Paginação

Consultas potencialmente grandes devem possuir paginação.

## CT-009 — Transação

Operações compostas devem preservar atomicidade.

## CT-010 — Concorrência

Operações concorrentes devem possuir proteção adequada.

## CT-011 — Histórico

Fatos históricos críticos devem ser preservados.

## CT-012 — Dados sensíveis

Dados sensíveis não devem ser registrados em logs.

## CT-013 — Observabilidade

Operações críticas devem ser rastreáveis.

## CT-014 — Testes

Funcionalidades críticas devem possuir testes automatizados.

## CT-015 — OpenAPI

Endpoints devem ser documentados.

---

# 33. Dependências Macro

A sequência geral do MVP deve seguir:

```text
Empresa
   ↓
Estrutura Organizacional
   ↓
Funcionários
   ↓
Usuários
   ↓
Perfis + Permissões
   ↓
Autorização
   ↓
Projetos + Tarefas
   ↓
Reuniões
   ↓
Clientes + Fornecedores
   ↓
Produtos + Categorias
   ↓
Estoque
   ↓
Vendas + Compras
   ↓
Contas Financeiras
   ↓
Contas a Receber/Pagar
   ↓
Parcelas
   ↓
Pagamentos
   ↓
Movimentações Financeiras
   ↓
Integrações
   ↓
Auditoria + Consultas
   ↓
Relatórios + Indicadores
```

---

# 34. Regras para evitar ciclos

Não deve existir dependência no formato:

```text
A → B
B → C
C → A
```

Também não deve existir:

```text
US-001 → EP-01 → US-001
```

Épicos são agrupadores e não constituem dependências.

Dependências devem apontar para artefatos concretos.

---

# 35. Operações Críticas

As seguintes operações exigem tratamento especial:

### Estoque

```text
Entrada
Saída
Ajuste
```

Controles:

* validação;
* autorização;
* optimistic locking;
* idempotência;
* transação;
* auditoria.

### Vendas

```text
Venda
 ↓
Confirmação
 ↓
Estoque
 ↓
Obrigação
```

### Compras

```text
Compra
 ↓
Confirmação
 ↓
Estoque
 ↓
Obrigação
```

### Financeiro

```text
Obrigação
 ↓
Parcela
 ↓
Pagamento
 ↓
Movimentação
 ↓
Saldo
```

---

# 36. Relação com os Requisitos

Cada história deve possuir rastreabilidade para:

* requisito funcional;
* requisito não funcional quando aplicável;
* regra de negócio;
* critério de aceitação;
* tarefa técnica quando aplicável.

Exemplo:

```text
US-159
Registrar pagamento
       ↓
RF-172
       ↓
RN-188
RN-191
RN-192
RN-198
RN-199
       ↓
CT-001
CT-002
CT-004
CT-007
CT-009
CT-010
```

---

# 37. Escopo Pós-MVP

Não fazem parte das 200 histórias da Fase 1:

* campanhas;
* marketing completo;
* analytics avançado;
* CRM;
* leads;
* notificações;
* documentos;
* RH avançado;
* integrações externas;
* automação;
* IA;
* multi-tenancy;
* microservices.

Esses recursos deverão possuir backlog próprio quando forem priorizados.

---

# 38. Estado do Backlog

**Documento:** Backlog
**Versão:** 1.3
**Histórias:** 200
**Épicos:** 22
**Sprints:** 13
**Arquitetura:** Monólito Modular
**Banco:** MySQL 8
**Estado:** Backlog consolidado do MVP
