# BellaGestora

A BellaGestora é uma plataforma SaaS (Software as a Service) de gestão desenvolvida para profissionais da beleza e trabalhadores autônomos. 

O projeto adota uma arquitetura **Monorepo**, unificando a API RESTful e a interface de usuário no mesmo repositório, garantindo controle de versão sincronizado e facilitando futuros deploys com Docker. O sistema encontra-se em fase de evolução ativa.

## Tecnologias Utilizadas

**Back-end (`/backend`)**
* Java 21
* Spring Boot 3
* Spring Security & JWT (JSON Web Token)
* PostgreSQL (Persistência de dados)

**Front-end (`/frontend`)**
* Angular (Standalone Components)
* *Status Atual:* Módulo de Autenticação (Tela de Login) finalizado e integrado. Demais telas em desenvolvimento.

## Como Executar o Projeto Localmente

### 1. Configurando o Banco de Dados (Back-end)
A API utiliza o PostgreSQL. Antes de iniciar, certifique-se de ter um banco de dados criado e configure a variável de ambiente com a sua senha.
* Na sua IDE (ex: IntelliJ), adicione nas variáveis de ambiente: `DB_PASSWORD=sua_senha`
* Inicie a aplicação rodando a classe `BellaGestoraApplication`.
* A API estará rodando em: `http://localhost:8080`

### 2. Iniciando a Interface (Front-end)
Abra um terminal na raiz do projeto e acesse a pasta do front-end:
```bash
cd frontend
npm install
npm start