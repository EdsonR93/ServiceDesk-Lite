# Backend

This folder will contain the Spring Boot application.

Suggested stack:
- Java 17+ / 21
- Spring Boot (Web, Security, Validation, Data JPA, Actuator)
- PostgreSQL
- Flyway migrations
- Testcontainers integration tests

Local dev (later):
- Start dependencies: docker compose -f ../infra/docker-compose.yml up -d
- Run app: mvn spring-boot:run
