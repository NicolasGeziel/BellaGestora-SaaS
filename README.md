# BellaGestora

A BellaGestora é uma plataforma SaaS (Software as a Service) de gestão desenvolvida para profissionais da beleza e trabalhadores autônomos. 

O projeto adota uma arquitetura Monorepo, unificando a API RESTful e a interface de usuário no mesmo repositório. Esta abordagem garante o controle de versão sincronizado e permite a orquestração centralizada de todo o ambiente de execução através de containers.

## Tecnologias e Arquitetura

**Back-end (`/backend`)**
* Java 21
* Spring Boot 3
* Spring Security & JWT (JSON Web Token) para Autenticação Stateless
* PostgreSQL (Persistência de dados)
* Maven

**Front-end (`/frontend`)**
* Angular (Standalone Components)
* Design Responsivo e modular
* Nginx (Servidor Web para ambiente de produção)

**Infraestrutura e DevOps**
* Docker & Docker Compose (Multi-stage builds)

## Funcionalidades Principais

* **Autenticação e Segurança:** Fluxo completo de registro e login com validação de tokens JWT.
* **Controle de Acessos (RBAC):** Separação estrita de privilégios entre gestores (ADMIN) e clientes (USER).
* **Catálogo de Procedimentos:** CRUD completo gerido pelo administrador, com barra de pesquisa dinâmica.
* **Gestão de Agendamentos:** Clientes podem solicitar marcações, enquanto os administradores possuem o controle exclusivo para confirmar ou cancelar pedidos.

## Como Executar o Projeto

O projeto pode ser executado através de containers Docker (recomendado para simular o ambiente de produção) ou de forma manual para desenvolvimento.

### Opção 1: Execução via Docker (Recomendado)

1. Na raiz do projeto, crie um arquivo `.env` e defina a senha do banco de dados:
   ```env
   DB_PASSWORD=sua_senha_segura
   ```

2. Abra o terminal na raiz do projeto e execute o orquestrador:
   ```bash
   docker-compose up -d --build
   ```

3. A aplicação estará disponível nos seguintes endereços:
   * **Front-end:** `http://localhost:4200`
   * **Back-end (API):** `http://localhost:8080`

### Opção 2: Execução Local (Desenvolvimento)

**1. Configuração do Banco de Dados**
Certifique-se de ter uma instância do PostgreSQL ativa com uma base de dados chamada `bellagestora`. 

**2. Back-end**
* Na sua IDE, configure a variável de ambiente necessária para a conexão: `DB_PASSWORD=sua_senha`
* Execute a classe principal `BellaGestoraApplication`. A API iniciará na porta 8080.

**3. Front-end**
* Abra um terminal na pasta `/frontend` e instale as dependências:
  ```bash
  npm install
  ```
* Inicie o servidor de desenvolvimento do Angular:
  ```bash
  npm start
  ```
* A interface estará disponível em `http://localhost:4200`.
