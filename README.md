# Full-Stack Project Template (Backend-Leaning)

A reusable template repo for backend-first full-stack projects.

## Goals
- Professional repo structure (backend, frontend, infra, docs)
- Standardized GitHub Issues (Epics + Stories)
- Docker-friendly local development
- Clear documentation habits (architecture + ADRs)

## Repo Structure
- backend/  — API / services (e.g., Java + Spring Boot)
- frontend/ — UI (e.g., Angular)
- infra/    — Docker, environment examples
- docs/     — architecture, ADRs, API notes

## Getting Started
Run local infrastructure (Postgres):
- docker compose -f infra/docker-compose.yml up -d

## Planning Standard
We use GitHub Issues:
- Epics = big feature areas
- Stories = implementable work items
- Each Epic tracks its Stories using checkboxes linking to issues:
    - [ ] #123 Story title
