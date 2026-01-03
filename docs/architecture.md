# Architecture Overview

ServiceDesk-Lite is designed as a backend-first, modular system that emphasizes
clear separation of concerns, maintainability, and realistic engineering practices.

This document provides a high-level architectural view and will evolve as the
project grows.

---

## System Overview

At a high level, the system consists of:

- A RESTful backend API built with Spring Boot
- A web frontend built with Angular
- A PostgreSQL database for persistence
- Docker-based local infrastructure for development

---

## High-Level Flow

1. Clients interact with the system via the frontend or API clients
2. Requests are sent to the backend REST API
3. Authentication and authorization are enforced at the API layer
4. Business logic is executed in the domain layer
5. Data is persisted in PostgreSQL
6. Responses are returned to the client

---

## Backend Architecture

The backend follows a layered architecture:

### API Layer
- REST controllers
- Request and response DTOs
- Input validation
- Error handling

### Domain Layer
- Core business logic
- Domain models and rules
- No framework-specific dependencies where possible

### Persistence Layer
- JPA entities
- Repository interfaces
- Database migrations managed with Flyway

### Infrastructure Layer
- Database configuration
- Security configuration
- Cross-cutting concerns (logging, auditing)

---

## Security Model (High-Level)

- Authentication via JWT
- Passwords stored using strong hashing (e.g., BCrypt)
- Authorization enforced at the API and service layers
- Organization-based data isolation

---

## Data Management

- PostgreSQL is the primary data store
- Schema changes are managed through versioned Flyway migrations
- Hibernate is used for ORM, with automatic schema generation disabled in favor of migrations

---

## Testing Strategy (Planned)

- Unit tests for domain and service logic
- Integration tests using Testcontainers and PostgreSQL
- API-level tests for authentication and core workflows

---

## Architectural Principles

- Explicit domain logic over implicit framework behavior
- Clear ownership of responsibilities per layer
- Predictable and repeatable local development environment
- Evolutionary design driven by real requirements

---

## Evolution

This architecture is expected to evolve as new features are added.
Significant architectural decisions will be documented using ADRs
in `docs/decisions/`.
