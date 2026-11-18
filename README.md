# Usuário API

API REST de gerenciamento de usuários com autenticação via JWT, construída com Spring Boot e PostgreSQL.

O projeto foi desenvolvido para praticar, na prática, arquitetura em camadas, persistência com JPA e autenticação stateless com Spring Security.

## Tecnologias

| Categoria     | Tecnologia                              |
|---------------|-----------------------------------------|
| Linguagem     | Java 21                                 |
| Framework     | Spring Boot 4.1.2 (Web MVC)             |
| Persistência  | Spring Data JPA, PostgreSQL             |
| Segurança     | Spring Security, JWT (jjwt 0.13.0), BCrypt |
| Utilitários   | Lombok                                  |
| Build         | Maven (Maven Wrapper incluso)           |

## Arquitetura

O código segue uma divisão em camadas, cada uma com uma responsabilidade clara:

```
src/main/java/com/gabriel/spring_boot_project
├── controller/          # Endpoints REST e DTOs
│   └── dtos/
├── business/            # Regras de negócio (services)
└── infrastructure/
    ├── entity/          # Entidades JPA (Usuario, Endereco, Telefone)
    ├── repository/      # Repositórios Spring Data
    ├── exceptions/      # Exceções de domínio
    └── security/        # JWT, filtro de autenticação e configuração do Spring Security
```

## Funcionalidades

- Cadastro de usuário com e-mail único, endereços e telefones (relacionamento `OneToMany` com cascade)
- Senhas armazenadas com hash BCrypt
- Login com geração de token JWT (validade de 1 hora)
- Autenticação stateless: um filtro valida o token a cada requisição
- Consulta e remoção de usuário por e-mail (rotas protegidas)
- Tratamento de e-mail duplicado (`ConflictException`) e de recurso inexistente (`ResourceNotFoundException`)

## Endpoints

| Método   | Rota                      | Autenticação | Descrição                          |
|----------|---------------------------|:------------:|------------------------------------|
| `POST`   | `/usuario`                | Não          | Cadastra um novo usuário           |
| `POST`   | `/usuario/login`          | Não          | Autentica e retorna o token JWT    |
| `GET`    | `/usuario?email={email}`  | Sim          | Busca um usuário pelo e-mail       |
| `DELETE` | `/usuario/{email}`        | Sim          | Remove um usuário pelo e-mail      |

### Exemplos

**Cadastro**

```bash
curl -X POST http://localhost:8080/usuario \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Gabriel",
    "email": "gabriel@email.com",
    "senha": "minhasenha",
    "enderecos": [],
    "telefones": []
  }'
```

**Login**

```bash
curl -X POST http://localhost:8080/usuario/login \
  -H "Content-Type: application/json" \
  -d '{"email": "gabriel@email.com", "senha": "minhasenha"}'
```

A resposta já vem no formato `Bearer <token>`, pronta para ser usada no header `Authorization`.

**Rota protegida**

```bash
curl "http://localhost:8080/usuario?email=gabriel@email.com" \
  -H "Authorization: Bearer <token>"
```

## Como executar

### Pré-requisitos

- Java 21
- PostgreSQL em execução

### Passos

1. Clone o repositório:

   ```bash
   git clone https://github.com/gcastrodev/microservice.git
   cd microservice
   ```

2. Crie o banco de dados:

   ```sql
   CREATE DATABASE db_usuario;
   ```

3. Ajuste as credenciais em `src/main/resources/application.properties`, se necessário:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/db_usuario
   spring.datasource.username=postgres
   spring.datasource.password=<sua-senha>
   ```

   As tabelas são criadas automaticamente (`spring.jpa.hibernate.ddl-auto=update`).

4. Execute a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

   No Windows: `mvnw.cmd spring-boot:run`

A API fica disponível em `http://localhost:8080`.

## Próximos passos

- Mover o segredo do JWT e as credenciais do banco para variáveis de ambiente
- Criar DTOs de resposta para não expor a entidade (e o hash da senha) nos endpoints
- Adicionar `@RestControllerAdvice` para padronizar as respostas de erro
- Validação de entrada com Bean Validation
- Testes unitários e de integração
- Containerização com Docker Compose (aplicação + PostgreSQL)
