# WebFlix API

API REST do catálogo de vídeos **WebFlix**: lista vídeos, gêneros e os dados do player consumidos pelo frontend Angular.

Frontend: [WebFlix-site](https://github.com/joaopedroleonel/WebFlix-site).

## Stack

- Java 21
- Spring Boot 4 (Web MVC + Spring Data JPA / Hibernate)
- MySQL 8.4
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito + AssertJ + H2 (testes)
- Docker / Docker Compose

## Estrutura

```
.
├── archives/                    # imagens e vídeos servidos pelo nginx (fora do git)
├── db/init.sql                  # cria as tabelas no primeiro start do MySQL
├── nginx/default.conf           # config do servidor de arquivos
├── docker-compose.yml           # sobe MySQL + API + nginx
├── Dockerfile                   # build multi-stage da API
├── .env.example                 # variáveis do banco (copie para .env)
└── src/
    ├── main/java/com/webflix/api/
    │   ├── config/              # CORS e rotas case-insensitive
    │   ├── controller/          # endpoints REST
    │   ├── dto/                 # objetos de resposta (records)
    │   ├── entity/              # entidades JPA (Video, Genre)
    │   ├── repository/          # Spring Data JPA
    │   └── service/             # regras e mapeamento entidade → DTO
    └── test/                    # testes unitários, de repositório e de integração
```

## Como rodar

### Com Docker (API + banco + arquivos)

Pré-requisito: Docker Desktop rodando.

```bash
cp .env.example .env
docker compose up --build
```

- API: http://localhost:5000/api
- Swagger: http://localhost:5000/swagger
- MySQL: `localhost:3306` (banco `webflixdb`, usuário `webflix` / senha `webflix`)
- Arquivos (nginx): http://localhost:8080/archives/

### Localmente (API fora do Docker)

Pré-requisito: JDK 21.

```bash
docker compose up -d db
./mvnw spring-boot:run
```

### Variáveis de ambiente da API

| Variável      | Padrão                                                                    |
|---------------|---------------------------------------------------------------------------|
| `PORT`        | `5000`                                                                    |
| `DB_URL`      | `jdbc:mysql://localhost:3306/webflixdb?allowPublicKeyRetrieval=true&useSSL=false` |
| `DB_USERNAME` | `webflix`                                                                 |
| `DB_PASSWORD` | `webflix`                                                                 |

O `docker-compose.yml` lê `MYSQL_DATABASE`, `MYSQL_USER`, `MYSQL_PASSWORD` e `MYSQL_ROOT_PASSWORD` do arquivo `.env`.

## Banco de dados

O `db/init.sql` é montado em `/docker-entrypoint-initdb.d/` do container MySQL e cria as tabelas `Videos`, `Genres` e `GenreVideo` (relação N:N). Os nomes são os mesmos do schema da versão .NET, então um banco antigo também pode ser usado.

- O script **só roda quando o volume do banco está vazio**, ou seja, na primeira vez. Ele também usa `CREATE TABLE IF NOT EXISTS`.
- O banco é criado sem dados. Para recriar do zero: `docker compose down -v && docker compose up --build`.
- A API não altera o schema: ela roda com `ddl-auto=validate` e só verifica se as tabelas batem com as entidades.

Exemplo para inserir dados:

```sql
INSERT INTO Genres (name) VALUES ('Ação');
INSERT INTO Videos (title, description, imageUrl, videoUrl)
VALUES ('Meu Filme', 'Descrição', 'meu-filme.jpg', 'meu-filme.mp4');
INSERT INTO GenreVideo (GenresgenreID, VideosvideoID) VALUES (1, 1);
```

```bash
docker compose exec db mysql -u webflix -pwebflix webflixdb
```

## Arquivos de mídia (nginx)

As capas e os vídeos são servidos pelo serviço `files` do compose, um nginx na porta **8080** configurado em `nginx/default.conf`. Ele expõe a pasta `archives/` do projeto em `http://localhost:8080/archives/`.

`imageUrl` e `videoUrl` no banco guardam **só o nome do arquivo**. O frontend monta a URL completa juntando esse nome com `urlServer` (`http://localhost:8080/archives/`).

Para adicionar um vídeo:

1. Copie `meu-filme.jpg` e `meu-filme.mp4` para `archives/`.
2. Cadastre esses nomes no banco (veja o exemplo de `INSERT` acima).
3. Confira em http://localhost:8080/archives/meu-filme.mp4.

- A pasta é montada no container como somente leitura e lida na hora, então arquivos novos aparecem sem reiniciar nada.
- O conteúdo de `archives/` fica fora do git (só o `.gitkeep` é versionado), para não colocar `.mp4` no repositório.
- O nginx atende requisições por trecho do arquivo, então dá para avançar e voltar o vídeo no player.

## Endpoints

As rotas não diferenciam maiúsculas e minúsculas (`/api/video/...` funciona igual a `/api/Video/...`). Quando o resultado é vazio, todos os endpoints respondem **404**. O CORS aceita qualquer origem.

| Método | Rota                                  | Retorno                                   |
|--------|---------------------------------------|-------------------------------------------|
| GET    | `/api/Genre`                          | Lista de gêneros                          |
| GET    | `/api/Video/videoList`                | Lista de vídeos (com capa)                |
| GET    | `/api/Video/{id}`                     | **Array** com o vídeo do player (com URL) |
| GET    | `/api/Video/genre?genreId={genreId}`  | Lista de vídeos de um gênero              |

`GET /api/Genre`
```json
[{ "genreID": 1, "name": "Ação" }]
```

`GET /api/Video/videoList` e `GET /api/Video/genre?genreId=1`
```json
[{
  "videoID": 1,
  "title": "Meu Filme",
  "description": "Descrição",
  "imageUrl": "meu-filme.jpg",
  "genres": [{ "genreID": 1, "name": "Ação" }]
}]
```

`GET /api/Video/1`
```json
[{
  "videoID": 1,
  "title": "Meu Filme",
  "description": "Descrição",
  "videoUrl": "meu-filme.mp4",
  "genres": [{ "genreID": 1, "name": "Ação" }]
}]
```

## Testes

```bash
./mvnw test
```

Os testes não precisam de Docker, porque usam um H2 em memória no modo MySQL:

- `controller/*ControllerTest`: `@WebMvcTest`, que cobre contrato JSON, 404, rotas case-insensitive e CORS
- `service/*ServiceTest`: testes unitários com Mockito do mapeamento entidade → DTO
- `repository/VideoRepositoryTest`: `@DataJpaTest`, que cobre as consultas com `JOIN FETCH` e o filtro por gênero
- `VideoApiIntegrationTest`: a aplicação completa com banco em memória
- `SchemaValidationTest`: roda o `db/init.sql` e valida se ele bate com as entidades JPA