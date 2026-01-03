# Infrastructure – ServiceDesk-Lite

This folder contains the local development infrastructure used by ServiceDesk-Lite.

The goal of this module is to make the project easy to run locally with minimal setup,
using Docker to manage external dependencies.

---

## Contents

- `docker-compose.yml`  
  Defines local development services.

- `env/`  
  Environment variable examples for local and test setups.

---

## Services (Current / Planned)

- PostgreSQL database

Additional services may be added as the project evolves.

---

## Local Development Usage

Start infrastructure:

 - docker compose -f infra/docker-compose.yml up -d

Stop infrastructure:

 - docker compose -f infra/docker-compose.yml down

---

## Notes

- This infrastructure is intended for **local development only**
- Production deployment is out of scope for ServiceDesk-Lite
- Environment variables should be configured using the examples in `env/`
