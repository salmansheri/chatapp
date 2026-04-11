# AGENTS

## Scope and layout
- This repo has two independent projects: `chatappfrontend` (TanStack Start + Vite + pnpm) and `ChatAppBackend` (Spring Boot + Maven wrapper).
- There is no workspace task runner at repo root; run commands inside each project directory.

## Frontend (`chatappfrontend`)
- Use `pnpm` (lockfile is `chatappfrontend/pnpm-lock.yaml`).
- Main commands: `pnpm dev` (port `3000`), `pnpm build`, `pnpm test`, `pnpm lint`, `pnpm format`, `pnpm check`.
- Routing is file-based TanStack Router. `src/routeTree.gen.ts` is generated; do not edit it.
- API client code in `src/generated/*.gen.ts` is generated from `chatapi.json`; do not hand-edit generated files.
- Regenerate API client with `pnpm exec openapi-ts -f openapi-ts.config.ts` (config points to `chatapi.json` and outputs `src/generated`).
- `biome.json` intentionally excludes `src/routeTree.gen.ts` and `src/styles.css` from checks.
- `package.json` has an `orval` script, but repo uses `@hey-api/openapi-ts` config (`openapi-ts.config.ts`) for current generated client.

## Backend (`ChatAppBackend`)
- Use Maven wrapper: `./mvnw spring-boot:run` and `./mvnw test`.
- Runtime dependencies expected locally: Postgres on `localhost:5432` and Keycloak on `localhost:9090` (see `docker-compose.yml`).
- `docker-compose.yml` service names/ports: `postgres` (`5432`), `keycloak` (`9090->8080`), with default creds checked in.
- Backend defaults from `application.yaml`: datasource `chatappdb`/`postgres` user, `flyway.enabled=false`, `spring.jpa.hibernate.ddl-auto=update`.
- OpenAPI is exposed by springdoc (security allows `/v3/api-docs/**`), and frontend `chatapi.json` is a checked-in snapshot.

## Cross-project gotchas
- Frontend generated API client uses base URL `http://localhost:8080`.
- CORS in backend currently allows only `http://localhost:4200` (`SecurityConfig`), while frontend dev server runs on `3000`; adjust if API calls fail in browser.
- WebSocket allowed origin in backend is `http://locahost:3000` (typo in `WebSocketConfig`), which can break socket setup until fixed.
