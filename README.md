# Solução E4E - APIs REST

Este repositório contém as duas APIs REST que são consumidas pela solução E4E.

## APIs Disponíveis

### 1. eduinovatte
Esta API é responsável por toda a lógica de negócio da aplicação. As principais funcionalidades incluem:

- **CRUD de Usuários**: Criação, leitura, atualização e exclusão de usuários.
- **Gestão de Nichos**: Gerenciamento de diferentes áreas de interesse e especialização.
- **Nível de Inglês**: Controle e atualização dos níveis de inglês dos alunos e professores.
- **Agendamentos**: Gerenciamento completo dos agendamentos de aulas.

### 2. eduinovatte-dashboard
Esta API é responsável pela lógica de exibição de dashboards. Ela oferece visualizações tanto para alunos quanto para professores, permitindo:

- **Visão Geral dos Alunos**: Acompanhamento do progresso, agendamentos futuros e estatísticas.
- **Visão Geral dos Professores**: Visualização de histórico, agendamentos futuros e métricas de desempenho.

## Tecnologias Utilizadas
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white) 
![Amazon Corretto JDK 17](https://img.shields.io/badge/Corretto%20JDK%2017-00A2E5?style=for-the-badge&logo=amazonaws&logoColor=white) 
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) 
![Validation](https://img.shields.io/badge/Validation-009688?style=for-the-badge&logo=google&logoColor=white) 
![ModelMapper](https://img.shields.io/badge/ModelMapper-42A5F5?style=for-the-badge&logo=google&logoColor=white) 
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)


## Versões das Bibliotecas

- **Spring Boot**: `3.2.5`
- **Spring Security**: `3.2.3`
- **ModelMapper**: `3.2.0`
- **Java JWT**: `4.4.0`
- **Springdoc OpenAPI (Swagger)**: `2.3.0`
- **MySQL Connector**: `8.0.28`

### Pré-requisitos

- **Java 17**: Amazon Corretto JDK 17.
- **Maven**: Utilizado para gerenciar as dependências do projeto.

### Variaveis de Ambiente

**Exemplos com banco de dados local -**

- **SPRING_DATASOURCE_URL**: jdbc:mysql://localhost:3306/english4ever
- **SPRING_DATASOURCE_PASSWORD**: senha_da_conexao
- **SPRING_DATASOURCE_USERNAME**: nome_da_conexao
