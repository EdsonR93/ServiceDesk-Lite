# Repository Standards

This document describes the working standards for ServiceDesk-Lite.

---

## Workflow Summary

- Work is tracked using GitHub Issues (Epics and Stories)
- Each Story is implemented on its own branch
- Changes land in `dev` via Pull Requests
- `main` receives stable milestones from `dev`

Branch flow:
feature/* -> dev -> main

---

## Issue Labels

Core labels:
- epic
- story
- bug
- chore

Optional labels:
- backend
- frontend
- infra
- docs
- security
- database
- testing
- ci

---

## Pull Request Guidelines

PRs should:
- Reference the Issue number
- Explain changes clearly
- Include test notes

Link Issues using:
Closes #<issue-number>

---

## Commit Guidance

Prefer one commit per Story when possible.

Suggested format:
story(<issue-number>): summary
bug(<issue-number>): summary
chore(<issue-number>): summary

---

## Branch Protection

We protect `dev` and `main` to prevent accidental direct commits.

Checks will become required after backend/frontend build steps exist.
