# ServiceDesk-Lite

ServiceDesk-Lite is a backend-focused, full-stack service desk / ticketing system designed to demonstrate
real-world backend engineering practices using Java and Spring Boot.

The project is intentionally scoped as a **solo-developer system** that starts small, works end-to-end,
and grows incrementally using professional workflows (epics, stories, CI, testing, Docker).

---

## 🎯 Project Goals

- Build a realistic backend system (not a toy CRUD app)
- Demonstrate clean architecture and domain-driven thinking
- Use professional tooling and workflows found in real teams
- Be easy to run locally using Docker
- Serve as a strong portfolio project for backend / full-stack roles

---

## 🧱 Core Features (Planned)

- Ticket management (CRUD + lifecycle)
- Authentication & authorization (JWT)
- Organization-based multi-tenancy (lite)
- Audit logging & traceability
- API documentation (OpenAPI / Swagger)
- CI pipeline with tests
- Dockerized local development

---

## 🛠 Tech Stack

### Backend
- Java 17+
- Spring Boot (Web, Security, Data JPA, Validation, Actuator)
- PostgreSQL
- Flyway migrations
- Testcontainers (integration tests)

### Frontend
- Angular
- TypeScript

### Infrastructure
- Docker & Docker Compose
- GitHub Actions (CI)

---

## 📁 Repository Structure

- backend/  — Spring Boot application
- frontend/ — Angular application
- infra/    — Docker Compose & environment config
- docs/     — Architecture and API documentation

---

## 🚀 Local Development (high-level)

Start infrastructure:
 - docker compose -f infra/docker-compose.yml up -d

Backend (later):
 - cd backend
 - mvn spring-boot:run

Frontend (later):
 - cd frontend
 - npm install
 - npm start

---

## 📌 Project Status

This project is actively developed using GitHub Issues:
- **Epics** = major feature areas
- **Stories** = small, implementable units of work

See the Issues tab for the roadmap and progress.
