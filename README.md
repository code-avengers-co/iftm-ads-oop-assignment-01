# 📦 Inventory Management System

Sistema de gerenciamento de estoque desenvolvido em **Java puro**, utilizando arquitetura **MVC** (Model-View-Controller) com persistência em memória (arrays). Projeto acadêmico focado em Programação Orientada a Objetos.

## 📋 Sobre o Projeto

Uma aplicação de console que simula uma loja virtual completa, permitindo o gerenciamento de produtos, usuários, carrinho de compras, pedidos, cupons de desconto, entregas e controle de estoque — tudo via terminal interativo.

O sistema possui dois perfis de acesso:
- **Administrador** — Gerencia produtos, estoque, cupons, entregas e visualiza relatórios
- **Cliente** — Navega produtos, adiciona ao carrinho e realiza compras

## ✨ Funcionalidades

### 👤 Gestão de Usuários
- Criação de conta com dados pessoais (nome, data de nascimento, documento)
- Autenticação por login/senha
- Sessão de usuário com controle de acesso (admin vs. cliente)
- Conta de administrador criada automaticamente na inicialização

### 📦 Gestão de Produtos
- Cadastro, edição e exclusão (soft delete) de produtos
- Listagem de produtos ativos
- Controle de preço e status (ativo/inativo)

### 🛒 Carrinho de Compras
- Adição de produtos ao carrinho com quantidade
- Detecção automática de itens duplicados (soma quantidade)
- Visualização do carrinho com subtotais
- Remoção de itens individuais
- Expiração automática após 24 horas (via sistema de triggers)

### 💳 Checkout e Pedidos
- Finalização de compra com método de pagamento
- Verificação de estoque antes da compra
- Aplicação de cupons de desconto no checkout
- Criação automática de pedido, itens do pedido, entregas e movimentações de estoque
- Histórico de pedidos do cliente

### 🎟️ Cupons de Desconto
- Cadastro de cupons com código, tipo de desconto e validade
- Dois tipos de desconto: **Fixo (R$)** e **Percentual (%)**
- Validação de valor mínimo do pedido
- Verificação de expiração automática
- Listagem e exclusão de cupons

### 📊 Controle de Estoque
- Entrada manual de estoque (IN)
- Saída/baixa manual de estoque (OUT)
- Ajuste absoluto de estoque (ADJUST)
- Registro de movimentações com valor unitário
- Atualização automática do estoque no checkout

### 🚚 Gestão de Entregas
- Criação automática de entrega ao finalizar pedido
- Visualização de entregas pendentes (Preparing)
- Atualização de transportadora e código de rastreamento
- Progressão automática de status via triggers

### 📈 Relatórios (Admin)
- Pedidos filtrados por status (Created, Paid, Preparation, Shipped, Delivered, Cancelled)
- Receita por período (diária, mensal, anual)

### ⏰ Sistema de Tempo Virtual
- Relógio virtual controlado pelo administrador
- Avanço de dias para simular passagem do tempo
- Triggers automáticos ao avançar o tempo:
  - Carrinhos abertos há +24h → Expirados
  - Pedidos com +24h → Shipped (Enviado)
  - Pedidos com +48h → Delivered (Entregue)

## 🏗️ Arquitetura

O projeto segue o padrão **MVC** com 5 camadas:

```
src/
├── Main.java                    # Ponto de entrada da aplicação
├── model/                       # Entidades de domínio
│   ├── Product.java
│   ├── Person.java
│   ├── User.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── StockMovement.java
│   ├── Coupon.java
│   ├── Delivery.java
│   └── enums/
│       ├── CartStatus.java      # Open, Closed, Cancelled, Expired
│       ├── OrderStatus.java     # Created, Paid, Preparation, Shipped, Delivered, Canceled
│       ├── DeliveryStatus.java  # Preparing, Shipped, InTransit, Delivered, Cancelled
│       ├── MovementType.java    # IN, OUT, ADJUST
│       ├── DiscountType.java    # Fixed, Percentual
│       └── RevenuePeriod.java   # DAILY, MONTHLY, YEARLY
├── dao/                         # Persistência em memória (arrays)
│   ├── ProductDao.java
│   ├── PersonDao.java
│   ├── UserDao.java
│   ├── CartDao.java
│   ├── CartItemDao.java
│   ├── OrderDao.java
│   ├── OrderItemDao.java
│   ├── StockMovementDao.java
│   ├── CouponDao.java
│   └── DeliveryDao.java
├── controller/                  # Lógica de negócio
│   ├── ProductController.java
│   ├── UserController.java
│   ├── CartController.java
│   ├── CheckoutController.java
│   ├── StockController.java
│   ├── CouponController.java
│   ├── OrderController.java
│   ├── ReportController.java
│   ├── DeliveryController.java
│   └── TriggerController.java
├── view/                        # Interface de console (menus interativos)
│   ├── MainView.java
│   ├── ProductView.java
│   ├── UserView.java
│   ├── CartView.java
│   ├── CouponView.java
│   ├── OrderView.java
│   ├── StockView.java
│   ├── ReportView.java
│   └── DeliveryView.java
└── utils/                       # Classes utilitárias
    ├── CreationIdUtils.java     # Geração sequencial de IDs
    ├── InputUtils.java          # Leitura segura de inputs do console
    ├── SystemClock.java         # Relógio virtual para simulação de tempo
    └── UserSession.java         # Gerenciamento de sessão do usuário logado
```

### Decisões Técnicas

- **Persistência em memória** — Todos os dados são armazenados em arrays com tamanho fixo (100 por padrão). Não utiliza banco de dados.
- **Geração de IDs** — Cada entidade gera seu próprio ID sequencial automaticamente no construtor, via `CreationIdUtils`.
- **Soft Delete** — Produtos são desativados (`active = false`) em vez de removidos do array.
- **Auditoria** — Todas as entidades possuem `createdAt` e `updatedAt`, atualizados automaticamente pelos DAOs.

## 🚀 Como Executar

### Pré-requisitos
- **Java 17+** (utiliza Text Blocks)

### Compilar e Rodar

```bash
# Compilar
javac -d out -sourcepath src src/Main.java

# Executar
java -cp out Main
```

### Credenciais de Acesso

| Perfil | Usuário | Senha |
|---|---|---|
| Administrador | `admin` | `admin` |

## 🎮 Como Usar

### Fluxo do Administrador
1. Faça login com `admin` / `admin`
2. **Cadastre produtos** — Menu 1 → Opção 1
3. **Adicione estoque** — Menu 3 (Manual Stock Entry) → Selecione produto, quantidade e tipo IN
4. **Crie cupons** — Menu 2 → Opção 1
5. **Visualize relatórios** — Menu 5
6. **Avance o tempo** — Menu 6 para simular dias e disparar triggers automáticos
7. **Gerencie entregas** — Menu 7

### Fluxo do Cliente
1. **Crie uma conta** — Opção 2 no menu inicial
2. **Faça login** com as credenciais criadas
3. **Compre** — Menu 1 → Adicione produtos ao carrinho
4. **Finalize a compra** — Visualize o carrinho → Checkout → Informe pagamento e cupom (opcional)
5. **Veja seus pedidos** — Menu 2

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.