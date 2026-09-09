# MODELO DE DADOS

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Banco de dados:** MySQL 8
**ORM:** Spring Data JPA + Hibernate
**Migrações:** Flyway
**Identificadores principais:** UUID

---

# 1. Objetivo

Este documento define o modelo de dados do Sistema Interno para Empresas.

O objetivo é estabelecer:

* entidades;
* atributos;
* relacionamentos;
* cardinalidades;
* chaves primárias;
* chaves estrangeiras;
* regras de unicidade;
* integridade referencial;
* estados persistidos;
* informações históricas;
* requisitos específicos de concorrência.

O modelo de dados deve permanecer alinhado aos requisitos, às regras de negócio e à arquitetura.

---

# 2. Princípios do Modelo

O modelo seguirá os seguintes princípios:

* integridade referencial;
* identificação por UUID;
* separação entre entidade de negócio e conta de acesso;
* preservação de histórico;
* normalização adequada;
* constraints no banco;
* ausência de dados derivados desnecessariamente armazenados;
* uso de `BigDecimal` para valores monetários;
* controle de concorrência onde necessário;
* migrations versionadas.

---

# 3. Convenções

## 3.1 Identificador

As entidades principais utilizarão:

```text
UUID
```

como identificador.

## 3.2 Datas

Serão utilizados tipos apropriados para representar:

* `LocalDate`;
* `LocalDateTime`.

Datas que dependem de instante temporal devem possuir tratamento consistente de fuso horário na aplicação.

## 3.3 Valores monetários

Valores financeiros serão representados na aplicação por:

```text
BigDecimal
```

No banco, devem utilizar tipo decimal com precisão adequada.

Exemplo conceitual:

```text
DECIMAL(19,2)
```

A precisão definitiva deve ser validada conforme os requisitos financeiros do sistema.

## 3.4 Status

Estados controlados pelo domínio serão representados por enums na aplicação e por representação compatível no banco.

---

# 4. Diagrama Conceitual

Visão simplificada:

```text
                           EMPRESA
                              |
        +---------------------+----------------------+
        |                     |                      |
        v                     v                      v
   DEPARTAMENTO             CARGO                FUNCIONARIO
                                                     |
                                                     |
                                                   0..1
                                                     |
                                                     v
                                                   USUARIO
                                                     |
                                                     v
                                                  PERFIL
                                                     |
                                                     v
                                                 PERMISSAO


FUNCIONARIO ───────────────→ PROJETO
                               |
                               v
                            TAREFA

FUNCIONARIO ───────────────→ REUNIAO
                               |
                               v
                       PARTICIPANTE_REUNIAO


EMPRESA ───→ CLIENTE
EMPRESA ───→ FORNECEDOR
EMPRESA ───→ CATEGORIA ───→ PRODUTO
                              |
                              v
                           ESTOQUE
                              |
                              v
                    MOVIMENTACAO_ESTOQUE


CLIENTE ───→ VENDA ───→ ITEM_VENDA ───→ PRODUTO

FORNECEDOR ─→ COMPRA ─→ ITEM_COMPRA ─→ PRODUTO


VENDA ─────→ CONTA_A_RECEBER ──┐
                               |
                               v
                             PARCELA
                               |
                               v
                            PAGAMENTO
                               |
                               v
                         MOVIMENTACAO
                               |
                               v
                       CONTA_FINANCEIRA


COMPRA ────→ CONTA_A_PAGAR ───┘
```

---

# 5. Empresa

## Tabela

```text
empresa
```

## Atributos

| Campo            | Tipo conceitual | Obrigatório | Observação        |
| ---------------- | --------------- | ----------: | ----------------- |
| id               | UUID            |         Sim | PK                |
| razao_social     | VARCHAR         |         Sim | Razão social      |
| nome_fantasia    | VARCHAR         |         Sim | Nome comercial    |
| cnpj             | VARCHAR         |         Sim | Único             |
| email            | VARCHAR         |         Sim | E-mail            |
| telefone         | VARCHAR         |         Não | Telefone          |
| status           | ENUM            |         Sim | Status da empresa |
| data_criacao     | DATETIME        |         Sim | Auditoria         |
| data_atualizacao | DATETIME        |         Sim | Auditoria         |

## Relacionamentos

```text
Empresa 1:N Departamento
Empresa 1:N Cargo
Empresa 1:N Funcionario
Empresa 1:N Cliente
Empresa 1:N Fornecedor
Empresa 1:N Categoria
Empresa 1:N Produto
Empresa 1:N Projeto
Empresa 1:N Reuniao
Empresa 1:N ContaFinanceira
Empresa 1:N CategoriaFinanceira
```

---

# 6. Departamento

## Tabela

```text
departamento
```

## Atributos

| Campo            | Tipo         | Obrigatório |
| ---------------- | ------------ | ----------: |
| id               | UUID         |         Sim |
| empresa_id       | UUID         |         Sim |
| nome             | VARCHAR      |         Sim |
| descricao        | VARCHAR/TEXT |         Não |
| status           | ENUM         |         Sim |
| data_criacao     | DATETIME     |         Sim |
| data_atualizacao | DATETIME     |         Sim |

## Relacionamentos

```text
Empresa 1:N Departamento
Departamento 1:N Funcionario
```

---

# 7. Cargo

## Tabela

```text
cargo
```

## Atributos

| Campo            | Tipo         | Obrigatório |
| ---------------- | ------------ | ----------: |
| id               | UUID         |         Sim |
| empresa_id       | UUID         |         Sim |
| nome             | VARCHAR      |         Sim |
| descricao        | VARCHAR/TEXT |         Não |
| status           | ENUM         |         Sim |
| data_criacao     | DATETIME     |         Sim |
| data_atualizacao | DATETIME     |         Sim |

Cargo não armazena salário no MVP.

Cargo não define permissões diretamente.

---

# 8. Funcionário

## Tabela

```text
funcionario
```

## Atributos

| Campo            | Tipo     | Obrigatório | Observação |
| ---------------- | -------- | ----------: | ---------- |
| id               | UUID     |         Sim | PK         |
| empresa_id       | UUID     |         Sim | FK         |
| departamento_id  | UUID     |         Sim | FK         |
| cargo_id         | UUID     |         Sim | FK         |
| matricula        | VARCHAR  |         Sim | Unique     |
| nome             | VARCHAR  |         Sim |            |
| cpf              | VARCHAR  |         Sim | Unique     |
| email            | VARCHAR  |         Sim |            |
| telefone         | VARCHAR  |         Não |            |
| data_nascimento  | DATE     |         Não |            |
| data_admissao    | DATE     |         Sim |            |
| data_demissao    | DATE     |         Não |            |
| status           | ENUM     |         Sim |            |
| data_criacao     | DATETIME |         Sim |            |
| data_atualizacao | DATETIME |         Sim |            |

## Status

```text
ATIVO
AFASTADO
FERIAS
DESLIGADO
```

## Relacionamentos

```text
Empresa 1:N Funcionario
Departamento 1:N Funcionario
Cargo 1:N Funcionario
Funcionario 1:0..1 Usuario
Funcionario 1:N Endereco
```

---

# 9. Usuário

## Tabela

```text
usuario
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| funcionario_id   | UUID     |         Sim |
| email            | VARCHAR  |         Sim |
| senha_hash       | VARCHAR  |         Sim |
| status           | ENUM     |         Sim |
| ultimo_login     | DATETIME |         Não |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Status

```text
ATIVO
BLOQUEADO
INATIVO
```

## Restrições

`funcionario_id` deve possuir unicidade.

Isso garante:

```text
1 Funcionario → no máximo 1 Usuario
```

---

# 10. Perfil

## Tabela

```text
perfil
```

## Atributos

| Campo            | Tipo         | Obrigatório |
| ---------------- | ------------ | ----------: |
| id               | UUID         |         Sim |
| nome             | VARCHAR      |         Sim |
| descricao        | VARCHAR/TEXT |         Não |
| status           | ENUM         |         Sim |
| data_criacao     | DATETIME     |         Sim |
| data_atualizacao | DATETIME     |         Sim |

## Relacionamentos

```text
Usuario N:N Perfil
Perfil N:N Permissao
```

---

# 11. Usuario_Perfil

## Tabela

```text
usuario_perfil
```

## Atributos

| Campo      | Tipo | Obrigatório |
| ---------- | ---- | ----------: |
| usuario_id | UUID |         Sim |
| perfil_id  | UUID |         Sim |

## Chave

```text
PRIMARY KEY (usuario_id, perfil_id)
```

---

# 12. Permissão

## Tabela

```text
permissao
```

## Atributos

| Campo            | Tipo         | Obrigatório |
| ---------------- | ------------ | ----------: |
| id               | UUID         |         Sim |
| nome             | VARCHAR      |         Sim |
| descricao        | VARCHAR/TEXT |         Não |
| modulo           | ENUM         |         Sim |
| acao             | ENUM         |         Sim |
| data_criacao     | DATETIME     |         Sim |
| data_atualizacao | DATETIME     |         Sim |

## Restrição

A combinação:

```text
modulo + acao
```

deve ser única.

---

# 13. Perfil_Permissao

## Tabela

```text
perfil_permissao
```

## Atributos

| Campo        | Tipo | Obrigatório |
| ------------ | ---- | ----------: |
| perfil_id    | UUID |         Sim |
| permissao_id | UUID |         Sim |

## Chave

```text
PRIMARY KEY (perfil_id, permissao_id)
```

---

# 14. Endereço

## Tabela

```text
endereco
```

O modelo inicial utiliza relacionamentos explícitos com as entidades que possuem endereço.

Uma estratégia recomendada é utilizar tabelas específicas de associação para manter integridade referencial.

Exemplo:

```text
funcionario_endereco
cliente_endereco
fornecedor_endereco
```

O endereço contém:

| Campo       | Tipo    | Obrigatório |
| ----------- | ------- | ----------: |
| id          | UUID    |         Sim |
| logradouro  | VARCHAR |         Sim |
| numero      | VARCHAR |         Sim |
| complemento | VARCHAR |         Não |
| bairro      | VARCHAR |         Sim |
| cidade      | VARCHAR |         Sim |
| estado      | VARCHAR |         Sim |
| cep         | VARCHAR |         Sim |

Não será utilizado um campo genérico como:

```text
tipo_entidade
entidade_id
```

para representar relações polimórficas.

---

# 15. Projeto

## Tabela

```text
projeto
```

## Atributos

| Campo                 | Tipo     | Obrigatório |
| --------------------- | -------- | ----------: |
| id                    | UUID     |         Sim |
| empresa_id            | UUID     |         Sim |
| nome                  | VARCHAR  |         Sim |
| descricao             | TEXT     |         Não |
| data_inicio           | DATE     |         Sim |
| data_previsao_termino | DATE     |         Não |
| data_termino          | DATE     |         Não |
| status                | ENUM     |         Sim |
| prioridade            | ENUM     |         Sim |
| responsavel_id        | UUID     |         Sim |
| data_criacao          | DATETIME |         Sim |
| data_atualizacao      | DATETIME |         Sim |

## Status

```text
PLANEJAMENTO
EM_ANDAMENTO
PAUSADO
CONCLUIDO
CANCELADO
```

## Prioridade

```text
BAIXA
MEDIA
ALTA
CRITICA
```

---

# 16. Tarefa

## Tabela

```text
tarefa
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| titulo           | VARCHAR  |         Sim |
| descricao        | TEXT     |         Não |
| status           | ENUM     |         Sim |
| prioridade       | ENUM     |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_inicio      | DATETIME |         Não |
| data_prazo       | DATETIME |         Não |
| data_conclusao   | DATETIME |         Não |
| estimativa_horas | DECIMAL  |         Não |
| projeto_id       | UUID     |         Não |
| responsavel_id   | UUID     |         Sim |
| criado_por       | UUID     |         Sim |
| data_atualizacao | DATETIME |         Sim |

Projeto é opcional.

Isso permite atividades independentes.

---

# 17. Histórico da Tarefa

## Tabela

```text
historico_tarefa
```

## Atributos

| Campo           | Tipo     | Obrigatório |
| --------------- | -------- | ----------: |
| id              | UUID     |         Sim |
| tarefa_id       | UUID     |         Sim |
| usuario_id      | UUID     |         Sim |
| status_anterior | ENUM     |         Sim |
| status_novo     | ENUM     |         Sim |
| observacao      | TEXT     |         Não |
| data_hora       | DATETIME |         Sim |

O histórico deve ser tratado como registro imutável.

---

# 18. Reunião

## Tabela

```text
reuniao
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| empresa_id       | UUID     |         Sim |
| titulo           | VARCHAR  |         Sim |
| descricao        | TEXT     |         Não |
| data_inicio      | DATETIME |         Sim |
| data_fim         | DATETIME |         Sim |
| local            | VARCHAR  |         Não |
| link             | VARCHAR  |         Não |
| status           | ENUM     |         Sim |
| organizador_id   | UUID     |         Sim |
| projeto_id       | UUID     |         Não |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Status

```text
AGENDADA
EM_ANDAMENTO
CONCLUIDA
CANCELADA
```

---

# 19. Participante da Reunião

## Tabela

```text
participante_reuniao
```

## Atributos

| Campo          | Tipo     | Obrigatório |
| -------------- | -------- | ----------: |
| id             | UUID     |         Sim |
| reuniao_id     | UUID     |         Sim |
| funcionario_id | UUID     |         Sim |
| status         | ENUM     |         Sim |
| data_resposta  | DATETIME |         Não |

## Status

```text
PENDENTE
ACEITA
RECUSADA
```

## Restrição

A combinação:

```text
reuniao_id + funcionario_id
```

deve ser única.

---

# 20. Cliente

## Tabela

```text
cliente
```

## Atributos

| Campo         | Tipo     | Obrigatório |
| ------------- | -------- | ----------: |
| id            | UUID     |         Sim |
| empresa_id    | UUID     |         Sim |
| tipo_pessoa   | ENUM     |         Sim |
| nome          | VARCHAR  |         Sim |
| nome_fantasia | VARCHAR  |         Não |
| documento     | VARCHAR  |         Sim |
| email         | VARCHAR  |         Não |
| telefone      | VARCHAR  |         Não |
| status        | ENUM     |         Sim |
| observacoes   | TEXT     |         Não |
| criado_em     | DATETIME |         Sim |
| atualizado_em | DATETIME |         Sim |

## Status

```text
ATIVO
INATIVO
```

---

# 21. Fornecedor

## Tabela

```text
fornecedor
```

Estrutura semelhante a Cliente.

## Atributos

| Campo         | Tipo     | Obrigatório |
| ------------- | -------- | ----------: |
| id            | UUID     |         Sim |
| empresa_id    | UUID     |         Sim |
| tipo_pessoa   | ENUM     |         Sim |
| nome          | VARCHAR  |         Sim |
| nome_fantasia | VARCHAR  |         Não |
| documento     | VARCHAR  |         Sim |
| email         | VARCHAR  |         Não |
| telefone      | VARCHAR  |         Não |
| status        | ENUM     |         Sim |
| observacoes   | TEXT     |         Não |
| criado_em     | DATETIME |         Sim |
| atualizado_em | DATETIME |         Sim |

---

# 22. Categoria

## Tabela

```text
categoria
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| empresa_id       | UUID     |         Sim |
| nome             | VARCHAR  |         Sim |
| descricao        | TEXT     |         Não |
| status           | ENUM     |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

---

# 23. Produto

## Tabela

```text
produto
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| empresa_id       | UUID     |         Sim |
| categoria_id     | UUID     |         Sim |
| sku              | VARCHAR  |         Não |
| codigo_barras    | VARCHAR  |         Não |
| nome             | VARCHAR  |         Sim |
| descricao        | TEXT     |         Não |
| tipo             | ENUM     |         Sim |
| preco_venda      | DECIMAL  |         Não |
| custo_atual      | DECIMAL  |         Não |
| unidade_medida   | ENUM     |         Sim |
| status           | ENUM     |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Tipo

```text
PRODUTO
SERVICO
```

## Unidade de medida

```text
UNIDADE
CAIXA
PACOTE
QUILOGRAMA
GRAMA
LITRO
METRO
HORA
```

## Status

```text
ATIVO
INATIVO
```

## Restrições

Quando informados:

```text
sku → UNIQUE por empresa
codigo_barras → UNIQUE por empresa
```

---

# 24. Estoque

## Tabela

```text
estoque
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| produto_id       | UUID     |         Sim |
| quantidade_atual | DECIMAL  |         Sim |
| estoque_minimo   | DECIMAL  |         Sim |
| estoque_maximo   | DECIMAL  |         Não |
| localizacao      | VARCHAR  |         Não |
| status           | ENUM     |         Sim |
| version          | BIGINT   |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Restrição

Um produto possui no máximo um registro de estoque no modelo inicial.

```text
UNIQUE(produto_id)
```

Serviços não devem possuir estoque operacional.

---

# 25. Movimentação de Estoque

## Tabela

```text
movimentacao_estoque
```

## Atributos

| Campo                | Tipo         | Obrigatório |
| -------------------- | ------------ | ----------: |
| id                   | UUID         |         Sim |
| estoque_id           | UUID         |         Sim |
| tipo                 | ENUM         |         Sim |
| quantidade           | DECIMAL      |         Sim |
| quantidade_anterior  | DECIMAL      |         Sim |
| quantidade_posterior | DECIMAL      |         Sim |
| motivo               | VARCHAR/TEXT |         Não |
| responsavel_id       | UUID         |         Sim |
| data_movimentacao    | DATETIME     |         Sim |

## Tipo

```text
ENTRADA
SAIDA
AJUSTE
```

Os registros históricos são imutáveis.

---

# 26. Venda

## Tabela

```text
venda
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| numero           | VARCHAR  |         Sim |
| empresa_id       | UUID     |         Sim |
| cliente_id       | UUID     |         Sim |
| responsavel_id   | UUID     |         Sim |
| campanha_id      | UUID     |         Não |
| status           | ENUM     |         Sim |
| data_venda       | DATETIME |         Sim |
| subtotal         | DECIMAL  |         Sim |
| desconto         | DECIMAL  |         Sim |
| total            | DECIMAL  |         Sim |
| observacoes      | TEXT     |         Não |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

`campanha_id` permanece opcional para futura integração com Marketing.

---

# 27. Item da Venda

## Tabela

```text
item_venda
```

## Atributos

| Campo          | Tipo    | Obrigatório |
| -------------- | ------- | ----------: |
| id             | UUID    |         Sim |
| venda_id       | UUID    |         Sim |
| produto_id     | UUID    |         Sim |
| quantidade     | DECIMAL |         Sim |
| preco_unitario | DECIMAL |         Sim |
| desconto       | DECIMAL |         Sim |
| subtotal       | DECIMAL |         Sim |

O preço utilizado deve permanecer armazenado no item para preservar o histórico.

---

# 28. Compra

## Tabela

```text
compra
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| numero           | VARCHAR  |         Sim |
| empresa_id       | UUID     |         Sim |
| fornecedor_id    | UUID     |         Sim |
| responsavel_id   | UUID     |         Sim |
| status           | ENUM     |         Sim |
| data_compra      | DATETIME |         Sim |
| subtotal         | DECIMAL  |         Sim |
| desconto         | DECIMAL  |         Sim |
| total            | DECIMAL  |         Sim |
| observacoes      | TEXT     |         Não |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

---

# 29. Item da Compra

## Tabela

```text
item_compra
```

## Atributos

| Campo          | Tipo    | Obrigatório |
| -------------- | ------- | ----------: |
| id             | UUID    |         Sim |
| compra_id      | UUID    |         Sim |
| produto_id     | UUID    |         Sim |
| quantidade     | DECIMAL |         Sim |
| preco_unitario | DECIMAL |         Sim |
| desconto       | DECIMAL |         Sim |
| subtotal       | DECIMAL |         Sim |

O preço histórico é preservado no item.

---

# 30. Conta Financeira

## Tabela

```text
conta_financeira
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| empresa_id       | UUID     |         Sim |
| nome             | VARCHAR  |         Sim |
| descricao        | TEXT     |         Não |
| tipo             | ENUM     |         Sim |
| saldo_atual      | DECIMAL  |         Sim |
| status           | ENUM     |         Sim |
| version          | BIGINT   |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Tipos

```text
CONTA_CORRENTE
CONTA_POUPANCA
CAIXA
OUTRA
```

## Status

```text
ATIVA
INATIVA
```

---

# 31. Categoria Financeira

## Tabela

```text
categoria_financeira
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| empresa_id       | UUID     |         Sim |
| nome             | VARCHAR  |         Sim |
| descricao        | TEXT     |         Não |
| tipo             | ENUM     |         Sim |
| status           | ENUM     |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Tipo

```text
RECEITA
DESPESA
```

---

# 32. Conta a Receber

## Tabela

```text
conta_a_receber
```

## Atributos

| Campo                   | Tipo     | Obrigatório |
| ----------------------- | -------- | ----------: |
| id                      | UUID     |         Sim |
| empresa_id              | UUID     |         Sim |
| venda_id                | UUID     |         Não |
| cliente_id              | UUID     |         Não |
| categoria_financeira_id | UUID     |         Sim |
| valor_total             | DECIMAL  |         Sim |
| status                  | ENUM     |         Sim |
| descricao               | TEXT     |         Não |
| data_emissao            | DATE     |         Não |
| data_criacao            | DATETIME |         Sim |
| data_atualizacao        | DATETIME |         Sim |

Uma conta a receber pode existir sem venda quando o domínio permitir lançamentos independentes.

---

# 33. Conta a Pagar

## Tabela

```text
conta_a_pagar
```

## Atributos

| Campo                   | Tipo     | Obrigatório |
| ----------------------- | -------- | ----------: |
| id                      | UUID     |         Sim |
| empresa_id              | UUID     |         Sim |
| compra_id               | UUID     |         Não |
| fornecedor_id           | UUID     |         Não |
| categoria_financeira_id | UUID     |         Sim |
| valor_total             | DECIMAL  |         Sim |
| status                  | ENUM     |         Sim |
| descricao               | TEXT     |         Não |
| data_emissao            | DATE     |         Não |
| data_criacao            | DATETIME |         Sim |
| data_atualizacao        | DATETIME |         Sim |

---

# 34. Parcela

## Tabela

```text
parcela
```

## Atributos

| Campo            | Tipo     | Obrigatório |
| ---------------- | -------- | ----------: |
| id               | UUID     |         Sim |
| conta_receber_id | UUID     | Condicional |
| conta_pagar_id   | UUID     | Condicional |
| numero           | INT      |         Sim |
| valor            | DECIMAL  |         Sim |
| data_vencimento  | DATE     |         Sim |
| status           | ENUM     |         Sim |
| data_criacao     | DATETIME |         Sim |
| data_atualizacao | DATETIME |         Sim |

## Regra estrutural

Exatamente um dos campos abaixo deve estar preenchido:

```text
conta_receber_id
conta_pagar_id
```

Não é permitido:

```text
ambos preenchidos
```

nem:

```text
ambos vazios
```

## Restrição

```text
UNIQUE(conta_receber_id, numero)
UNIQUE(conta_pagar_id, numero)
```

A implementação definitiva da constraint de exclusividade deverá considerar as características do MySQL e da estratégia de modelagem adotada.

---

# 35. Pagamento

## Tabela

```text
pagamento
```

## Atributos

| Campo               | Tipo     | Obrigatório |
| ------------------- | -------- | ----------: |
| id                  | UUID     |         Sim |
| parcela_id          | UUID     |         Sim |
| conta_financeira_id | UUID     |         Sim |
| valor               | DECIMAL  |         Sim |
| data_pagamento      | DATETIME |         Sim |
| forma_pagamento     | ENUM     |         Sim |
| responsavel_id      | UUID     |         Sim |
| observacoes         | TEXT     |         Não |
| idempotency_key     | VARCHAR  |         Sim |
| data_criacao        | DATETIME |         Sim |

A chave de idempotência deve possuir proteção de unicidade conforme o escopo definido.

---

# 36. Forma de Pagamento

```text
DINHEIRO
PIX
CARTAO_CREDITO
CARTAO_DEBITO
BOLETO
TRANSFERENCIA
OUTRA
```

---

# 37. Movimentação Financeira

## Tabela

```text
movimentacao_conta
```

## Atributos

| Campo               | Tipo     | Obrigatório |
| ------------------- | -------- | ----------: |
| id                  | UUID     |         Sim |
| conta_financeira_id | UUID     |         Sim |
| pagamento_id        | UUID     |         Não |
| tipo                | ENUM     |         Sim |
| valor               | DECIMAL  |         Sim |
| saldo_anterior      | DECIMAL  |         Sim |
| saldo_posterior     | DECIMAL  |         Sim |
| descricao           | TEXT     |         Não |
| responsavel_id      | UUID     |         Sim |
| data_movimentacao   | DATETIME |         Sim |

## Tipo

```text
ENTRADA
SAIDA
```

Movimentações são históricas e não devem ser editadas diretamente.

---

# 38. Idempotência

As seguintes entidades/operações devem possuir controle de idempotência quando aplicável:

* pagamentos;
* recebimentos;
* vendas;
* compras;
* movimentações de estoque.

Uma estratégia possível é utilizar uma chave:

```text
idempotency_key
```

associada ao contexto da operação.

A estrutura definitiva deve impedir duas operações efetivas originadas da mesma chave.

---

# 39. Auditoria

## Tabela

```text
auditoria
```

## Atributos

| Campo       | Tipo      | Obrigatório |
| ----------- | --------- | ----------: |
| id          | UUID      |         Sim |
| usuario_id  | UUID      |         Não |
| acao        | VARCHAR   |         Sim |
| entidade    | VARCHAR   |         Sim |
| entidade_id | UUID      |         Não |
| data_hora   | DATETIME  |         Sim |
| request_id  | VARCHAR   |         Não |
| ip          | VARCHAR   |         Não |
| resultado   | ENUM      |         Sim |
| detalhes    | TEXT/JSON |         Não |

## Resultado

```text
SUCESSO
FALHA
```

A estrutura de `detalhes` deve ser cuidadosamente controlada para impedir armazenamento de dados sensíveis.

---

# 40. Restrições de Auditoria

Não devem ser armazenados:

```text
senha
senha_hash
JWT
refresh_token
credenciais
segredos
```

A auditoria é histórica e não deve ser editada por operações normais.

---

# 41. Índices

Índices devem ser criados para campos utilizados frequentemente em:

* busca;
* filtro;
* relacionamento;
* ordenação.

Exemplos:

```text
funcionario.cpf
funcionario.matricula
usuario.email
produto.sku
produto.codigo_barras
venda.numero
compra.numero
conta_receber.status
conta_pagar.status
parcela.data_vencimento
movimentacao_conta.data_movimentacao
movimentacao_estoque.data_movimentacao
auditoria.data_hora
auditoria.request_id
```

Os índices definitivos devem ser avaliados de acordo com consultas reais.

---

# 42. Unicidade

Restrições de unicidade esperadas:

| Entidade             | Campo/combinação               |
| -------------------- | ------------------------------ |
| Empresa              | CNPJ                           |
| Funcionário          | Matrícula                      |
| Funcionário          | CPF                            |
| Usuário              | Funcionário                    |
| Usuário              | E-mail, conforme regra adotada |
| Permissão            | Módulo + Ação                  |
| Usuário_Perfil       | Usuário + Perfil               |
| Perfil_Permissão     | Perfil + Permissão             |
| Participante_Reunião | Reunião + Funcionário          |
| Produto              | SKU por empresa                |
| Produto              | Código de barras por empresa   |
| Estoque              | Produto                        |
| Venda                | Número por empresa             |
| Compra               | Número por empresa             |
| Parcela              | Número dentro da obrigação     |
| Idempotência         | Chave no contexto definido     |

---

# 43. Integridade Referencial

Foreign keys devem impedir referências inválidas.

Exemplo:

```text
item_venda.produto_id
        ↓
produto.id
```

Um ItemVenda não deve apontar para produto inexistente.

O mesmo princípio se aplica a todos os relacionamentos estruturais.

---

# 44. Regras de Exclusão

Registros históricos importantes não devem ser excluídos fisicamente.

Especialmente:

* vendas concluídas;
* compras concluídas;
* pagamentos;
* movimentações financeiras;
* movimentações de estoque;
* auditoria;
* histórico de tarefas.

Quando o domínio permitir retirada lógica, deve ser utilizado status apropriado.

---

# 45. Concorrência

As seguintes entidades devem possuir atenção especial:

```text
estoque
conta_financeira
```

A versão:

```text
version
```

será utilizada para optimistic locking quando aplicável.

A operação deve:

1. carregar estado;
2. validar estado;
3. executar alteração;
4. persistir;
5. detectar conflito;
6. tratar conflito.

---

# 46. Histórico Financeiro

O histórico deve permitir determinar:

```text
saldo anterior
        ↓
movimentação
        ↓
saldo posterior
```

Para entrada:

```text
saldo_posterior =
saldo_anterior + valor
```

Para saída:

```text
saldo_posterior =
saldo_anterior - valor
```

---

# 47. Histórico de Estoque

A movimentação deve permitir reconstruir:

```text
quantidade anterior
       ↓
operação
       ↓
quantidade posterior
```

Para entrada:

```text
posterior =
anterior + quantidade
```

Para saída:

```text
posterior =
anterior - quantidade
```

Para ajuste:

```text
posterior =
valor definido pelo ajuste
```

observando as regras de domínio.

---

# 48. Integridade Comercial

## Venda

```text
Venda 1:N ItemVenda
```

## Compra

```text
Compra 1:N ItemCompra
```

Uma operação confirmada deve possuir pelo menos um item.

Itens preservam o preço histórico.

---

# 49. Integridade Financeira

A estrutura:

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

não representa uma única tabela ou operação.

São fatos de domínio relacionados.

A existência de um registro de venda não deve automaticamente significar que um pagamento ocorreu.

---

# 50. Relacionamento entre Venda e Conta a Receber

O relacionamento é opcional:

```text
Venda 1:0..1 ContaAReceber
```

quando o modelo de negócio determinar uma única obrigação financeira associada à venda.

A modelagem deve permitir evolução futura caso uma venda possa gerar mais de uma obrigação.

---

# 51. Relacionamento entre Compra e Conta a Pagar

Analogamente:

```text
Compra 1:0..1 ContaAPagar
```

quando aplicável.

A associação não significa que o pagamento já ocorreu.

---

# 52. Relacionamentos Principais

## Organização

```text
Empresa 1:N Departamento
Empresa 1:N Cargo
Empresa 1:N Funcionario

Departamento 1:N Funcionario
Cargo 1:N Funcionario

Funcionario 1:0..1 Usuario
Usuario N:N Perfil
Perfil N:N Permissao
```

## Operação

```text
Funcionario 1:N Projeto
Projeto 1:N Tarefa
Funcionario 1:N Tarefa

Funcionario 1:N Reuniao
Reuniao N:N Funcionario
```

## Comercial

```text
Cliente 1:N Venda
Venda 1:N ItemVenda
Produto 1:N ItemVenda

Fornecedor 1:N Compra
Compra 1:N ItemCompra
Produto 1:N ItemCompra
```

## Estoque

```text
Produto 1:0..1 Estoque
Estoque 1:N MovimentacaoEstoque
```

## Financeiro

```text
ContaFinanceira 1:N MovimentacaoConta

ContaAReceber 1:N Parcela
ContaAPagar 1:N Parcela

Parcela 1:N Pagamento

Pagamento 1:0..1 MovimentacaoConta
```

---

# 53. Dados Derivados

Os seguintes valores não devem ser armazenados como fonte independente quando puderem ser derivados com segurança:

* progresso do projeto;
* margem do produto;
* total histórico recalculável de operações;
* indicadores gerenciais.

Entretanto, valores históricos necessários para preservar o fato ocorrido devem ser armazenados.

Exemplo:

```text
ItemVenda.preco_unitario
```

deve permanecer armazenado porque representa o preço praticado naquela venda.

---

# 54. Campos Calculados

Exemplos de valores derivados:

```text
subtotal = Σ itens
total = subtotal - desconto
```

O cliente não deve ser a autoridade desses valores.

O servidor deve calculá-los ou validá-los.

---

# 55. Soft Delete e Status

O sistema não utilizará exclusão física como mecanismo padrão para históricos críticos.

Quando o domínio permitir:

```text
ATIVO
INATIVO
```

será utilizado.

Para entidades com workflow, o próprio status de negócio será utilizado.

---

# 56. Migrations

As tabelas serão criadas e alteradas exclusivamente por migrations versionadas.

Estrutura:

```text
src/main/resources/db/migration/
```

Exemplo:

```text
V1__criacao_empresa.sql
V2__criacao_estrutura_organizacional.sql
V3__criacao_usuarios_perfis_permissoes.sql
V4__criacao_clientes_fornecedores.sql
V5__criacao_produtos_estoque.sql
V6__criacao_vendas_compras.sql
V7__criacao_financeiro.sql
V8__criacao_auditoria.sql
```

A divisão exata das migrations será definida durante a implementação.

---

# 57. Ordem Recomendada de Criação das Tabelas

A ordem estrutural recomendada é:

```text
1. empresa

2. departamento
3. cargo

4. funcionario

5. usuario
6. perfil
7. permissao
8. usuario_perfil
9. perfil_permissao

10. endereco
11. funcionario_endereco

12. projeto
13. tarefa
14. historico_tarefa

15. reuniao
16. participante_reuniao

17. cliente
18. cliente_endereco

19. fornecedor
20. fornecedor_endereco

21. categoria
22. produto
23. estoque
24. movimentacao_estoque

25. venda
26. item_venda

27. compra
28. item_compra

29. conta_financeira
30. categoria_financeira

31. conta_a_receber
32. conta_a_pagar
33. parcela
34. pagamento
35. movimentacao_conta

36. auditoria
```

---

# 58. Integridade por Camadas

O sistema deverá proteger o domínio em três níveis:

```text
API
 ↓
Regras de negócio
 ↓
Banco
```

Exemplo:

```text
@Positive
     ↓
Service valida saldo
     ↓
Database constraints
```

Nenhum nível deve ser tratado como substituto absoluto dos outros.

---

# 59. Modelo de Dados e Segurança

O modelo deve evitar:

* armazenamento de senha em texto;
* armazenamento de tokens;
* armazenamento desnecessário de dados sensíveis;
* relações polimórficas sem integridade;
* campos administrativos controlados diretamente pelo cliente;
* exclusão física de fatos históricos importantes.

---

# 60. Decisões Pendentes para Implementação

Alguns detalhes poderão ser refinados durante a implementação sem alterar o domínio principal:

* precisão definitiva dos campos monetários;
* tamanho máximo dos campos textuais;
* estratégia final de UUID no MySQL;
* estratégia de timestamps;
* convenção definitiva de nomes de tabelas;
* estratégia de índices composta;
* implementação técnica da exclusividade da `Parcela`;
* estratégia de idempotência;
* estratégia de auditoria de falhas;
* política de retenção da auditoria.

Essas decisões devem ser documentadas quando forem tomadas.

---

# 61. Estado do Documento

**Documento:** Modelo de Dados
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Banco:** MySQL 8
**Estado:** Modelo conceitual e lógico inicial consolidado

Este documento deve ser atualizado sempre que uma decisão de domínio alterar entidades, atributos, relacionamentos ou regras de integridade.
