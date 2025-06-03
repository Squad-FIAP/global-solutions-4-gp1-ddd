# API de Monitoramento e Combate a Queimadas

## Integrantes
- Gabriel Mediotti Marques - RM 552632
- Jó Sales - RM 552679

## Descrição do Problema Abordado
Optamos por desenvolver como solução uma API RESTful focada em oferecer um sistema de monitoramento no combate à focos de incêndio. Através do Domain-Drive Design, modelamos o domínio nas seguintes entidades principais:
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

### Tecnologias Utilizadas
- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database
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
