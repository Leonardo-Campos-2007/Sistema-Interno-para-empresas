# REQUISITOS DO SISTEMA

## Sistema Interno para Empresas

**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Arquitetura:** Monólito Modular

---

# 1. Objetivo

Este documento define os requisitos funcionais e não funcionais do Sistema Interno de Gestão Empresarial.

Os requisitos descrevem **o que o sistema deve fazer** e **quais características técnicas e operacionais devem ser observadas**.

As regras específicas do domínio são detalhadas em `REGRAS-DE-NEGOCIO.md`.

---

# 2. Requisitos Funcionais

## 2.1 Empresa

**RF-001** — O sistema deve permitir o cadastro da empresa.

**RF-002** — O sistema deve permitir consultar os dados da empresa.

**RF-003** — O sistema deve permitir atualizar os dados da empresa.

**RF-004** — O sistema deve permitir ativar ou inativar a empresa.

---

## 2.2 Autenticação

**RF-005** — O sistema deve permitir autenticação de usuários.

**RF-006** — O sistema deve permitir encerramento de sessão conforme o mecanismo de autenticação adotado.

**RF-007** — O sistema deve permitir alteração de senha.

**RF-008** — O sistema deve permitir bloqueio de usuários.

**RF-009** — O sistema deve permitir desbloqueio de usuários.

**RF-010** — O sistema deve permitir recuperação de acesso conforme política de segurança definida.

**RF-011** — O sistema deve registrar o último acesso do usuário.

---

## 2.3 Usuários

**RF-012** — O sistema deve permitir criar usuários vinculados a funcionários.

**RF-013** — O sistema deve permitir consultar usuários.

**RF-014** — O sistema deve permitir editar usuários.

**RF-015** — O sistema deve permitir inativar usuários.

**RF-016** — O sistema deve permitir consultar o status de um usuário.

---

## 2.4 Perfis

**RF-017** — O sistema deve permitir criar perfis.

**RF-018** — O sistema deve permitir consultar perfis.

**RF-019** — O sistema deve permitir editar perfis.

**RF-020** — O sistema deve permitir inativar perfis.

**RF-021** — O sistema deve permitir associar perfis a usuários.

**RF-022** — O sistema deve permitir remover perfis de usuários.

**RF-023** — O sistema deve permitir consultar os perfis associados a um usuário.

---

## 2.5 Permissões

**RF-024** — O sistema deve possuir permissões organizadas por módulo e ação.

**RF-025** — O sistema deve permitir consultar permissões disponíveis.

**RF-026** — O sistema deve permitir associar permissões a perfis.

**RF-027** — O sistema deve permitir consultar permissões associadas a um perfil.

**RF-028** — O sistema deve verificar permissões antes da execução de operações protegidas.

**RF-029** — O sistema deve verificar autorização contextual para recursos individuais.

---

## 2.6 Estrutura Organizacional

**RF-030** — O sistema deve permitir cadastrar departamentos.

**RF-031** — O sistema deve permitir consultar departamentos.

**RF-032** — O sistema deve permitir editar departamentos.

**RF-033** — O sistema deve permitir inativar departamentos.

**RF-034** — O sistema deve permitir cadastrar cargos.

**RF-035** — O sistema deve permitir consultar cargos.

**RF-036** — O sistema deve permitir editar cargos.

**RF-037** — O sistema deve permitir inativar cargos.

---

## 2.7 Funcionários

**RF-038** — O sistema deve permitir cadastrar funcionários.

**RF-039** — O sistema deve permitir consultar funcionários.

**RF-040** — O sistema deve permitir editar funcionários.

**RF-041** — O sistema deve permitir alterar o status de funcionários.

**RF-042** — O sistema deve permitir vincular funcionários a departamentos.

**RF-043** — O sistema deve permitir vincular funcionários a cargos.

**RF-044** — O sistema deve permitir consultar funcionários por departamento.

**RF-045** — O sistema deve permitir consultar funcionários por cargo.

**RF-046** — O sistema deve permitir consultar funcionários por nome, matrícula, CPF ou status, respeitando as permissões aplicáveis.

---

## 2.8 Endereços

**RF-047** — O sistema deve permitir cadastrar endereços de funcionários.

**RF-048** — O sistema deve permitir editar endereços de funcionários.

**RF-049** — O sistema deve permitir consultar endereços de funcionários.

**RF-050** — O sistema deve permitir cadastrar endereços de clientes.

**RF-051** — O sistema deve permitir editar endereços de clientes.

**RF-052** — O sistema deve permitir cadastrar endereços de fornecedores.

**RF-053** — O sistema deve permitir editar endereços de fornecedores.

---

## 2.9 Projetos

**RF-054** — O sistema deve permitir criar projetos.

**RF-055** — O sistema deve permitir consultar projetos.

**RF-056** — O sistema deve permitir editar projetos.

**RF-057** — O sistema deve permitir definir prioridade de projetos.

**RF-058** — O sistema deve permitir definir responsável por projetos.

**RF-059** — O sistema deve permitir alterar o status de projetos.

**RF-060** — O sistema deve permitir definir datas de projetos.

**RF-061** — O sistema deve permitir concluir projetos.

**RF-062** — O sistema deve permitir cancelar projetos conforme as regras de negócio.

**RF-063** — O sistema deve calcular o progresso do projeto a partir das tarefas relacionadas.

---

## 2.10 Tarefas

**RF-064** — O sistema deve permitir criar tarefas.

**RF-065** — O sistema deve permitir criar tarefas vinculadas a projetos.

**RF-066** — O sistema deve permitir criar tarefas independentes de projetos.

**RF-067** — O sistema deve permitir atribuir responsáveis às tarefas.

**RF-068** — O sistema deve permitir editar tarefas.

**RF-069** — O sistema deve permitir definir prazo de tarefas.

**RF-070** — O sistema deve permitir definir prioridade de tarefas.

**RF-071** — O sistema deve permitir alterar o status de tarefas.

**RF-072** — O sistema deve permitir concluir tarefas.

**RF-073** — O sistema deve permitir cancelar tarefas.

**RF-074** — O sistema deve manter histórico de alterações de status das tarefas.

**RF-075** — O sistema deve permitir consultar tarefas por responsável, status e prazo.

---

## 2.11 Reuniões

**RF-076** — O sistema deve permitir criar reuniões.

**RF-077** — O sistema deve permitir editar reuniões.

**RF-078** — O sistema deve permitir cancelar reuniões.

**RF-079** — O sistema deve permitir definir data e horário de reuniões.

**RF-080** — O sistema deve permitir definir local ou link de reunião.

**RF-081** — O sistema deve permitir adicionar participantes.

**RF-082** — O sistema deve permitir aceitar convites.

**RF-083** — O sistema deve permitir recusar convites.

**RF-084** — O sistema deve permitir consultar reuniões de um funcionário.

**RF-085** — O sistema deve permitir vincular reuniões a projetos.

---

## 2.12 Clientes

**RF-086** — O sistema deve permitir cadastrar clientes.

**RF-087** — O sistema deve permitir consultar clientes.

**RF-088** — O sistema deve permitir editar clientes.

**RF-089** — O sistema deve permitir inativar clientes.

**RF-090** — O sistema deve permitir pesquisar clientes por nome ou documento.

**RF-091** — O sistema deve permitir consultar o histórico comercial de um cliente.

---

## 2.13 Fornecedores

**RF-092** — O sistema deve permitir cadastrar fornecedores.

**RF-093** — O sistema deve permitir consultar fornecedores.

**RF-094** — O sistema deve permitir editar fornecedores.

**RF-095** — O sistema deve permitir inativar fornecedores.

**RF-096** — O sistema deve permitir pesquisar fornecedores por nome ou documento.

**RF-097** — O sistema deve permitir consultar o histórico de compras de um fornecedor.

---

## 2.14 Categorias e Produtos

**RF-098** — O sistema deve permitir cadastrar categorias de produtos.

**RF-099** — O sistema deve permitir consultar categorias.

**RF-100** — O sistema deve permitir editar categorias.

**RF-101** — O sistema deve permitir inativar categorias.

**RF-102** — O sistema deve permitir cadastrar produtos e serviços.

**RF-103** — O sistema deve permitir consultar produtos.

**RF-104** — O sistema deve permitir editar produtos.

**RF-105** — O sistema deve permitir inativar produtos.

**RF-106** — O sistema deve permitir associar produtos a categorias.

**RF-107** — O sistema deve permitir pesquisar produtos por nome, SKU ou código de barras.

**RF-108** — O sistema deve distinguir produtos físicos de serviços.

---

## 2.15 Estoque

**RF-109** — O sistema deve permitir criar o registro de estoque de um produto.

**RF-110** — O sistema deve permitir consultar o estoque atual.

**RF-111** — O sistema deve permitir definir estoque mínimo.

**RF-112** — O sistema deve permitir definir estoque máximo.

**RF-113** — O sistema deve permitir registrar entrada de estoque.

**RF-114** — O sistema deve permitir registrar saída de estoque.

**RF-115** — O sistema deve permitir registrar ajustes de estoque.

**RF-116** — O sistema deve manter o histórico de movimentações de estoque.

**RF-117** — O sistema deve informar a quantidade anterior e posterior de uma movimentação.

**RF-118** — O sistema deve permitir identificar o responsável pela movimentação.

**RF-119** — O sistema deve permitir consultar produtos abaixo do estoque mínimo.

**RF-120** — O sistema deve impedir saldo negativo quando a regra da operação não permitir estoque negativo.

---

## 2.16 Vendas

**RF-121** — O sistema deve permitir criar vendas.

**RF-122** — O sistema deve permitir adicionar itens às vendas.

**RF-123** — O sistema deve permitir informar a quantidade de cada item.

**RF-124** — O sistema deve permitir informar descontos conforme as regras aplicáveis.

**RF-125** — O sistema deve calcular subtotal e total da venda.

**RF-126** — O sistema deve permitir editar vendas em rascunho.

**RF-127** — O sistema deve permitir confirmar vendas.

**RF-128** — O sistema deve permitir cancelar vendas conforme seu estado.

**RF-129** — O sistema deve registrar o preço utilizado no momento da venda.

**RF-130** — O sistema deve permitir consultar vendas por período.

**RF-131** — O sistema deve permitir consultar vendas por cliente.

---

## 2.17 Compras

**RF-132** — O sistema deve permitir criar compras.

**RF-133** — O sistema deve permitir adicionar itens às compras.

**RF-134** — O sistema deve permitir informar quantidades.

**RF-135** — O sistema deve permitir informar descontos conforme as regras aplicáveis.

**RF-136** — O sistema deve calcular subtotal e total da compra.

**RF-137** — O sistema deve permitir editar compras em rascunho.

**RF-138** — O sistema deve permitir confirmar compras.

**RF-139** — O sistema deve permitir cancelar compras conforme seu estado.

**RF-140** — O sistema deve registrar o preço utilizado no momento da compra.

**RF-141** — O sistema deve permitir consultar compras por período.

---

## 2.18 Contas Financeiras

**RF-142** — O sistema deve permitir cadastrar contas financeiras.

**RF-143** — O sistema deve permitir consultar contas financeiras.

**RF-144** — O sistema deve permitir editar contas financeiras.

**RF-145** — O sistema deve permitir inativar contas financeiras.

**RF-146** — O sistema deve permitir consultar o saldo atual de uma conta.

**RF-147** — O sistema deve permitir consultar o histórico financeiro de uma conta.

**RF-148** — O sistema deve impedir operações financeiras em contas inativas.

---

## 2.19 Categorias Financeiras

**RF-149** — O sistema deve permitir cadastrar categorias financeiras.

**RF-150** — O sistema deve permitir consultar categorias financeiras.

**RF-151** — O sistema deve permitir editar categorias financeiras.

**RF-152** — O sistema deve permitir inativar categorias financeiras.

**RF-153** — O sistema deve identificar categorias como receita ou despesa.

---

## 2.20 Contas a Receber

**RF-154** — O sistema deve permitir registrar contas a receber.

**RF-155** — O sistema deve permitir vincular contas a receber a vendas quando aplicável.

**RF-156** — O sistema deve permitir vincular contas a receber a clientes.

**RF-157** — O sistema deve permitir consultar contas a receber em aberto.

**RF-158** — O sistema deve permitir consultar contas a receber vencidas.

**RF-159** — O sistema deve permitir cancelar contas a receber conforme as regras de negócio.

---

## 2.21 Contas a Pagar

**RF-160** — O sistema deve permitir registrar contas a pagar.

**RF-161** — O sistema deve permitir vincular contas a pagar a compras quando aplicável.

**RF-162** — O sistema deve permitir vincular contas a pagar a fornecedores.

**RF-163** — O sistema deve permitir consultar contas a pagar em aberto.

**RF-164** — O sistema deve permitir consultar contas a pagar vencidas.

**RF-165** — O sistema deve permitir cancelar contas a pagar conforme as regras de negócio.

---

## 2.22 Parcelas

**RF-166** — O sistema deve permitir dividir uma obrigação financeira em parcelas.

**RF-167** — O sistema deve permitir definir o vencimento das parcelas.

**RF-168** — O sistema deve permitir consultar parcelas pendentes.

**RF-169** — O sistema deve identificar parcelas atrasadas.

**RF-170** — O sistema deve permitir controle de pagamentos parciais.

**RF-171** — O sistema deve validar a soma das parcelas em relação ao valor da obrigação.

---

## 2.23 Pagamentos e Recebimentos

**RF-172** — O sistema deve permitir registrar pagamentos e recebimentos.

**RF-173** — O sistema deve permitir informar a forma de pagamento.

**RF-174** — O sistema deve permitir pagamentos parciais.

**RF-175** — O sistema deve permitir quitação completa de parcelas.

**RF-176** — O sistema deve impedir pagamentos superiores ao saldo devido.

**RF-177** — O sistema deve permitir consultar pagamentos realizados.

**RF-178** — O sistema deve permitir estornar pagamentos conforme as regras de negócio.

**RF-179** — O sistema deve identificar o responsável pelo pagamento.

---

## 2.24 Movimentações Financeiras

**RF-180** — O sistema deve registrar movimentações financeiras geradas por pagamentos e recebimentos.

**RF-181** — O sistema deve permitir consultar movimentações financeiras.

**RF-182** — O sistema deve identificar entradas e saídas.

**RF-183** — O sistema deve registrar saldo anterior e posterior da movimentação.

**RF-184** — O sistema deve permitir consultar movimentações por período.

**RF-185** — O sistema deve permitir consultar entradas e saídas por categoria.

**RF-186** — O sistema deve permitir consultar o saldo consolidado das contas financeiras.

**RF-187** — O sistema deve permitir identificar a origem da movimentação.

---

## 2.25 Integração Comercial, Estoque e Financeiro

**RF-188** — O sistema deve integrar operações comerciais com estoque quando aplicável.

**RF-189** — O sistema deve integrar operações comerciais com obrigações financeiras quando aplicável.

**RF-190** — O sistema deve garantir consistência transacional em operações integradas.

**RF-191** — O sistema deve impedir duplicidade de operações críticas.

**RF-192** — O sistema deve validar o estado das entidades antes da execução de operações críticas.

---

## 2.26 Auditoria

**RF-193** — O sistema deve registrar operações relevantes.

**RF-194** — O sistema deve registrar o usuário responsável pela operação quando identificável.

**RF-195** — O sistema deve registrar data e hora da operação.

**RF-196** — O sistema deve identificar a entidade afetada pela operação.

**RF-197** — O sistema deve permitir consultar registros de auditoria conforme permissões.

**RF-198** — O sistema deve permitir filtrar registros de auditoria.

**RF-199** — O sistema deve registrar resultado da operação.

**RF-200** — O sistema deve suportar rastreamento por requestId/correlationId.

---

## 2.27 Consultas e Paginação

**RF-201** — O sistema deve oferecer consultas paginadas quando o volume de dados justificar.

**RF-202** — O sistema deve permitir filtros nas consultas.

**RF-203** — O sistema deve permitir ordenação quando aplicável.

**RF-204** — O sistema deve impor limites de paginação.

**RF-205** — O sistema deve evitar retorno desnecessário de grandes volumes de dados.

---

## 2.28 Relatórios e Indicadores

**RF-206** — O sistema deve apresentar total de vendas por período.

**RF-207** — O sistema deve apresentar total de compras por período.

**RF-208** — O sistema deve apresentar produtos mais vendidos.

**RF-209** — O sistema deve apresentar clientes que mais geram receita.

**RF-210** — O sistema deve apresentar indicadores financeiros básicos.

**RF-211** — O sistema deve apresentar indicadores operacionais básicos.

---

# 3. Requisitos Não Funcionais

## 3.1 Arquitetura e Manutenibilidade

**RNF-001** — O sistema deve utilizar arquitetura de monólito modular.

**RNF-002** — Os módulos devem possuir responsabilidades bem definidas.

**RNF-003** — O sistema deve seguir separação clara entre Controller, Service/Use Case e Repository.

**RNF-004** — Controllers não devem concentrar regras de negócio.

**RNF-005** — Regras de negócio devem ser mantidas na camada apropriada.

**RNF-006** — Entities não devem ser utilizadas diretamente como resposta da API.

**RNF-007** — DTOs devem ser utilizados para entrada e saída da API quando aplicável.

**RNF-008** — O código deve priorizar baixo acoplamento e alta coesão.

**RNF-009** — O projeto deve possuir estrutura preparada para evolução incremental.

---

## 3.2 Desempenho

**RNF-010** — Consultas devem evitar carregamento desnecessário de dados.

**RNF-011** — Consultas com grande volume devem utilizar paginação.

**RNF-012** — Consultas frequentes devem possuir índices apropriados.

**RNF-013** — Operações críticas devem evitar processamento desnecessário.

**RNF-014** — O sistema deve estabelecer limites para requisições que possam consumir recursos excessivamente.

---

## 3.3 Escalabilidade

**RNF-015** — O sistema deve permitir crescimento do volume de dados sem alteração estrutural completa.

**RNF-016** — Módulos devem poder evoluir sem acoplamento excessivo.

**RNF-017** — A arquitetura deve permitir futura extração de componentes caso exista justificativa técnica.

**RNF-018** — A evolução para microservices não deve ser obrigatória no MVP.

---

## 3.4 Disponibilidade e Confiabilidade

**RNF-019** — Falhas em operações devem resultar em estados consistentes.

**RNF-020** — Operações transacionais devem possuir rollback quando necessário.

**RNF-021** — A aplicação deve disponibilizar mecanismos de health check.

**RNF-022** — Falhas devem ser registradas de maneira rastreável sem expor informações sensíveis.

---

## 3.5 Integridade de Dados

**RNF-023** — O banco deve utilizar constraints de integridade quando apropriado.

**RNF-024** — Chaves estrangeiras devem ser utilizadas nas relações persistidas.

**RNF-025** — Campos com unicidade necessária devem possuir restrições apropriadas.

**RNF-026** — Valores monetários devem utilizar precisão adequada.

**RNF-027** — Operações financeiras e de estoque devem preservar consistência.

**RNF-028** — Registros históricos críticos não devem ser apagados fisicamente sem justificativa de domínio.

---

## 3.6 Concorrência

**RNF-029** — Operações concorrentes sobre estoque devem possuir controle adequado.

**RNF-030** — Operações concorrentes sobre saldos financeiros devem possuir controle adequado.

**RNF-031** — O sistema deve utilizar optimistic locking ou mecanismo equivalente quando apropriado.

**RNF-032** — Operações críticas devem validar o estado atual antes de alterar dados.

---

## 3.7 Idempotência

**RNF-033** — Operações críticas devem possuir mecanismo de idempotência.

**RNF-034** — A repetição de uma mesma requisição crítica não deve gerar duplicação indevida.

**RNF-035** — O controle de idempotência deve possuir proteção adequada no banco.

---

## 3.8 Segurança

**RNF-036** — Recursos protegidos devem exigir autenticação.

**RNF-037** — O sistema deve aplicar autorização por permissões.

**RNF-038** — O sistema deve aplicar autorização contextual quando necessária.

**RNF-039** — O sistema deve validar dados recebidos do cliente.

**RNF-040** — O cliente não deve ser considerado uma fonte confiável de regras de negócio.

**RNF-041** — O sistema deve evitar exposição de dados desnecessários.

**RNF-042** — Mensagens de erro não devem expor informações internas.

**RNF-043** — Senhas devem ser armazenadas utilizando hash seguro.

**RNF-044** — Tokens e credenciais não devem ser registrados em logs.

**RNF-045** — O sistema deve possuir proteção contra tentativa excessiva de autenticação.

**RNF-046** — O sistema deve possuir controles contra acesso indevido a objetos.

**RNF-047** — O sistema deve possuir proteção contra manipulação indevida de propriedades.

**RNF-048** — O sistema deve possuir proteção contra SQL Injection.

**RNF-049** — O sistema deve possuir proteção contra XSS quando aplicável.

**RNF-050** — CORS deve ser configurado de forma restritiva.

---

## 3.9 Autenticação e Autorização

**RNF-051** — O mecanismo de autenticação deve ser baseado em Spring Security.

**RNF-052** — Tokens devem possuir configuração adequada de expiração.

**RNF-053** — O sistema deve impedir privilégio superior ao autorizado.

**RNF-054** — O sistema deve impedir operações administrativas não autorizadas.

**RNF-055** — O sistema deve impedir que usuários removam o último administrador válido.

---

## 3.10 Auditoria e Observabilidade

**RNF-056** — Operações críticas devem possuir rastreabilidade.

**RNF-057** — Auditoria deve registrar informações suficientes para reconstruir o contexto da operação.

**RNF-058** — Dados sensíveis não devem ser registrados em logs.

**RNF-059** — O sistema deve possuir requestId ou mecanismo equivalente de correlação.

**RNF-060** — O sistema deve disponibilizar health checks.

**RNF-061** — O sistema deve possuir métricas e informações básicas de observabilidade.

---

## 3.11 Banco de Dados

**RNF-062** — O sistema deve utilizar MySQL 8.

**RNF-063** — Alterações estruturais do banco devem ser controladas por migrations.

**RNF-064** — As migrations devem ser versionadas.

**RNF-065** — Migrations aplicadas não devem ser alteradas arbitrariamente.

**RNF-066** — O banco deve utilizar índices para consultas relevantes.

**RNF-067** — O banco deve utilizar constraints para reforçar regras importantes.

---

## 3.12 API

**RNF-068** — A API deve utilizar HTTP de acordo com os padrões REST adotados pelo projeto.

**RNF-069** — Os endpoints devem possuir respostas consistentes.

**RNF-070** — Os erros da API devem utilizar formato padronizado.

**RNF-071** — Endpoints protegidos devem exigir autenticação e autorização apropriadas.

**RNF-072** — A API deve utilizar DTOs.

**RNF-073** — A API deve possuir documentação OpenAPI.

**RNF-074** — A API deve limitar recursos de consulta conforme necessário.

---

## 3.13 Validação

**RNF-075** — Dados recebidos devem ser validados no servidor.

**RNF-076** — Valores monetários negativos indevidos devem ser rejeitados.

**RNF-077** — Quantidades inválidas devem ser rejeitadas.

**RNF-078** — Datas inconsistentes devem ser rejeitadas.

**RNF-079** — IDs inexistentes devem gerar respostas de erro adequadas.

---

## 3.14 Testes

**RNF-080** — Funcionalidades críticas devem possuir testes automatizados.

**RNF-081** — O sistema deve possuir testes unitários.

**RNF-082** — O sistema deve possuir testes de integração.

**RNF-083** — Testes de integração com banco devem utilizar ambiente compatível com MySQL.

**RNF-084** — Testcontainers deve ser utilizado para testes que necessitem de infraestrutura de banco.

**RNF-085** — Operações financeiras críticas devem possuir testes de concorrência e consistência quando aplicável.

**RNF-086** — Funcionalidades de segurança devem possuir testes específicos.

---

## 3.15 Configuração e Ambientes

**RNF-087** — O sistema deve possuir configuração separada por ambiente.

**RNF-088** — Credenciais não devem ser armazenadas diretamente no código-fonte.

**RNF-089** — Configurações sensíveis devem ser fornecidas externamente.

**RNF-090** — O projeto deve suportar ambiente de desenvolvimento, teste e produção.

---

## 3.16 Deploy

**RNF-091** — O sistema deve possuir suporte a execução em container.

**RNF-092** — O projeto deve possuir Dockerfile.

**RNF-093** — O ambiente local deve poder utilizar Docker Compose quando necessário.

**RNF-094** — O processo de build deve ser automatizável.

---

## 3.17 CI/CD

**RNF-095** — O projeto deve executar testes automaticamente no pipeline.

**RNF-096** — O pipeline deve validar a construção da aplicação.

**RNF-097** — O pipeline deve executar verificações de qualidade.

**RNF-098** — O pipeline deve permitir evolução para etapas de deploy automatizado.

---

## 3.18 Backup e Recuperação

**RNF-099** — O ambiente de produção deve possuir estratégia de backup.

**RNF-100** — Backups devem possuir política de retenção.

**RNF-101** — Deve existir procedimento para recuperação do banco.

**RNF-102** — O procedimento de recuperação deve ser testável.

---

## 3.19 Privacidade

**RNF-103** — O sistema deve limitar a exposição de dados pessoais ao necessário.

**RNF-104** — Dados pessoais não devem ser expostos a usuários sem autorização.

**RNF-105** — Logs não devem armazenar dados pessoais desnecessários.

**RNF-106** — O sistema deve aplicar controles compatíveis com requisitos de proteção de dados aplicáveis.

---

## 3.20 Qualidade de Código

**RNF-107** — O código deve seguir padrões consistentes de nomenclatura.

**RNF-108** — A lógica de negócio deve evitar duplicação desnecessária.

**RNF-109** — Classes devem possuir responsabilidades bem definidas.

**RNF-110** — O projeto deve utilizar análise de qualidade de código no pipeline.

**RNF-111** — Dependências devem ser mantidas em versões suportadas.

**RNF-112** — Bibliotecas não utilizadas devem ser evitadas.

---

# 4. Matriz de Rastreabilidade

Os requisitos funcionais serão relacionados às histórias de usuário do Backlog Mestre.

Exemplo:

| Requisito | História |
| --------- | -------- |
| RF-001    | US-001   |
| RF-002    | US-002   |
| RF-012    | US-010   |
| RF-017    | US-016   |
| RF-038    | US-036   |
| RF-054    | US-046   |
| RF-064    | US-057   |
| RF-076    | US-070   |
| RF-086    | US-078   |
| RF-092    | US-086   |
| RF-102    | US-094   |
| RF-113    | US-106   |
| RF-121    | US-111   |
| RF-132    | US-121   |
| RF-142    | US-130   |
| RF-154    | US-141   |
| RF-160    | US-147   |
| RF-166    | US-153   |
| RF-172    | US-159   |
| RF-180    | US-167   |
| RF-193    | US-180   |
| RF-201    | US-188   |
| RF-206    | US-196   |

A matriz completa deverá ser refinada junto ao backlog durante a implementação.

---

# 5. Critérios Gerais de Aceitação

Uma funcionalidade estará apta a ser aceita quando:

* atender ao requisito funcional correspondente;
* respeitar as regras de negócio;
* respeitar as permissões;
* validar entradas;
* tratar erros adequadamente;
* possuir testes compatíveis;
* não expor dados indevidos;
* manter a integridade dos dados;
* possuir documentação técnica necessária.

---

# 6. Relação com os demais documentos

Este documento deve ser utilizado em conjunto com:

* `README.md`
* `REGRAS-DE-NEGOCIO.md`
* `ARQUITETURA.md`
* `SEGURANCA.md`
* `MODELO-DE-DADOS.md`
* `BACKLOG.md`

### Responsabilidade

**REQUISITOS.md**

Define **o que o sistema deve fazer**.

**REGRAS-DE-NEGOCIO.md**

Define **como o domínio deve se comportar**.

**ARQUITETURA.md**

Define **como o sistema será estruturado tecnicamente**.

**SEGURANCA.md**

Define **como os riscos e controles de segurança serão tratados**.

**MODELO-DE-DADOS.md**

Define **como os dados serão representados e relacionados**.

**BACKLOG.md**

Define **o que será desenvolvido, em qual ordem e em qual Sprint**.

---

# 7. Estado do Documento

**Documento:** Requisitos do Sistema
**Versão:** 1.0
**Escopo:** MVP / Fase 1
**Estado:** Especificação consolidada inicial
