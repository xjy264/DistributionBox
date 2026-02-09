# Claude Task 01 — Scaffold + Auth/RBAC/Menu (v1.1)

You are Claude Code inside repo `/Users/xuejiayao/project/distributionBox`.

## Hard constraints
- Do NOT copy old-box code; only use it to understand what modules exist.
- Preserve fields: DB columns must match Java entity fields (camelCase column names). Use entities as the source of truth.
- Every time you add/modify a module, update:
  1) `docs/demands/v1.1.md` (mark implemented sections)
  2) `docs/devlog/CHANGELOG-v1.1.md` (what changed + which SQL file)
  3) `sql/migrations/*.sql` (Flyway migrations)

## Goal of this task
Create a runnable full-stack skeleton and implement the first module: authentication + RBAC + menu management.

### Backend requirements (Java 21, Spring Boot 3, Maven)
Create `backend/` Spring Boot project:
- Dependencies: web, validation, security, jdbc (or jpa), flyway, mysql driver, lombok, springdoc-openapi.
- Use MyBatis-Plus OR Spring Data JPA: choose whichever is faster for clean CRUD. (No need to mimic old-box.)
- JWT auth: login endpoint returns token.
- Authorization: role/permission based for APIs.
- Provide these endpoints (new design allowed, but keep them coherent):
  - POST `/api/auth/login`
  - GET `/api/auth/me`
  - CRUD `/api/users`
  - CRUD `/api/roles`
  - CRUD `/api/menus`
  - POST `/api/roles/{roleId}/menus` set role's menu ids (similar capability as old-box)
  - GET `/api/roles/{roleId}/menus` get role menu ids
  - GET `/api/menus/tree` get menu tree for current user

### Database (MySQL 8.0)
- Create Flyway migration `sql/migrations/V1__init.sql` and wire Flyway in backend.
- Tables for auth/RBAC at least: users, roles, menus, roleMenus.
- Column names must be camelCase matching Java fields.
- Admin seeding is NOT required (human will insert via DB). But create schema to support it.

### Frontend (Vue 3 + Vite + TS + Element Plus)
Create `frontend/`:
- Login page
- Basic layout with sidebar menu from `/api/menus/tree`
- CRUD pages for Users / Roles / Menus (minimal, can be simple tables + dialogs)
- Token in localStorage, axios interceptor, router guard.

### Root tooling
- Add `docker-compose.yml` for MySQL 8.0.
- Update root `README.md` with run steps.

## Output expectations
- Code compiles.
- Backend starts and serves Swagger UI.
- Frontend starts and can login (assume there is a user in DB).

Proceed in small commits.
