# old-box Backend Migration Notes

This document summarizes the backend migration of `old-box` into `DistributionBox` (Spring Boot 3.3.5 + Java 21, MyBatis-Plus, MySQL, Flyway, JWT).

**What moved**
- Entities, mappers, services, and controllers from `old-box` were ported into `backend` under the `com.distributionbox` package.
- Mapper XML files were copied to `backend/src/main/resources/mapper` and namespaces updated.
- The unified response wrapper is `com.distributionbox.common.Result` (same structure as old-box).
- Global exception handling is in `com.distributionbox.exception.GlobalExceptionHandler`.

**MyBatis-Plus + Flyway**
- JPA/Hibernate was removed from `backend/pom.xml`; MyBatis-Plus was added.
- `@MapperScan("com.distributionbox.mapper")` is configured in `DistributionBoxApplication`.
- `backend/src/main/resources/application.yml` includes MyBatis-Plus settings and Flyway points to `../sql/migrations`.
- Flyway scripts in `sql/migrations` define all tables for the migrated modules. Columns were normalized to snake_case to match mapper SQL and MyBatis camel-case mapping.

**JWT + Security**
- JWT generation uses `JwtService` (claims include `userId` and `role`) inside `UserServiceImpl.login`.
- `SecurityConfig` allows `/login`, `/register`, swagger endpoints, and public file download routes; other endpoints require JWT.
- Unauthenticated access returns HTTP 401 with a JSON `Result` payload via `RestAuthenticationEntryPoint`.

**Files**
- Upload path uses `app.files.upload-path`.
- Base URL uses `app.files.base-url`, which is stored in the database as the file URL.
- Endpoints:
  - `POST /files/upload` for upload
  - `GET /files/{uuid}` for download
  - `GET /files/preview/{uuid}` for inline preview

**Run**
- Build: `mvn -q -DskipTests package` from `backend/`
- Swagger UI: `/swagger-ui.html`
