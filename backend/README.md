# BellaGestora - Authentication API

Este projeto é uma API construída utilizando Java, Spring Boot, banco de dados H2 em memória e Spring Security com JWT para controle de autenticação e autorização. 

A API foi desenvolvida como um projeto prático para demonstrar a configuração de segurança em uma aplicação Spring, aplicada ao domínio da **BellaGestora** (uma plataforma de gestão para profissionais da beleza e autônomos).

## Tabela de Conteúdos

* [Uso](#uso)
* [Endpoints da API](#endpoints-da-api)
* [Autenticação](#autenticação)
* [Banco de Dados](#banco-de-dados)
* [Contribuição](#contribuição)

## Uso

Inicie a aplicação utilizando o Maven ou dando o "Play" na sua IDE (na classe `BellaGestoraApplication`). Como o projeto utiliza o H2 Database em memória, não é necessário configurar nenhum banco de dados externo.

A API estará acessível em: `http://localhost:8080`

Para acessar a interface do banco de dados, acesse: `http://localhost:8080/h2-console`

## Endpoints da API

A API fornece os seguintes endpoints:

**Públicos (Não exigem Token):**
* `POST /auth/login` - Realiza o login na aplicação e retorna o Token JWT.
* `POST /auth/register` - Registra um novo usuário na aplicação.

**Protegidos (Exigem Token JWT no cabeçalho):**
* `GET /procedimentos` - Retorna a lista de todos os procedimentos disponíveis (Acesso: ADMIN e USER).
* `POST /procedimentos` - Registra um novo procedimento no sistema (Acesso obrigatório: ADMIN).
* `DELETE /procedimentos` - Deleta um procedimento no sistema (Acesso obrigatório: ADMIN).
  
* `POST /agendamentos` - Registra um novo agendamento (Acesso: ADMIN e USER).
* `GET /agendamentos` - Retorna a lista de todos os agendamentos (Acesso: ADMIN e USER).
* `DELETE /agendamentos` - Deleta um agendamento no sistema (Acesso: ADMIN e USER).

## Autenticação

A API utiliza Spring Security e JWT para o controle de autenticação. As seguintes *roles* (papéis) estão disponíveis:

* **USER** -> Papel padrão para usuários logados. Permite visualizar procedimentos e marcar agendamentos.
* **ADMIN** -> Papel de administrador (dono do negócio). Permite o gerenciamento total, incluindo o cadastro de novos procedimentos na plataforma.

Para acessar os endpoints protegidos, forneça o token JWT gerado no login através do cabeçalho da requisição HTTP (`Authorization: Bearer <seu_token>`).

## Banco de Dados

O projeto utiliza o **H2 Database** operando em memória. 

Isso significa que o banco de dados é gerado automaticamente através do Hibernate toda vez que a aplicação é iniciada, facilitando os testes locais e a avaliação do código sem a necessidade de configurações complexas de infraestrutura.
