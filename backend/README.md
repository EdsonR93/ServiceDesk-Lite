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

---

## How to test Flyway

Start the backend with dev profile enabled:

 - .\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=dev"

Verify schema table exists:

 - docker exec -it servicedesk-postgres psql -U servicedesk -d servicedesk -c "\dt"
 - docker exec -it servicedesk-postgres psql -U servicedesk -d servicedesk -c "SELECT version, description, success FROM flyway_schema_history;"

It will show 1 row in the results. This confirms that Flyway ran successfully and applied the baseline migration (V1__baseline.sql).

You can verify the constraints in each table executing:

 - docker exec -it servicedesk-postgres psql -U servicedesk -d servicedesk -c "\d <table_name>"

Where <table_name> should match an existing table (organizations, users, memberships...).

To Verify the constraints only allow the correct values run the following:

 - docker exec -it servicedesk-postgres psql -U servicedesk -d servicedesk -c "INSERT INTO users (email,status) VALUES ('x@test.com','BAD');"
 - docker exec -it servicedesk-postgres psql -U servicedesk -d servicedesk -c "INSERT INTO memberships (org_id,user_id,role) VALUES (gen_random_uuid(),gen_random_uuid(),'BAD');"

They should fail with a CHECK constraint error.

Verify the Triggers works by executing the following:

 - docker exec -it servicedesk-postgres psql -U servicedesk -d servicedesk
 - INSERT INTO organizations (name, slug) VALUES ('Acme', 'acme') RETURNING id, created_at, updated_at;
 - UPDATE organizations SET name = 'Acme Inc.' WHERE slug = 'acme' RETURNING created_at, updated_at;

updated_at should be newer after the UPDATE.

---
