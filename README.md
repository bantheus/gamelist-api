# GameList API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-025E8C.svg?style=for-the-badge&logo=databricks&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Uma API RESTful para gerenciar um catálogo de jogos eletrônicos, onde os jogos podem ser organizados em listas
personalizadas. Este projeto permite aos usuários criar e administrar coleções de jogos, com informações detalhadas
sobre cada título.

## Tecnologias Utilizadas

- **Java 24**
- **Spring Boot 3.4.5**
- **Spring Data JPA** – Para operações com o banco de dados
- **PostgreSQL** – Banco de dados de produção e desenvolvimento
- **H2 Database** – Banco de dados para testes
- **Maven** – Gerenciamento de dependências e build
- **Docker & Docker Compose** – Para conteinerização e fácil deploy

## Estrutura do Projeto

O projeto segue uma arquitetura padrão do Spring Boot com os seguintes componentes:

### Entidades

- **Game** – Representa um jogo com propriedades como título, ano, gênero, plataformas, nota, etc.
- **GameList** – Representa uma lista de jogos com um nome
- **Belonging** – Representa o relacionamento muitos-para-muitos entre `Game` e `GameList`, incluindo a posição do jogo
  na lista

### DTOs

- **GameDTO** – Objeto de transferência com dados completos do jogo
- **GameMinDTO** – Objeto de transferência com dados reduzidos do jogo
- **GameListDTO** – Objeto de transferência da lista de jogos

### Controllers

- **GameController** – Controlador para requisições relacionadas aos jogos
- **GameListController** – Controlador para requisições relacionadas às listas de jogos

### Services

- **GameService** – Lógica de negócio para jogos
- **GameListService** – Lógica de negócio para listas de jogos

### Repositories

- **GameRepository** – Acesso aos dados de jogos
- **GameListRepository** – Acesso aos dados de listas de jogos

## Endpoints da API

### Jogos

- `GET /api/v1/games` – Retorna todos os jogos (representação reduzida)
- `GET /api/v1/games/{gameId}` – Retorna detalhes de um jogo específico

### Listas de Jogos

- `GET /api/v1/lists` – Retorna todas as listas de jogos
- `GET /api/v1/lists/{listId}/games` – Retorna todos os jogos de uma lista específica
- `POST /api/v1/lists/{listId}/replacement` – Altera a posição de um jogo dentro da lista especificada, movendo-o de um
  índice para outro.

### Exemplo de body da requisição:

```json
{
  "sourceIndex": 4,
  "destinationIndex": 1
}
```

Esse body move o jogo que está na posição 4 para a posição 1 na lista, reordenando os demais automaticamente.

## Configuração e Instalação

### Pré-requisitos

- Java 24 ou superior
- Maven
- Docker e Docker Compose (opcional, para usar banco containerizado)

### Executando Localmente

1. Clone o repositório: ```bash
   git clone <repository-url>
   cd gamelist
   ```

2. Inicie o banco de dados PostgreSQL via Docker Compose (opcional):
   ```bash
   docker-compose up -d
   ```

3. Compile e execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

   Por padrão, a aplicação roda com o perfil `test`, usando um banco H2 em memória.

4. Para rodar com um perfil específico:
   ```bash
   ./mvnw spring-boot:run -Dspring.profiles.active=dev
   ```

### Perfis

- **test** - Usa banco H2 em memória, ideal para testes
- **dev** - Usa PostgreSQL local, ideal para desenvolvimento
- **prod** - Usa PostgreSQL com dados de conexão fornecidos por variáveis de ambiente

### Variáveis de Ambiente (perfil prod)

- `DB_URL` - URL do banco de dados
- `DB_USERNAME` - Usuário do banco
- `DB_PASSWORD` - Senha do banco
- `APP_PROFILE` - Perfil da aplicação (`test`, `dev`, `prod`)
- `CORS_ORIGINS` - Lista de origens permitidas para CORS, separadas por vírgula

## Configuração do Banco de Dados

### Perfil Teste

- Banco H2 em memória
- Console disponível em `/h2-console`
- Usuário: sa
- Senha: (em branco)

### Perfil Dev

- Banco PostgreSQL local
- Host: `localhost`
- Porta: `5433`
- Banco: `gamelist`
- Usuário: `postgres`
- Senha: `1234567`

### Perfil Prod

- Banco PostgreSQL
- Configuração via variáveis de ambiente

## Setup com Docker

O projeto inclui um arquivo Docker Compose que configura:

- Banco PostgreSQL 14 na porta 5433
- pgAdmin para gerenciar o banco na porta 5050

Para iniciar os containers:

```bash
docker-compose up -d
```

O pgAdmin estará disponível em http://localhost:5050 com:

- Email: me@example.com
- Senha: 1234567

## License

Este projeto está licenciado sob a Licença MIT.