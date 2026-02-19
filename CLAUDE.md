# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Distribution Panel (配电箱) Management System - a full-stack application for managing electrical distribution panels with asset registry, circuit inventory, maintenance logs, and access control.

## Development Commands

### Database
```bash
docker compose up -d  # Start MySQL 8 container
```

### Backend (Spring Boot 3 + Java 21)
```bash
cd backend
mvn clean package           # Build
mvn spring-boot:run         # Run (port 8888)
mvn test                    # Run tests
mvn test -Dtest=TestClass   # Run single test class
```

### Frontend (Vue 3 + Vite + TypeScript)
```bash
cd frontend
npm install                 # Install dependencies
npm run dev                 # Dev server (port 5173)
npm run build               # Production build
npm run preview             # Preview production build
```

## Architecture

### Backend Structure (`backend/src/main/java/com/distributionbox/`)
- **controller/** - REST endpoints, use DTO classes in `controller/dto/`
- **service/** - Business logic with interfaces; implementations in `service/impl/`
- **mapper/** - MyBatis-Plus mappers; XML in `resources/mapper/`
- **entity/** - JPA entities with Lombok annotations
- **security/** - JWT authentication with Spring Security (JwtService, JwtAuthenticationFilter)
- **common/** - Result wrapper class for API responses, Constants interface

### Frontend Structure (`frontend/src/`)
- **views/** - Page components (one .vue per route)
- **layouts/** - MainLayout.vue wraps authenticated pages
- **router/** - Vue Router with auth guard (redirects to /login without token)
- **stores/** - Pinia stores; user.ts manages auth state (token, user, menus)
- **api/** - Axios HTTP client with Bearer token interceptor

### Database
- MySQL 8 (user: root, password: root, database: distributionbox)
- Flyway migrations in `sql/migrations/` (V1__*.sql naming)
- Migrations run automatically on backend startup

### API Conventions
- All REST responses wrapped in `Result{code, msg, data}`
- Status codes: 200 (success), 400 (param error), 401 (unauthorized), 500 (system error)
- Frontend proxies `/api` to `http://localhost:8888`

### File Storage
- Uploads stored in `./files/` directory
- Served via `/files/` endpoint

## Key Entities
User, Role, Menu, RoleMenu, Department, Box (配电箱), Components (元器件), Inspection (巡检), Detect (检测), Check (抽检), Record (记录), Maintenance (维护), Fix (修理)

## Notes
- Legacy code exists in `myvue/` and `old-box/` - current implementation is in `frontend/` and `backend/`
- Root `pom.xml` and `src/` are from legacy codebase (Spring Boot 2.7 + Java 8)
- Swagger UI available at `/swagger-ui.html`
