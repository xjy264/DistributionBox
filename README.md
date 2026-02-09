# DistributionBox

Distribution Panel (配电箱) Management System.

## Goals
- Asset registry for panels (location, model, capacity, photos)
- Circuit/breaker inventory & labeling
- Maintenance logs, inspections, fault history
- Permissions and audit trail

## Structure
- `backend/` Spring Boot 3 (Java 21) API
- `frontend/` Vue 3 + Vite + TS UI
- `files/` uploaded files (local storage root)
- `sql/migrations/` Flyway migrations
- `docs/` specifications & dev logs
- `scripts/` utilities

## Quick Start
1. Start MySQL 8
   - `docker compose up -d`
2. Run backend
   - `cd backend`
   - `mvn clean package`
   - `mvn spring-boot:run`
3. Run frontend
   - `cd frontend`
   - `npm install`
   - `npm run dev`

## Notes
- Backend default DB: `distributionbox` (user `root` / password `root`).
- Flyway migrations are stored in `sql/migrations/` and applied on startup.
- Uploads are stored under `./files` by default.
- Admin user should be inserted manually (password must be BCrypt-encoded).
