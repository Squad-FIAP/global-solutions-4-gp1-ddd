# API de Monitoramento e Combate a Queimadas

## Integrantes
- Gabriel Mediotti Marques - RM 552632
- Jó Sales - RM 552679

## Descrição do Problema Abordado
Optamos por desenvolver uma solução API RESTful focada em oferecer um sistema de monitoramento no combate à focos de incêndio. Através do Domain-Drive Design, modelamos o domínio nas seguintes entidades principais:
- **Região**: Representação de uma área geográfica, monitorada pelo sistema. Exemplos: Amazônia, Pantanal, Caatinga, etc;
- **Ponto de Foco**: Representação de um foco de incêndio, descrito pelas suas coordenadas geográficas (latitude e longitude), intensidade, área estimada de extensão, etc.
- **Ação de Combate**: Representação de ações tomadas no combate de um ponto de foco.

## Visão Geral da Arquitetura da Solução

O projeto segue os princípios de Domain-Driven Design (DDD), organizando o código em camadas bem definidas que refletem o domínio do problema. A arquitetura foi estruturada para permitir a fácil manutenção e evolução do sistema, com separação clara de responsabilidades.

A aplicação é construída como uma API RESTful utilizando Spring Boot, com endpoints organizados logicamente para gerenciar regiões, pontos de foco e ações de combate. Cada entidade do domínio (Região, PontoFoco, AcaoCombate) possui seu próprio controlador, serviço e repositório.

### Camadas
- **Entidades de Domínio**: `/domain/` - Define as entidades principais e suas regras de negócio
- **Repositórios**: `/repository/` - Gerencia a persistência e recuperação de dados
- **Serviços**: `/service/` - Implementa a lógica de negócio e coordena operações entre entidades
- **Factories**: `/service/factory/` - Classes para criação de objetos
- **Controladores**: `/controller/` - Controladores REST que expõem endpoints para manipulação das entidades
- **Configurações**: `/config/` - Classes de configuração do sistema

O banco de dados H2 é utilizado em ambiente de desenvolvimento, permitindo rápida prototipação e testes. Para produção, a arquitetura permite fácil substituição por um banco de dados persistente.

### Tecnologias Utilizadas
- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database (para desenvolvimento)
- Maven
- Lombok

## Pré-requisitos
- JDK 17 ou superior
- Maven 3.8 ou superior

## Configuração e Execução

### Clonar o repositório
```bash
git clone [url-do-repositório]
cd gs4-ddd
```

### Compilar o projeto
```bash
mvn clean install
```

### Executar o projeto
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8082`

### Banco de Dados
Por padrão, a aplicação utiliza o banco de dados H2 em memória.
Console do H2 disponível em: `http://localhost:8082/h2-console`
- JDBC URL: `jdbc:h2:mem:queimadasdb`
- Usuário: `sa`
- Senha: `password`

### Documentação da API (Swagger)
A documentação interativa da API está disponível através do Swagger UI:
- URL: `http://localhost:8082/swagger-ui.html`

Também é possível acessar a especificação OpenAPI em formato JSON:
- URL: `http://localhost:8082/api-docs`

## API REST

### Regiões

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/regioes` | Lista todas as regiões |
| GET | `/api/regioes/{id}` | Busca uma região pelo ID |
| GET | `/api/regioes/nome/{nome}` | Busca regiões por nome |
| GET | `/api/regioes/tipo/{tipo}` | Busca regiões por tipo |
| GET | `/api/regioes/risco/{nivelMinimo}` | Lista regiões por nível de risco mínimo |
| GET | `/api/regioes/com-focos` | Lista regiões ordenadas por pontos de foco ativos |
| GET | `/api/regioes/sem-focos` | Lista regiões sem pontos de foco ativos |
| POST | `/api/regioes` | Registra uma nova região |
| PUT | `/api/regioes/{id}` | Atualiza uma região |
| POST | `/api/regioes/{id}/recalcular-risco` | Recalcula o nível de risco de uma região |
| DELETE | `/api/regioes/{id}` | Remove uma região |

### Pontos de Foco

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/pontos-foco` | Lista todos os pontos de foco |
| GET | `/api/pontos-foco/{id}` | Busca um ponto de foco pelo ID |
| GET | `/api/pontos-foco/status/{status}` | Lista pontos de foco por status |
| GET | `/api/pontos-foco/ativos` | Lista pontos de foco ativos |
| GET | `/api/pontos-foco/regiao/{regiaoId}` | Lista pontos de foco por região |
| GET | `/api/pontos-foco/proximidade` | Lista pontos de foco por proximidade geográfica |
| GET | `/api/pontos-foco/intensidade` | Lista pontos de foco por intensidade mínima |
| POST | `/api/pontos-foco` | Registra um novo ponto de foco |
| POST | `/api/pontos-foco/completo` | Registra um ponto de foco com detalhes |
| PATCH | `/api/pontos-foco/{id}/status` | Atualiza o status de um ponto de foco |
| PATCH | `/api/pontos-foco/{id}` | Atualiza os detalhes de um ponto de foco |
| DELETE | `/api/pontos-foco/{id}` | Remove um ponto de foco |

### Ações de Combate

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/acoes-combate` | Lista todas as ações de combate |
| GET | `/api/acoes-combate/{id}` | Busca uma ação pelo ID |
| GET | `/api/acoes-combate/ponto-foco/{pontoFocoId}` | Lista ações por ponto de foco |
| GET | `/api/acoes-combate/em-andamento` | Lista ações em andamento |
| GET | `/api/acoes-combate/tipo` | Lista ações por tipo |
| GET | `/api/acoes-combate/regiao/{regiaoId}` | Lista ações por região |
| GET | `/api/acoes-combate/concluidas` | Lista ações concluídas em um período |
| POST | `/api/acoes-combate/terrestre/{pontoFocoId}` | Inicia combate terrestre |
| POST | `/api/acoes-combate/aereo/{pontoFocoId}` | Inicia combate aéreo |
| POST | `/api/acoes-combate/monitoramento/{pontoFocoId}` | Inicia monitoramento |
| POST | `/api/acoes-combate/personalizada/{pontoFocoId}` | Inicia ação personalizada |
| POST | `/api/acoes-combate/{acaoId}/concluir` | Conclui uma ação de combate |
| PATCH | `/api/acoes-combate/{id}` | Atualiza os detalhes de uma ação |
| DELETE | `/api/acoes-combate/{id}` | Remove uma ação |

## Exemplos de Uso

### Registrar uma nova região
```bash
curl -X POST http://localhost:8080/api/regioes \
  -H "Content-Type: application/json" \
  -d '{"nome":"Serra da Mantiqueira", "tipo":"Montanha", "areaMetrosQuadrados":1200000000, "descricao":"Região montanhosa no sudeste do Brasil", "nivelRisco":2}'
```

### Registrar um novo ponto de foco
```bash
curl -X POST "http://localhost:8080/api/pontos-foco/completo?latitude=-22.7896&longitude=-45.5982&intensidade=65.3&areaEstimada=5000&descricao=Incêndio%20em%20área%20de%20mata%20nativa&regiaoId=1"
```

### Iniciar uma ação de combate
```bash
curl -X POST "http://localhost:8080/api/acoes-combate/terrestre/1?descricao=Brigada%20combatendo%20incêndio%20com%20abafadores%20e%20bombas%20d%27água&responsavel=Corpo%20de%20Bombeiros%20SP"
```

## Dados de Teste
A aplicação possui um inicializador de dados que cria exemplos de regiões e pontos de foco durante a inicialização (apenas em ambientes de desenvolvimento e teste).
