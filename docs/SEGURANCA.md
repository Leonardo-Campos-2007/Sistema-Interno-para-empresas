# SEGURANÇA

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Arquitetura:** Monólito Modular
**Framework de segurança:** Spring Security
**Autenticação:** JWT
**Banco de dados:** MySQL 8

---

# 1. Objetivo

Este documento define os requisitos, controles e decisões de segurança do Sistema Interno para Empresas.

Segurança será tratada como requisito estrutural do sistema, e não como uma funcionalidade adicionada posteriormente.

O objetivo é proteger:

* contas de usuários;
* dados empresariais;
* dados pessoais;
* informações financeiras;
* dados comerciais;
* estoque;
* operações administrativas;
* histórico e auditoria.

---

# 2. Princípios de Segurança

O sistema seguirá os seguintes princípios:

### Menor privilégio

Cada usuário deve possuir somente os acessos necessários para sua função.

### Negação por padrão

Recursos protegidos devem ser negados quando não houver autorização explícita.

### Defesa em profundidade

A segurança será aplicada em múltiplas camadas:

```text id="5n9e2c"
Cliente
   ↓
API
   ↓
Spring Security
   ↓
Autorização
   ↓
Service
   ↓
Validação de domínio
   ↓
Banco de dados
```

### Zero Trust no cliente

Dados enviados pelo cliente não devem ser considerados confiáveis.

### Segurança por padrão

Novos endpoints e funcionalidades devem nascer com controles de segurança apropriados.

### Rastreabilidade

Operações relevantes devem ser identificáveis e auditáveis.

---

# 3. Modelo de Segurança

O modelo será baseado em:

**Autenticação + RBAC + Autorização Contextual**

```text id="5un4t4"
Usuário
   ↓
Perfil
   ↓
Permissão
   ↓
Ação
   ↓
Recurso
```

A permissão funcional sozinha não garante acesso a qualquer objeto.

---

# 4. Autenticação

A autenticação será implementada utilizando:

* Spring Security;
* JWT;
* senha armazenada como hash;
* políticas de expiração;
* controle de estado da conta.

Fluxo:

```text id="gx66z1"
Cliente
   ↓
POST /auth/login
   ↓
Spring Security
   ↓
Validação de credenciais
   ↓
JWT
   ↓
Cliente
```

---

# 5. Senhas

Senhas jamais devem ser armazenadas em texto puro.

O sistema deve utilizar algoritmo de hashing apropriado para senhas.

Exemplo de abordagem:

```java id="qyli7t"
PasswordEncoder
```

A senha original não deve:

* ser armazenada;
* aparecer em logs;
* aparecer em auditoria;
* ser retornada pela API;
* ser armazenada em cache de forma insegura.

---

# 6. JWT

O token deve possuir configuração apropriada de:

* assinatura;
* algoritmo;
* expiração;
* emissor quando aplicável;
* subject;
* claims mínimos;
* validação.

Não devem ser colocadas no token informações desnecessárias ou dados sensíveis.

O servidor deve validar o token antes de processar recursos protegidos.

---

# 7. Estado do Usuário

O acesso deve respeitar o estado da conta.

Estados:

```text id="4hyvpf"
ATIVO
BLOQUEADO
INATIVO
```

### ATIVO

Pode autenticar e executar operações conforme suas permissões.

### BLOQUEADO

Não pode autenticar enquanto estiver bloqueado.

### INATIVO

Não pode autenticar.

---

# 8. Funcionário x Usuário

Funcionário e usuário são conceitos distintos.

```text id="bvn2dd"
Funcionário
    │
    └──── 0..1
           │
         Usuário
```

Um funcionário pode existir sem conta de acesso.

Cada funcionário pode possuir no máximo um usuário.

---

# 9. Perfis

Perfis representam conjuntos de permissões.

Perfis iniciais:

* ADMINISTRADOR;
* GESTOR;
* FUNCIONARIO;
* FINANCEIRO;
* RH;
* MARKETING.

Esses perfis são configurações iniciais e não substituem a necessidade de autorização contextual.

---

# 10. Permissões

Cada permissão será composta conceitualmente por:

```text id="vm53nt"
MÓDULO + AÇÃO
```

Exemplo:

```text
VENDAS + VISUALIZAR
```

ou:

```text
ESTOQUE + AJUSTAR
```

A combinação módulo + ação deve possuir unicidade lógica.

---

# 11. Autorização Funcional

A autorização funcional verifica se o usuário possui a permissão necessária.

Exemplo:

```text id="xqgkuo"
VENDAS:CRIAR
```

Permite criação de vendas.

Isso não implica automaticamente:

```text
VENDAS:CANCELAR
```

---

# 12. Autorização Contextual

A autorização contextual verifica se o usuário pode operar sobre um recurso específico.

Exemplo:

```text id="g7t37c"
Usuário:
TAREFAS:EDITAR
```

Não significa:

```text
pode editar qualquer tarefa.
```

O sistema deve verificar também:

* quem criou a tarefa;
* responsável;
* departamento;
* equipe;
* papel do usuário;
* contexto organizacional;
* regras específicas do domínio.

---

# 13. BOLA / IDOR

O sistema deve ser protegido contra Broken Object Level Authorization.

Nunca utilizar apenas:

```java id="yk62ww"
repository.findById(id)
```

como prova de autorização.

Fluxo esperado:

```text id="a2yz5g"
ID recebido
    ↓
Autenticação
    ↓
Permissão
    ↓
Carregar recurso
    ↓
Verificar autorização contextual
    ↓
Executar operação
```

---

# 14. Broken Function Level Authorization

O sistema deve impedir que usuários executem funções administrativas apenas porque conhecem a URL do endpoint.

Exemplo:

```text
POST /usuarios
```

deve exigir a permissão correspondente.

A segurança não pode depender apenas da interface do cliente.

---

# 15. Broken Object Property Authorization

O sistema deve controlar quais propriedades podem ser alteradas por cada operação.

Exemplo:

Uma atualização comum de usuário não deve permitir que o cliente altere arbitrariamente:

```json id="1voh2z"
{
  "perfil": "ADMINISTRADOR"
}
```

Campos administrativos devem ser controlados por casos de uso específicos.

---

# 16. Mass Assignment

DTOs específicos devem ser utilizados para impedir que o cliente envie propriedades que não deveria controlar.

Exemplo:

```text id="fuprn0"
UsuarioUpdateRequest
```

não deve conter campos administrativos não necessários.

---

# 17. Validação de Entrada

Toda entrada externa deve ser validada.

Devem ser considerados:

* formato;
* tamanho;
* obrigatoriedade;
* intervalo;
* enum;
* datas;
* valores monetários;
* quantidade;
* identificadores.

Exemplos:

```java id="c3mlhb"
@NotBlank
@Email
@Positive
@Size
@Valid
```

---

# 18. SQL Injection

Queries devem utilizar mecanismos seguros de persistência.

É proibida a construção insegura de SQL baseada diretamente em dados recebidos do cliente.

Devem ser utilizados:

* Spring Data JPA;
* parâmetros;
* queries parametrizadas;
* Criteria/Specifications quando apropriado.

---

# 19. XSS

Dados fornecidos por usuários devem ser tratados de forma que não provoquem execução indevida quando posteriormente apresentados por uma interface.

A API deve evitar retornar dados em contextos que permitam interpretação indevida como código.

---

# 20. CSRF

A proteção contra CSRF deve considerar o mecanismo de autenticação adotado.

Caso o sistema utilize tokens enviados de maneira que não dependam automaticamente de cookies de autenticação, o risco será diferente de uma aplicação baseada exclusivamente em sessão/cookie.

A configuração definitiva será documentada juntamente com a implementação da autenticação.

---

# 21. Brute Force

O endpoint de autenticação deve possuir proteção contra tentativas excessivas.

Controles possíveis:

* rate limiting;
* bloqueio temporário;
* monitoramento;
* políticas de tentativa;
* mecanismos de detecção de abuso.

---

# 22. Credential Stuffing

O sistema deve considerar ataques que utilizem credenciais vazadas de outros serviços.

Devem ser utilizados controles como:

* proteção contra tentativas automatizadas;
* rate limiting;
* política de senha adequada;
* monitoramento;
* bloqueios quando necessário.

---

# 23. Rate Limiting

Endpoints sensíveis devem possuir limites apropriados.

Especial atenção para:

* login;
* recuperação de acesso;
* operações financeiras;
* operações de estoque;
* endpoints de consulta potencialmente pesados.

---

# 24. Resource Consumption

O sistema deve impedir utilização abusiva de recursos.

Devem existir limites para:

* tamanho de requisição;
* tamanho de página;
* quantidade de registros;
* filtros excessivamente custosos;
* operações repetitivas.

---

# 25. Paginação

Endpoints de consulta não devem retornar quantidade arbitrária de dados.

Exemplo:

```text id="yd8d38"
GET /clientes?page=0&size=20
```

O servidor deve impor limite máximo.

---

# 26. Excesso de Dados

A API deve retornar somente os dados necessários.

Não deve retornar diretamente Entities.

Exemplo inadequado:

```java id="m2l5j1"
return funcionario;
```

Exemplo esperado:

```text id="x4b3m6"
Entity
 ↓
Mapper
 ↓
FuncionarioResponse
 ↓
API
```

---

# 27. Enum e ID Manipulation

IDs e enums recebidos do cliente devem ser validados.

O sistema não deve assumir que:

```text
id = 1
```

significa que o usuário tem direito ao registro.

Da mesma forma, valores como:

```text
role=ADMIN
```

não devem alterar privilégios sem autorização.

---

# 28. Enumeration

O sistema deve evitar revelar informações desnecessárias sobre:

* existência de usuários;
* existência de clientes;
* existência de contas;
* IDs internos;
* recursos não autorizados.

Respostas de erro devem ser cuidadosamente controladas.

---

# 29. Error Handling

Mensagens de erro não devem revelar:

* stack trace;
* SQL;
* classes internas;
* caminhos do servidor;
* credenciais;
* tokens;
* detalhes desnecessários da infraestrutura.

A API deve utilizar respostas padronizadas.

---

# 30. CORS

CORS deve ser configurado explicitamente.

Não utilizar configuração permissiva indiscriminadamente.

Exemplo inadequado:

```text id="7wmjs3"
Access-Control-Allow-Origin: *
```

A configuração definitiva deve considerar os clientes autorizados do sistema.

---

# 31. Content-Type

A API deve validar os tipos de conteúdo aceitos.

Requisições inesperadas devem ser rejeitadas quando apropriado.

---

# 32. Logs

Logs devem ser utilizados para observabilidade e diagnóstico.

Não devem conter:

* senha;
* password hash quando desnecessário;
* JWT;
* refresh token;
* chaves;
* secrets;
* credenciais;
* dados pessoais desnecessários.

---

# 33. Auditoria

Auditoria e logs possuem funções diferentes.

### Log

Destinado a:

* diagnóstico;
* infraestrutura;
* observabilidade.

### Auditoria

Destinada a:

* ações de negócio;
* alterações relevantes;
* rastreabilidade;
* investigação.

Logs não substituem auditoria.

---

# 34. Auditoria de Operações Críticas

Devem ser auditadas, conforme aplicabilidade:

* criação;
* alteração;
* exclusão lógica;
* confirmação;
* conclusão;
* cancelamento;
* estorno;
* ajuste;
* alterações de permissões;
* alterações de usuários;
* operações financeiras;
* operações de estoque.

---

# 35. Dados da Auditoria

O registro deve poder conter:

* usuário;
* ação;
* entidade;
* entidadeId;
* data/hora;
* requestId;
* IP, quando aplicável;
* resultado;
* detalhes controlados.

Nunca registrar:

* senha;
* tokens;
* refresh tokens;
* credenciais;
* segredos.

---

# 36. Segurança do Estoque

Operações de estoque devem utilizar controles adicionais.

Fluxo:

```text id="giztzw"
Requisição
   ↓
Autenticação
   ↓
Autorização
   ↓
Validação
   ↓
Verificação do saldo
   ↓
Controle de concorrência
   ↓
Atualização
   ↓
Histórico
```

---

# 37. Segurança Financeira

Operações financeiras exigem controles reforçados.

Fluxo:

```text id="6x4knu"
Pagamento
   ↓
Autenticação
   ↓
Autorização
   ↓
Validação
   ↓
Verificação da parcela
   ↓
Verificação do saldo
   ↓
Controle de concorrência
   ↓
Movimentação
   ↓
Auditoria
```

---

# 38. Idempotência

Operações críticas devem suportar idempotência.

Principalmente:

* pagamentos;
* recebimentos;
* confirmação de venda;
* confirmação de compra;
* entrada de estoque;
* saída de estoque;
* ajustes críticos.

Objetivo:

```text id="ni4yz8"
Mesma requisição
      ↓
processada uma vez
```

Retry da mesma operação não deve produzir duplicidade.

---

# 39. Replay

Operações sensíveis devem considerar ataques de repetição.

Uma requisição capturada e reenviada não deve gerar novamente uma operação financeira ou de estoque.

A idempotência é uma das principais medidas contra esse cenário.

---

# 40. Concorrência

Operações concorrentes devem ser protegidas.

Especialmente:

* saldo de contas;
* pagamentos;
* estoque.

Devem ser utilizados mecanismos como:

* transações;
* optimistic locking;
* constraints;
* validação de estado;
* idempotência.

---

# 41. Saldo Financeiro

O cliente nunca deve poder simplesmente enviar:

```json id="e0s5o2"
{
  "saldoAtual": 100000
}
```

para modificar o saldo.

O saldo deve ser consequência de uma operação financeira válida.

---

# 42. Estoque

O cliente também não deve poder alterar arbitrariamente:

```json id="d2s2e0"
{
  "quantidadeAtual": 999999
}
```

A alteração deve acontecer por:

* entrada;
* saída;
* ajuste autorizado.

---

# 43. Workflow Bypass

O sistema deve impedir mudanças ilegais de estado.

Exemplo:

```text id="5g8r9f"
RASCUNHO
   ↓
CONCLUIDA
```

não deve ignorar as etapas ou validações necessárias do domínio.

O servidor deve controlar as transições permitidas.

---

# 44. Parameter Tampering

Valores críticos não devem depender do cliente.

Exemplos:

* preço;
* desconto;
* total;
* saldo;
* quantidade disponível;
* permissões;
* status administrativo.

O servidor deve recalcular ou validar esses valores.

---

# 45. Operações Fora de Ordem

O sistema deve impedir operações incompatíveis com o estado atual.

Exemplo:

```text id="jn5ncn"
Parcela CANCELADA
        ↓
Pagamento
```

deve ser rejeitado.

Outro exemplo:

```text id="0cviu2"
Venda CANCELADA
        ↓
Conclusão
```

deve ser rejeitado.

---

# 46. Segurança de Integrações

Quando uma operação afetar mais de um módulo, os controles devem ser aplicados à operação completa.

Exemplo:

```text id="k2d9w7"
Venda
 ├── Estoque
 └── Financeiro
```

Não deve existir mecanismo que permita ao usuário contornar a validação de um dos módulos executando chamadas separadas.

---

# 47. Princípio de autoridade no servidor

O cliente pode solicitar:

```text
"confirme esta venda"
```

mas não deve determinar:

```text
"confirme esta venda e defina o estoque como 500"
```

O servidor decide o resultado final conforme o estado real do domínio.

---

# 48. Segurança de Banco

As credenciais do MySQL não devem ser armazenadas no código-fonte.

Devem ser utilizadas configurações externas.

O usuário da aplicação deve possuir somente os privilégios necessários.

Quando possível:

* evitar usuário com privilégios administrativos;
* restringir acesso;
* utilizar credenciais diferentes por ambiente;
* proteger backups;
* monitorar acesso.

---

# 49. Proteção de Dados Pessoais

O sistema poderá tratar dados pessoais como:

* nome;
* CPF;
* e-mail;
* telefone;
* endereço.

O acesso deve respeitar:

* necessidade;
* autorização;
* finalidade;
* minimização;
* proteção contra exposição desnecessária.

---

# 50. Segurança de Backups

Backups podem conter dados sensíveis.

Devem possuir:

* acesso restrito;
* controle de armazenamento;
* política de retenção;
* proteção adequada;
* procedimento de recuperação.

---

# 51. Dependências de Terceiros

Dependências externas devem ser mantidas em versões suportadas.

O projeto deve considerar:

* vulnerabilidades conhecidas;
* atualizações;
* origem das dependências;
* integridade do build;
* análise de dependências.

---

# 52. Pipeline de Segurança

O pipeline deverá incorporar verificações de segurança quando possível.

Exemplos:

```text id="cl1d93"
Build
 ↓
Testes
 ↓
Análise estática
 ↓
Análise de dependências
 ↓
Testes de segurança
 ↓
Artefato
```

---

# 53. Matriz Inicial de Ameaças

| ID      | Ameaça                              | Controle principal                 |
| ------- | ----------------------------------- | ---------------------------------- |
| SEC-001 | BOLA / IDOR                         | Autorização contextual             |
| SEC-002 | Broken Function Level Authorization | RBAC                               |
| SEC-003 | Mass Assignment                     | DTOs                               |
| SEC-004 | SQL Injection                       | JPA / queries parametrizadas       |
| SEC-005 | XSS                                 | Validação / saída segura           |
| SEC-006 | CSRF                                | Configuração conforme autenticação |
| SEC-007 | Brute Force                         | Rate limiting                      |
| SEC-008 | Credential Stuffing                 | Rate limiting + monitoramento      |
| SEC-009 | JWT incorreto                       | Validação de assinatura/claims     |
| SEC-010 | Escalonamento de privilégio         | RBAC + autorização contextual      |
| SEC-011 | Excesso de dados                    | DTOs                               |
| SEC-012 | Resource Consumption                | Limites/paginação                  |
| SEC-013 | ID manipulation                     | Autorização contextual             |
| SEC-014 | Enumeration                         | Respostas controladas              |
| SEC-015 | Information Disclosure              | Error handling                     |
| SEC-016 | Race Condition                      | Locking/transações                 |
| SEC-017 | Business Logic Abuse                | Regras de domínio                  |
| SEC-018 | Parameter Tampering                 | Validação servidor                 |
| SEC-019 | Replay                              | Idempotência                       |
| SEC-020 | Duplicação financeira               | Idempotência                       |
| SEC-021 | Duplicação de estoque               | Idempotência                       |
| SEC-022 | Workflow Bypass                     | Máquina de estados                 |
| SEC-023 | Cross-object leakage                | Autorização contextual             |
| SEC-024 | Auditoria ausente                   | AuditService                       |
| SEC-025 | Sensitive Data Logging              | Sanitização                        |
| SEC-026 | CORS permissivo                     | Configuração restritiva            |
| SEC-027 | Content-Type abuse                  | Validação                          |
| SEC-028 | Requisição fora de ordem            | Validação de estado                |
| SEC-029 | Cache inseguro                      | Política de cache                  |
| SEC-030 | Upload inseguro                     | Validação, quando aplicável        |
| SEC-031 | Path Traversal                      | Validação, quando aplicável        |
| SEC-032 | SSRF                                | Validação, quando aplicável        |

---

# 54. Segurança por Endpoint

Cada endpoint deve ser avaliado considerando:

| Controle     | Pergunta                                     |
| ------------ | -------------------------------------------- |
| Autenticação | O usuário está autenticado?                  |
| Autorização  | Possui a permissão necessária?               |
| Contexto     | Pode acessar este recurso específico?        |
| Validação    | A entrada é válida?                          |
| Integridade  | A operação preserva as regras?               |
| Auditoria    | A operação precisa ser registrada?           |
| Idempotência | Pode ser repetida?                           |
| Concorrência | Pode ocorrer simultaneamente?                |
| Rate limit   | Pode ser abusada?                            |
| Exposição    | A resposta possui somente dados necessários? |

---

# 55. Endpoints de Autenticação

Endpoints de autenticação devem possuir controles reforçados.

Exemplos:

```text
POST /auth/login
POST /auth/logout
POST /auth/change-password
```

Devem ser avaliados para:

* brute force;
* credential stuffing;
* enumeration;
* rate limiting;
* exposição de informações;
* segurança de token.

---

# 56. Endpoints Financeiros

Endpoints relacionados a:

```text
POST /pagamentos
POST /recebimentos
POST /movimentacoes
```

devem exigir:

* autenticação;
* autorização;
* autorização contextual;
* validação;
* idempotência;
* auditoria;
* controle de concorrência;
* transação.

---

# 57. Endpoints de Estoque

Operações como:

```text
POST /estoque/entrada
POST /estoque/saida
POST /estoque/ajuste
```

devem exigir:

* autenticação;
* autorização;
* validação;
* controle de concorrência;
* idempotência;
* auditoria;
* proteção contra saldo negativo.

---

# 58. Segurança de Estados

Estados das entidades devem ser validados no servidor.

Exemplo:

```text id="w6dq0g"
RASCUNHO
   ↓
CONFIRMADA
   ↓
CONCLUIDA
```

Cada transição deve possuir condição válida.

Não deve ser possível enviar diretamente:

```json id="p7nryq"
{
  "status": "CONCLUIDA"
}
```

para contornar o fluxo.

---

# 59. Responsabilidades

## Controller

Responsável por:

* autenticação da camada HTTP;
* validação inicial;
* entrada e saída;
* encaminhamento.

## Service / Use Case

Responsável por:

* regras de negócio;
* autorização contextual;
* transações;
* integridade;
* operações críticas.

## Security

Responsável por:

* autenticação;
* JWT;
* principal;
* autorização.

## Banco

Responsável por reforçar:

* unicidade;
* integridade;
* relacionamento;
* consistência.

Nenhuma camada substitui completamente as demais.

---

# 60. Testes de Segurança

O projeto deverá possuir testes para cenários como:

### Autenticação

* usuário inexistente;
* senha incorreta;
* usuário bloqueado;
* usuário inativo;
* token inválido;
* token expirado.

### Autorização

* usuário sem permissão;
* permissão insuficiente;
* acesso a recurso de outro usuário;
* escalonamento de privilégio.

### API

* parâmetros inválidos;
* IDs manipulados;
* payload com campos proibidos;
* paginação abusiva;
* excesso de dados.

### Financeiro

* pagamento duplicado;
* pagamento excedente;
* pagamento concorrente;
* replay;
* operação fora de ordem.

### Estoque

* saída acima do saldo;
* ajuste não autorizado;
* movimentação duplicada;
* concorrência;
* replay.

---

# 61. Referências de Segurança

A implementação deverá considerar boas práticas e referências reconhecidas, especialmente:

* OWASP Top 10;
* OWASP API Security Top 10;
* OWASP ASVS;
* boas práticas do Spring Security;
* práticas de segurança para JWT;
* práticas de proteção de dados aplicáveis.

---

# 62. Estado do Documento

**Documento:** Segurança
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Estado:** Especificação inicial consolidada

Este documento deverá evoluir junto com a implementação de autenticação, autorização, API, auditoria e operações críticas.
