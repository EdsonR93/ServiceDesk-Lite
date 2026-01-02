# Architecture

## Overview
This project uses a backend-first full-stack architecture:

- Backend exposes REST APIs
- Frontend consumes APIs
- Infra provides local dependencies via Docker Compose

## Typical Components
- API service (Spring Boot)
- PostgreSQL database
- Optional: Redis, message broker, observability stack

## Key Principles
- Database schema managed by migrations (Flyway/Liquibase)
- DTOs isolate API contracts from persistence entities
- Tests include integration tests against real dependencies (Testcontainers)
