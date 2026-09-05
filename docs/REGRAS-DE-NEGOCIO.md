# REGRAS DE NEGÓCIO

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Arquitetura:** Monólito Modular

---

# 1. Objetivo

Este documento define as regras que determinam o comportamento do domínio do Sistema Interno de Gestão Empresarial.

As regras aqui descritas devem ser respeitadas independentemente da interface utilizada para acessar o sistema.

A existência de uma interface web, aplicação mobile ou API não altera as regras do domínio.

---

# 2. Convenções

| Termo        | Significado                                             |
| ------------ | ------------------------------------------------------- |
| Empresa      | Organização administrada pelo sistema                   |
| Funcionário  | Pessoa vinculada à estrutura organizacional             |
| Usuário      | Conta utilizada para acesso ao sistema                  |
| Perfil       | Conjunto de permissões                                  |
| Permissão    | Autorização para uma ação em determinado módulo         |
| Projeto      | Conjunto organizado de atividades                       |
| Tarefa       | Atividade executável                                    |
| Cliente      | Pessoa ou organização que compra da empresa             |
| Fornecedor   | Pessoa ou organização da qual a empresa compra          |
| Produto      | Item comercializado ou adquirido                        |
| Serviço      | Item comercializado que não possui estoque              |
| Venda        | Operação comercial de saída                             |
| Compra       | Operação comercial de aquisição                         |
| Obrigação    | Valor a receber ou pagar                                |
| Parcela      | Parte de uma obrigação                                  |
| Pagamento    | Liquidação parcial ou total de uma parcela              |
| Movimentação | Registro de alteração de estoque ou de saldo financeiro |

---

# 3. Regras Gerais

## RN-001 — Empresa da instalação

Cada instalação do sistema representa uma única empresa.

## RN-002 — Isolamento organizacional

Todos os registros pertencentes à empresa devem estar relacionados à empresa correta.

## RN-003 — Integridade referencial

Não deve ser permitido relacionar registros de entidades incompatíveis ou inexistentes.

## RN-004 — Registros inativos

A inativação de um registro não equivale necessariamente à sua exclusão física.

## RN-005 — Preservação histórica

Registros necessários para reconstrução do histórico operacional ou financeiro não devem ser apagados fisicamente.

## RN-006 — Validação no servidor

Regras de negócio devem ser validadas no servidor independentemente das validações realizadas pelo cliente.

## RN-007 — Cliente não confiável

Valores críticos enviados pelo cliente não devem ser considerados verdadeiros sem validação.

---

# 4. Empresa

## RN-008 — Cadastro da empresa

Uma empresa deve possuir, no mínimo:

* razão social;
* CNPJ;
* e-mail;
* status.

## RN-009 — Unicidade do CNPJ

O CNPJ da empresa deve ser único no sistema.

## RN-010 — Empresa ativa

Operações de negócio devem ocorrer somente enquanto a empresa estiver ativa.

## RN-011 — Empresa inativa

Uma empresa inativa não deve permitir novas operações incompatíveis com seu estado.

## RN-012 — Dados cadastrais

Alterações dos dados da empresa devem ser restritas a usuários autorizados.

---

# 5. Funcionários

## RN-013 — Cadastro de funcionário

Todo funcionário deve pertencer à empresa.

## RN-014 — Matrícula única

A matrícula de um funcionário deve ser única dentro da empresa.

## RN-015 — CPF único

O CPF de um funcionário deve ser único dentro da empresa.

## RN-016 — E-mail de funcionário

O e-mail de funcionário deve respeitar o formato válido e as regras de unicidade estabelecidas pelo sistema.

## RN-017 — Departamento válido

Quando informado, o departamento do funcionário deve pertencer à mesma empresa.

## RN-018 — Cargo válido

Quando informado, o cargo do funcionário deve pertencer à mesma empresa.

## RN-019 — Funcionário ativo

Funcionários ativos podem receber novas atribuições compatíveis com suas permissões.

## RN-020 — Funcionário desligado

Funcionários desligados não devem receber novas atribuições incompatíveis com seu estado.

## RN-021 — Data de desligamento

A data de desligamento não pode ser anterior à data de admissão.

## RN-022 — Status do funcionário

Os estados permitidos são:

* ATIVO;
* AFASTADO;
* FERIAS;
* DESLIGADO.

## RN-023 — Histórico do funcionário

Alterações de situação relevantes devem preservar rastreabilidade por meio dos mecanismos definidos pelo sistema.

---

# 6. Usuários

## RN-024 — Relação funcionário/usuário

Um usuário de negócio deve estar vinculado a um funcionário.

## RN-025 — Funcionário sem usuário

Um funcionário pode existir sem possuir uma conta de usuário.

## RN-026 — Um usuário por funcionário

Um funcionário pode possuir no máximo um usuário.

## RN-027 — Login

O e-mail utilizado como login deve identificar univocamente o usuário conforme as regras definidas pelo sistema.

## RN-028 — Senha

A senha nunca deve ser armazenada em texto puro.

## RN-029 — Usuário inativo

Usuários inativos não podem autenticar.

## RN-030 — Usuário bloqueado

Usuários bloqueados não podem autenticar enquanto permanecerem bloqueados.

## RN-031 — Usuário desligado

O acesso de um funcionário desligado deve ser revogado ou impedido conforme a política de segurança definida.

## RN-032 — Primeiro administrador

A instalação inicial deve possuir mecanismo de bootstrap capaz de criar o primeiro administrador válido.

---

# 7. Perfis

## RN-033 — Perfil

Um perfil representa um conjunto de permissões.

## RN-034 — Perfil ativo

Somente perfis ativos podem ser utilizados para concessão de acesso.

## RN-035 — Perfil inativo

A inativação de um perfil impede sua utilização para novos acessos.

## RN-036 — Perfis predefinidos

O sistema poderá possuir perfis iniciais, como:

* ADMINISTRADOR;
* GESTOR;
* FUNCIONARIO;
* FINANCEIRO;
* RH;
* MARKETING.

Esses perfis representam uma configuração inicial e poderão ser refinados conforme o produto evoluir.

---

# 8. Permissões

## RN-037 — Permissão

Uma permissão representa uma ação autorizada sobre determinado módulo.

## RN-038 — Unicidade lógica

A combinação de módulo e ação deve identificar uma única permissão.

## RN-039 — Concessão de permissão

Uma permissão só pode ser concedida por meio de perfil ou mecanismo explicitamente previsto pela arquitetura.

## RN-040 — Permissão inexistente

Uma ação sem permissão correspondente deve ser negada quando estiver protegida.

---

# 9. Controle de Acesso

## RN-041 — Autenticação

Recursos protegidos exigem usuário autenticado.

## RN-042 — Autorização funcional

Possuir autenticação não concede automaticamente autorização.

## RN-043 — Autorização contextual

Possuir uma permissão funcional não significa possuir acesso irrestrito a todos os objetos daquele módulo.

## RN-044 — Acesso ao próprio recurso

Quando aplicável, o usuário pode acessar seus próprios recursos mesmo quando o acesso a recursos de terceiros estiver restrito.

## RN-045 — Acesso gerencial

Usuários gestores podem acessar recursos dentro do escopo organizacional permitido pelas suas permissões.

## RN-046 — Administrador

Usuários administradores possuem as permissões administrativas definidas pelo sistema, observadas as regras de proteção do administrador principal.

## RN-047 — Privilégio mínimo

Usuários devem possuir somente as permissões necessárias para suas funções.

## RN-048 — Escalonamento de privilégio

Um usuário não pode conceder a outro usuário privilégios superiores aos que ele próprio pode administrar.

## RN-049 — Último administrador

O sistema não deve permitir uma operação que resulte na ausência de qualquer administrador válido.

---

# 10. Departamentos

## RN-050 — Departamento

Todo departamento pertence a uma empresa.

## RN-051 — Nome de departamento

Não devem existir departamentos duplicados dentro do mesmo escopo organizacional quando a regra de unicidade estiver configurada.

## RN-052 — Departamento inativo

Funcionários não devem ser vinculados a departamentos inativos para novas atribuições.

---

# 11. Cargos

## RN-053 — Cargo

Todo cargo pertence a uma empresa.

## RN-054 — Cargo e perfil

Cargo representa função profissional e não define, por si só, permissões de sistema.

## RN-055 — Separação de conceitos

Não deve existir relação implícita entre:

**Cargo → Perfil**

A associação de acesso deve ocorrer por usuário e perfil.

---

# 12. Endereços

## RN-056 — Endereço

Um endereço pertence a uma entidade proprietária específica.

## RN-057 — Integridade

Não deve existir endereço apontando para entidade inexistente.

## RN-058 — Entidades atendidas

O modelo inicial permite endereços para:

* funcionários;
* clientes;
* fornecedores.

---

# 13. Projetos

## RN-059 — Projeto

Todo projeto pertence a uma empresa.

## RN-060 — Responsável

O responsável de um projeto deve ser funcionário válido da empresa.

## RN-061 — Status do projeto

Os estados permitidos são:

* PLANEJAMENTO;
* EM_ANDAMENTO;
* PAUSADO;
* CONCLUIDO;
* CANCELADO.

## RN-062 — Início

Um projeto em execução deve possuir data de início válida.

## RN-063 — Previsão

A previsão de término, quando informada, não pode ser anterior à data de início.

## RN-064 — Conclusão

Um projeto concluído deve possuir data de término ou informação equivalente de encerramento.

## RN-065 — Cancelamento

Um projeto cancelado não deve permanecer disponível para novas operações incompatíveis com seu estado.

## RN-066 — Projeto concluído

Alterações após conclusão devem ser restritas às operações explicitamente permitidas.

## RN-067 — Progresso

O progresso do projeto deve ser derivado das tarefas relacionadas e não armazenado como um valor arbitrário informado pelo cliente.

---

# 14. Tarefas

## RN-068 — Tarefa

Toda tarefa deve possuir título e status.

## RN-069 — Projeto opcional

Uma tarefa pode ser vinculada a um projeto ou existir de forma independente.

## RN-070 — Responsável

Quando houver responsável, ele deve ser funcionário válido da empresa.

## RN-071 — Prazo

A data de prazo deve respeitar as demais datas da tarefa.

## RN-072 — Status

Os estados permitidos são:

* BACKLOG;
* A_FAZER;
* EM_ANDAMENTO;
* EM_REVISAO;
* CONCLUIDA;
* CANCELADA.

## RN-073 — Conclusão

Uma tarefa concluída deve registrar data de conclusão.

## RN-074 — Cancelamento

Uma tarefa cancelada não deve continuar em execução.

## RN-075 — Histórico de status

Mudanças de status relevantes devem gerar histórico.

## RN-076 — Histórico imutável

O histórico de alterações de status deve ser preservado.

---

# 15. Reuniões

## RN-077 — Reunião

Toda reunião deve pertencer à empresa.

## RN-078 — Intervalo de tempo

A data/hora de término deve ser posterior à data/hora de início.

## RN-079 — Organizador

O organizador deve ser funcionário válido e possuir autorização para criar ou administrar a reunião.

## RN-080 — Participantes

Participantes devem pertencer à mesma empresa.

## RN-081 — Participante único

O mesmo funcionário não pode aparecer duas vezes como participante da mesma reunião.

## RN-082 — Status do convite

Cada participação pode estar:

* PENDENTE;
* ACEITA;
* RECUSADA.

## RN-083 — Reunião cancelada

Reunião cancelada não deve aceitar operações incompatíveis com seu estado.

---

# 16. Clientes

## RN-084 — Cliente

Todo cliente pertence à empresa.

## RN-085 — Tipo de pessoa

São permitidos:

* PESSOA_FISICA;
* PESSOA_JURIDICA.

## RN-086 — Documento

O documento deve ser armazenado e validado conforme o tipo de pessoa.

## RN-087 — Cliente inativo

Clientes inativos não devem participar de novas operações que exijam cliente ativo.

## RN-088 — Histórico comercial

O histórico comercial deve ser preservado mesmo quando o cliente for inativado.

---

# 17. Fornecedores

## RN-089 — Fornecedor

Todo fornecedor pertence à empresa.

## RN-090 — Tipo de pessoa

São permitidos:

* PESSOA_FISICA;
* PESSOA_JURIDICA.

## RN-091 — Documento

O documento deve ser validado conforme o tipo de pessoa.

## RN-092 — Fornecedor inativo

Fornecedores inativos não devem participar de novas compras que exijam fornecedor ativo.

## RN-093 — Histórico de compras

O histórico de compras deve ser preservado quando um fornecedor for inativado.

---

# 18. Produtos e Serviços

## RN-094 — Produto

Todo produto pertence à empresa.

## RN-095 — Tipos de produto

São permitidos:

* PRODUTO;
* SERVICO.

## RN-096 — Produto físico

Produtos físicos podem participar do controle de estoque.

## RN-097 — Serviço

Serviços não devem possuir estoque.

## RN-098 — SKU

Quando informado, o SKU deve ser único dentro da empresa.

## RN-099 — Código de barras

Quando informado, o código de barras deve ser único dentro da empresa.

## RN-100 — Preço de venda

O preço de venda não pode ser negativo.

## RN-101 — Custo

O custo atual não pode ser negativo.

## RN-102 — Produto inativo

Produtos inativos não devem ser utilizados em novas operações que exijam produto ativo.

## RN-103 — Histórico de preço

O preço utilizado em uma venda ou compra deve permanecer registrado no item da operação.

---

# 19. Categorias de Produto

## RN-104 — Categoria

Toda categoria pertence à empresa.

## RN-105 — Produto categorizado

Um produto pode estar associado a uma categoria válida.

## RN-106 — Categoria inativa

A categoria inativa não deve ser utilizada para novos vínculos quando a regra exigir categoria ativa.

---

# 20. Estoque

## RN-107 — Registro de estoque

Cada produto físico deve possuir no máximo um registro de estoque no modelo inicial.

## RN-108 — Estoque inicial

A criação do estoque deve respeitar as regras de quantidade inicial definidas pelo sistema.

## RN-109 — Quantidade

Quantidade de estoque nunca pode ser inferior a zero quando estoque negativo não for permitido.

## RN-110 — Entrada

Entrada aumenta a quantidade disponível.

## RN-111 — Saída

Saída reduz a quantidade disponível.

## RN-112 — Ajuste

Ajuste pode aumentar ou reduzir a quantidade, desde que respeitadas as regras de autorização.

## RN-113 — Produto de serviço

Serviços não participam de movimentações de estoque.

## RN-114 — Histórico de movimentação

Toda alteração operacional relevante da quantidade deve gerar movimentação.

## RN-115 — Quantidade anterior

A movimentação deve registrar a quantidade anterior.

## RN-116 — Quantidade posterior

A movimentação deve registrar a quantidade posterior.

## RN-117 — Consistência

Deve ser sempre possível representar:

```text
Quantidade posterior
=
Quantidade anterior
+
Entrada
-
Saída
± Ajuste
```

conforme o tipo de operação.

## RN-118 — Imutabilidade

Movimentações de estoque históricas não devem ser editadas diretamente.

## RN-119 — Correção

Uma correção histórica deve ocorrer por nova movimentação ou mecanismo de estorno apropriado.

---

# 21. Vendas

## RN-120 — Venda

Toda venda pertence à empresa.

## RN-121 — Cliente

A venda deve possuir cliente quando a operação exigir identificação do comprador.

## RN-122 — Responsável

A venda deve possuir responsável válido.

## RN-123 — Itens

Uma venda precisa possuir pelo menos um item para ser confirmada.

## RN-124 — Quantidade

A quantidade de um item de venda deve ser maior que zero.

## RN-125 — Preço

O preço unitário utilizado deve ser determinado pelo servidor.

## RN-126 — Desconto

Descontos devem obedecer às regras definidas para a operação e não podem gerar total inválido.

## RN-127 — Subtotal

O subtotal de um item deve ser calculado a partir de seus valores válidos.

## RN-128 — Total

O total da venda deve ser calculado no servidor.

## RN-129 — Alteração de rascunho

Somente vendas em estado compatível podem ser editadas livremente.

## RN-130 — Confirmação

Uma venda só pode ser confirmada se todas as validações necessárias forem atendidas.

## RN-131 — Estoque

Quando a venda contiver produto controlado em estoque, a confirmação deve validar disponibilidade.

## RN-132 — Venda sem estoque

Serviços não exigem disponibilidade de estoque.

## RN-133 — Venda concluída

Venda concluída representa um fato comercial histórico.

## RN-134 — Exclusão

Venda concluída não deve ser fisicamente excluída.

## RN-135 — Cancelamento

Cancelamento deve respeitar o estado atual da venda.

## RN-136 — Idempotência

Operações críticas de confirmação ou conclusão devem suportar idempotência.

---

# 22. Compras

## RN-137 — Compra

Toda compra pertence à empresa.

## RN-138 — Fornecedor

A compra deve possuir fornecedor válido.

## RN-139 — Responsável

A compra deve possuir responsável válido.

## RN-140 — Itens

Uma compra precisa possuir pelo menos um item para ser confirmada.

## RN-141 — Quantidade

A quantidade de um item de compra deve ser maior que zero.

## RN-142 — Preço

O preço utilizado deve ser determinado e validado pelo servidor.

## RN-143 — Total

O total da compra deve ser calculado no servidor.

## RN-144 — Alteração de rascunho

Somente compras em estado compatível podem ser alteradas livremente.

## RN-145 — Confirmação

A compra só pode ser confirmada se as validações necessárias forem atendidas.

## RN-146 — Estoque

Produtos controlados por estoque podem gerar entrada de estoque decorrente da operação.

## RN-147 — Compra concluída

Compra concluída representa um fato histórico.

## RN-148 — Exclusão

Compra concluída não deve ser fisicamente excluída.

## RN-149 — Cancelamento

Cancelamento deve respeitar o estado atual da compra.

## RN-150 — Idempotência

Operações críticas de confirmação ou conclusão devem suportar idempotência.

---

# 23. Contas Financeiras

## RN-151 — Conta financeira

Toda conta financeira pertence à empresa.

## RN-152 — Tipos

Os tipos iniciais são:

* CONTA_CORRENTE;
* CONTA_POUPANCA;
* CAIXA;
* OUTRA.

## RN-153 — Status

Os estados são:

* ATIVA;
* INATIVA.

## RN-154 — Conta inativa

Uma conta financeira inativa não pode ser utilizada em novas operações financeiras.

## RN-155 — Saldo

O saldo não pode ser alterado diretamente pelo cliente da API.

## RN-156 — Atualização de saldo

Alterações de saldo devem ocorrer como consequência de operações financeiras válidas.

## RN-157 — Histórico

Movimentações devem permitir reconstruir o histórico financeiro da conta.

---

# 24. Categorias Financeiras

## RN-158 — Categoria financeira

Toda categoria financeira pertence à empresa.

## RN-159 — Tipo

Cada categoria deve ser classificada como:

* RECEITA;
* DESPESA.

## RN-160 — Compatibilidade

Categorias de receita não podem ser utilizadas em obrigações classificadas exclusivamente como despesa.

Categorias de despesa não podem ser utilizadas em obrigações classificadas exclusivamente como receita.

## RN-161 — Inativação

Categoria inativa não deve ser utilizada em novas operações quando a regra exigir categoria ativa.

---

# 25. Contas a Receber

## RN-162 — Conta a receber

Representa valor que a empresa possui direito de receber.

## RN-163 — Cliente

Quando aplicável, a conta a receber deve estar associada a um cliente válido.

## RN-164 — Venda

Uma conta a receber pode estar relacionada a uma venda.

## RN-165 — Valor

O valor total deve ser maior que zero.

## RN-166 — Status

Os estados são:

* ABERTA;
* PARCIALMENTE_PAGA;
* PAGA;
* VENCIDA;
* CANCELADA.

## RN-167 — Conta aberta

Uma conta aberta ainda possui valor pendente.

## RN-168 — Conta parcialmente paga

Uma conta parcialmente paga possui parte do valor liquidada e saldo restante.

## RN-169 — Conta paga

Uma conta é considerada paga quando não existe saldo restante.

## RN-170 — Conta vencida

Uma conta pode ser considerada vencida quando possui saldo pendente após sua data de vencimento, conforme regra temporal do sistema.

## RN-171 — Conta cancelada

Uma conta cancelada não pode receber novos pagamentos.

---

# 26. Contas a Pagar

## RN-172 — Conta a pagar

Representa valor que a empresa possui obrigação de pagar.

## RN-173 — Fornecedor

Quando aplicável, a conta a pagar deve estar associada a fornecedor válido.

## RN-174 — Compra

Uma conta a pagar pode estar relacionada a uma compra.

## RN-175 — Valor

O valor total deve ser maior que zero.

## RN-176 — Status

Os estados são:

* ABERTA;
* PARCIALMENTE_PAGA;
* PAGA;
* VENCIDA;
* CANCELADA.

## RN-177 — Conta cancelada

Uma conta cancelada não pode receber novos pagamentos.

---

# 27. Parcelas

## RN-178 — Obrigação

Toda parcela deve pertencer a uma única obrigação.

## RN-179 — Exclusividade

Uma parcela não pode pertencer simultaneamente a uma conta a receber e a uma conta a pagar.

## RN-180 — Número

O número da parcela deve ser positivo.

## RN-181 — Unicidade da parcela

O número da parcela deve ser único dentro da obrigação.

## RN-182 — Valor

O valor da parcela deve ser maior que zero.

## RN-183 — Vencimento

Toda parcela deve possuir data de vencimento quando o modelo exigir parcelamento.

## RN-184 — Soma das parcelas

A soma dos valores das parcelas deve corresponder ao valor da obrigação, respeitando o arredondamento monetário.

## RN-185 — Status

Os estados são:

* PENDENTE;
* PAGA;
* ATRASADA;
* CANCELADA.

## RN-186 — Parcela atrasada

Uma parcela com saldo pendente após seu vencimento pode assumir estado ATRASADA.

## RN-187 — Parcela paga

Uma parcela pode ser considerada paga somente quando seu saldo pendente chegar a zero.

---

# 28. Pagamentos

## RN-188 — Pagamento

Todo pagamento deve estar relacionado a uma parcela.

## RN-189 — Conta financeira

Todo pagamento deve indicar a conta financeira utilizada para a liquidação.

## RN-190 — Valor

O valor do pagamento deve ser maior que zero.

## RN-191 — Limite

O valor pago não pode exceder o saldo ainda devido da parcela.

## RN-192 — Pagamento parcial

Pagamento parcial é permitido.

## RN-193 — Pagamento total

Quando o pagamento quitar o saldo da parcela, a parcela deve ser marcada como paga.

## RN-194 — Atualização da obrigação

A obrigação deve refletir os pagamentos realizados em suas parcelas.

## RN-195 — Conta cancelada

Não é permitido registrar pagamento para parcela pertencente a obrigação cancelada.

## RN-196 — Histórico

Pagamentos realizados representam fatos financeiros e não devem ser alterados arbitrariamente.

## RN-197 — Correção

Correções de pagamentos devem ocorrer por estorno ou mecanismo compensatório apropriado.

## RN-198 — Idempotência

O registro de pagamento deve possuir proteção contra processamento duplicado.

## RN-199 — Concorrência

Dois pagamentos simultâneos não podem fazer com que o total pago ultrapasse o saldo devido.

---

# 29. Movimentações Financeiras

## RN-200 — Origem

Uma movimentação financeira deve possuir origem identificável quando for gerada por pagamento.

## RN-201 — Tipo

Os tipos são:

* ENTRADA;
* SAIDA.

## RN-202 — Valor

O valor deve ser maior que zero.

## RN-203 — Saldo anterior

A movimentação deve registrar o saldo anterior da conta.

## RN-204 — Saldo posterior

A movimentação deve registrar o saldo posterior da conta.

## RN-205 — Consistência

Para uma entrada:

```text
Saldo posterior = Saldo anterior + Valor
```

Para uma saída:

```text
Saldo posterior = Saldo anterior - Valor
```

## RN-206 — Imutabilidade

Movimentações financeiras históricas não devem ser editadas diretamente.

## RN-207 — Correção

Correções devem ocorrer por novas operações compensatórias ou estornos autorizados.

---

# 30. Integração Comercial e Financeira

## RN-208 — Venda e estoque

A confirmação/conclusão de uma venda de produto controlado em estoque deve refletir a saída correspondente de estoque.

## RN-209 — Compra e estoque

A confirmação/conclusão de uma compra de produto controlado em estoque deve refletir a entrada correspondente.

## RN-210 — Venda e contas a receber

Quando a operação exigir faturamento a prazo, a venda pode gerar conta a receber.

## RN-211 — Compra e contas a pagar

Quando a operação exigir pagamento futuro, a compra pode gerar conta a pagar.

## RN-212 — Separação entre fato comercial e financeiro

A existência de uma venda ou compra não significa necessariamente que o dinheiro já tenha sido recebido ou pago.

## RN-213 — Atomicidade

Quando uma única operação alterar múltiplos componentes do domínio, o sistema deve garantir que a operação não termine em estado parcialmente aplicado.

## RN-214 — Falha

Caso uma etapa crítica falhe, alterações dependentes devem ser revertidas ou tratadas por mecanismo de compensação definido pela arquitetura.

---

# 31. Estornos

## RN-215 — Estorno não é exclusão

Um estorno deve gerar uma operação compensatória em vez de apagar silenciosamente o histórico.

## RN-216 — Referência

O estorno deve permitir identificar a operação original.

## RN-217 — Autorização

Estornos devem exigir autorização apropriada.

## RN-218 — Auditoria

Estornos devem ser auditados.

---

# 32. Auditoria

## RN-219 — Operações auditáveis

Operações críticas devem gerar registros de auditoria.

## RN-220 — Responsável

Quando houver usuário autenticado, o responsável deve ser identificado.

## RN-221 — Data e hora

A data e hora da operação devem ser registradas.

## RN-222 — Entidade

Quando aplicável, a entidade afetada deve ser identificada.

## RN-223 — Resultado

A auditoria deve permitir distinguir operação bem-sucedida de operação malsucedida quando tecnicamente possível.

## RN-224 — Request ID

Operações de API devem poder ser relacionadas por requestId/correlationId quando o mecanismo estiver disponível.

## RN-225 — Dados sensíveis

Senha, token, segredo e outras credenciais não devem ser armazenados na auditoria.

## RN-226 — Imutabilidade

Registros de auditoria não devem ser editados por operações normais do sistema.

---

# 33. Consultas

## RN-227 — Paginação

Consultas potencialmente grandes devem utilizar paginação.

## RN-228 — Limite

O sistema deve impor tamanho máximo de página.

## RN-229 — Filtros

Filtros devem respeitar as permissões e o escopo de acesso do usuário.

## RN-230 — Dados retornados

Consultas devem retornar somente os dados necessários para a operação.

## RN-231 — Ordenação

Ordenações permitidas devem ser controladas pelo servidor.

---

# 34. Relatórios

## RN-232 — Origem dos dados

Indicadores devem ser calculados a partir dos dados registrados pelo sistema.

## RN-233 — Valores financeiros

Totais financeiros devem utilizar valores monetários com precisão adequada.

## RN-234 — Período

Relatórios por período devem definir claramente o intervalo considerado.

## RN-235 — Consistência

Relatórios não devem alterar dados operacionais.

## RN-236 — Permissão

Relatórios financeiros e administrativos devem respeitar as permissões do usuário.

---

# 35. Controle de Concorrência

## RN-237 — Estoque

Operações concorrentes de estoque devem validar o saldo atual no momento da alteração.

## RN-238 — Saldo financeiro

Operações concorrentes sobre uma conta financeira devem impedir perda de atualização.

## RN-239 — Pagamentos simultâneos

Pagamentos concorrentes devem impedir dupla utilização do mesmo saldo.

## RN-240 — Idempotência

Retry de uma mesma operação crítica não deve produzir uma segunda operação efetiva.

---

# 36. Integridade de Operações Críticas

## RN-241 — Estado válido

Nenhuma operação deve permitir transição incompatível com o estado atual da entidade.

## RN-242 — Ordem de execução

Operações dependentes devem respeitar a ordem definida pelo domínio.

Exemplo:

```text
Obrigação
    ↓
Parcela
    ↓
Pagamento
    ↓
Movimentação
```

## RN-243 — Estado intermediário

Estados intermediários não podem ser expostos como se fossem operações concluídas.

## RN-244 — Consistência após erro

Uma operação que falhar não deve deixar valores, saldos ou quantidades parcialmente atualizados.

---

# 37. Segurança de Dados

## RN-245 — Dados sensíveis

Informações sensíveis devem ser acessíveis somente a usuários autorizados.

## RN-246 — Logs

Logs não devem armazenar credenciais ou tokens.

## RN-247 — Exposição

Endpoints não devem retornar campos que o usuário não precisa conhecer.

## RN-248 — Auditoria

Operações críticas devem permanecer rastreáveis sem expor dados confidenciais.

---

# 38. Regras de Fase 2

Os seguintes conceitos não fazem parte do núcleo obrigatório do MVP:

* campanhas;
* cliente/campanha;
* analytics avançado;
* CRM;
* leads;
* notificações;
* documentos;
* RH avançado;
* integrações externas;
* automação;
* IA.

Quando forem implementados, suas regras deverão ser documentadas em versão específica.

---

# 39. Relacionamento com Requisitos

As regras de negócio devem ser utilizadas em conjunto com `REQUISITOS.md`.

Exemplo:

```text
RF-113
Registrar entrada de estoque
        ↓
RN-110
Entrada aumenta quantidade
        ↓
RN-114
Entrada gera histórico
        ↓
RN-237
Operação concorrente deve ser protegida
```

Outro exemplo:

```text
RF-172
Registrar pagamento
        ↓
RN-188
Pagamento pertence a uma parcela
        ↓
RN-191
Não pode exceder saldo
        ↓
RN-194
Atualiza obrigação
        ↓
RN-205
Atualiza movimentação/saldo
```

---

# 40. Princípio Final

As regras de negócio representam o comportamento que deve ser preservado independentemente da tecnologia utilizada.

Portanto:

```text
API
Interface
Banco
Framework
Cliente
```

não devem substituir ou enfraquecer as regras do domínio.

A implementação deve fazer com que as regras permaneçam verdadeiras em qualquer fluxo válido de execução.

---

# 41. Estado do Documento

**Documento:** Regras de Negócio
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Estado:** Especificação consolidada inicial

Este documento deve ser atualizado sempre que uma decisão de domínio alterar o comportamento esperado do sistema.
