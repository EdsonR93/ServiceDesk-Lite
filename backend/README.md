# Backend – ServiceDesk-Lite

This module contains the Spring Boot backend for ServiceDesk-Lite.

The backend is responsible for:
- Business logic and domain rules
- Data persistence
- Authentication and authorization
- REST API exposure
- Audit logging and traceability

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway
- Testcontainers

---

## Architectural Overview

The backend follows a layered architecture:

- **API layer**
    - REST controllers
    - Request / response DTOs
    - Validation

- **Domain layer**
    - Core business logic
    - Domain models and rules

- **Persistence layer**
    - JPA entities
    - Repositories
    - Database migrations (Flyway)

- **Infrastructure layer**
    - Database configuration
    - Security configuration
    - Cross-cutting concerns

Detailed design decisions are documented in `/docs/architecture.md`.

---

## Development Status

The backend will be implemented incrementally, starting with:
- Project bootstrap
- Database setup
- Core domain entities
- Authentication foundation

Each step is tracked as a GitHub Story under the relevant Epic.
