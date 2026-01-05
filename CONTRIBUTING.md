# Contributing

This repository follows a lightweight workflow designed for incremental delivery and clean history.

---

## Branching

Create a new branch for every Story, Bug, or Chore.

Recommended naming:
- feature/story-<issue-number>-short-title
- fix/bug-<issue-number>-short-title
- chore/<issue-number>-short-title

Examples:
- feature/story-12-ticket-crud
- fix/bug-33-null-pointer-login
- chore/5-update-readme

---

## Commits

Prefer one commit per Story when reasonable.

Commit message format (suggested):
- story(<issue-number>): short summary
- bug(<issue-number>): short summary
- chore(<issue-number>): short summary

Examples:
- story(12): add ticket create endpoint
- chore(3): update repo documentation

---

## Pull Requests

All changes merge through Pull Requests.

PR requirements:
- Link the Issue using "Closes #<issue-number>"
- Describe what changed and why
- Include basic test notes (even if manual)
- Keep PRs small and focused

---

## Definition of Done

A Story is considered done when:
- The acceptance criteria are met
- Code is readable and organized
- Docs are updated if behavior changed
- Basic tests were run (manual or automated)

---

## Issue Types

- Epic: groups multiple Stories
- Story: implementable unit with acceptance criteria
- Bug: fixes incorrect behavior
- Chore: non-feature work (cleanup, tooling, documentation)
