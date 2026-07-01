# 📖 Tutorial de Instalação e Execução

Olá, Professor Eduardo Silvestre! Espero que esteja bem.

Este documento contém as instruções passo a passo para configurar e executar o **Sistema de Gestão de Estoque** localmente em sua máquina.

---

## ⚙️ Pré-requisitos

Para que o projeto funcione corretamente, é necessário ter o seguinte ambiente configurado:
- **MySQL** instalado e rodando localmente na porta padrão (`3306`).
- **Java JDK 17** ou superior.
- Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.).

---

## 🚀 Passos de Execução

Siga os passos abaixo para preparar o banco de dados e rodar a aplicação:

### 1. Criar o Banco de Dados
Abra o seu gerenciador do MySQL (como MySQL Workbench, DBeaver ou via terminal) e crie o banco de dados principal do projeto executando o comando:
```sql
CREATE DATABASE inventory_db;
```

### 2. Importar o Script SQL
Executar o script `backup_loja.sql` que envio em anexo junto ao trabalho. 
- Este script é responsável por criar todas as tabelas necessárias e já adicionará **dados de teste** para facilitar a avaliação.

### 3. Configurar a Conexão com o Banco
No projeto Java, navegue até o pacote `utils` e abra a classe `DatabaseConnection.java`.
- Verifique as credenciais de acesso ao banco.
- Caso a senha do usuário `root` do seu MySQL local **não seja** `'admin'`, por favor, altere a string de conexão nessa classe para a sua senha root local.

### 4. Adicionar a Biblioteca de PDF
O sistema possui geração de relatórios em PDF. Para isso, utilizamos a biblioteca iText.
- Certifique-se de adicionar o arquivo **`itextpdf-5.2.0.jar`** no projeto.
- Caso a sua IDE não faça isso automaticamente ao abrir o projeto, você precisará adicioná-la manualmente ao *Build Path* / *Dependencies*.

---

## 🔑 Credenciais de Teste

Com o banco de dados populado pelo script `backup_loja.sql`, você pode acessar o sistema utilizando as credenciais prontas abaixo:

| Perfil | Login | Senha |
| :--- | :--- | :--- |
| **Administrador** | `admin` | `admin` |
| **Cliente** | `devluquinha` | `12345` |

---
Qualquer dúvida durante a execução da aplicação, estou à disposição. Bom trabalho!
