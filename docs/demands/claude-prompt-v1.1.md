# Claude Code Prompt — DistributionBox v1.1 (Spring Boot + Vue)

You are Claude Code running inside a local repo.

## Context
We are rebuilding a Distribution Panel / 配电箱管理系统 **from scratch**.

- Repo root: `distributionBox/`
- There is a reference folder `old-box/` cloned from an older system. **Use it only for inspiration** (field naming, page layout ideas). Do **NOT** reuse its code directly.
- Requirements doc: `docs/demands/v1.1.md`

## High-level requirements (v1.1)
Implement a minimal usable web system including:
- Distribution box asset registry (CRUD)
- Circuits/breakers under a box (CRUD)
- Inspections (create + list + detail)
- Issues (fault/hazard) with status flow and handling records
- Basic authentication + role-based access (Admin/Manager/Technician/Viewer) — keep it simple

## Tech stack (must)
- Backend: Java 17, Spring Boot, Maven
  - Spring Web, Validation
  - Spring Data JPA
  - MySQL (dev via docker-compose)
  - Flyway migrations
  - Spring Security (JWT)
  - OpenAPI/Swagger UI
- Frontend: Vue 3 + Vite + TypeScript
  - Vue Router, Pinia
  - UI library: Element Plus
  - Axios client

## Project layout (create)
At repo root, create:
- `backend/` (Spring Boot project)
- `frontend/` (Vue project)
- `docker-compose.yml` (MySQL + optional adminer)
- `Makefile` or `scripts/` commands for dev convenience

## Backend details
1) Domain entities (start minimal, but extensible):
- DistributionBox
- Circuit
- Inspection
- Issue
- IssueHandlingRecord (or embedded history)
- User + Role (seed default admin)

2) API endpoints (REST):
- `/api/auth/login` (JWT)
- `/api/boxes` CRUD + `/api/boxes/{id}/circuits`
- `/api/inspections` create + list + detail
- `/api/issues` CRUD + status update + add handling record

3) Include:
- DTOs + validation
- Global exception handler
- Pagination for list endpoints
- Flyway initial schema and seed admin user
- Swagger configured and secured (allow login endpoint publicly)

## Frontend details
1) Pages (Element Plus):
- Login
- Boxes: list + create/edit dialog + detail page
- Circuits: managed within box detail
- Inspections: list + create page
- Issues: list + detail + update status/handling

2) Auth handling:
- Store token in localStorage
- Axios interceptors for auth header
- Router guards

## Quality bar
- Make it runnable on macOS with simple steps.
- Provide `README.md` with:
  - prerequisites
  - how to start mysql (docker-compose)
  - how to run backend and frontend
  - default admin credentials

## Execution instructions
Please:
1) Read `docs/demands/v1.1.md` carefully.
2) Skim `old-box/` to capture useful naming/UX conventions.
3) Generate the full codebase described above.
4) Ensure it builds and starts.

If any requirement is ambiguous, ask concise clarification questions **before** making large architectural decisions.
