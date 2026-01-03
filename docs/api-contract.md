# API Contract

This document describes the public REST API exposed by the ServiceDesk-Lite backend.

The API is designed to be:
- Clear and predictable
- Easy to consume by frontend clients
- Explicit about authentication and authorization requirements

This contract will evolve as features are implemented.

---

## Base Information

- API style: REST
- Data format: JSON
- Authentication: JWT (Bearer tokens)
- Base path (planned): /api

---

## Authentication (Planned)

Most endpoints will require authentication using a JWT.

Authentication flow:
1. User authenticates with credentials
2. Backend issues a JWT
3. Client includes the token in requests using the Authorization header

Authorization header format:
Authorization: Bearer <token>

---

## Error Handling (Planned)

Errors will be returned using a consistent JSON structure.

Example error response:

{
"timestamp": "...",
"status": 400,
"error": "Bad Request",
"message": "Validation failed",
"path": "/api/example"
}

---

## Endpoint Documentation Strategy

Endpoints will be documented in two complementary ways:

1. Human-readable documentation  
   This file will describe endpoint intent, behavior, and constraints.

2. OpenAPI / Swagger  
   Interactive API documentation will be generated automatically and exposed
   via Swagger UI during local development.

---

## Planned Core Endpoint Groups

The following endpoint groups will be implemented incrementally:

- Authentication
    - User registration
    - Login

- Organizations
    - Create organization
    - Manage memberships
    - Organization-level access control

- Tickets
    - Create ticket
    - Update ticket
    - Change ticket status
    - List and filter tickets

- Audit
    - View audit history for entities

---

## Status

This document is a living contract.

Endpoints will be added and refined as corresponding backend stories
are implemented and stabilized.
