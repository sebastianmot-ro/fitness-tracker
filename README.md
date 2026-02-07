# Fitness Tracker

This is a small Spring Boot app for tracking users and their workouts. It ships with a simple static front-end (served from `src/main/resources/static`) and a JSON API for users and activities.

## What it does
- Create users and list them.
- Log activities (run/bike), including distance, duration, calories, and notes.
- Calculates pace for runs and speed for bike rides.

## Tech stack
- Java 21 + Spring Boot
- Spring Data JPA
- PostgreSQL

## Quick start
1. Make sure Postgres is running and create a database:
   - `createdb fitness_db`
2. Update credentials if needed in `src/main/resources/application.properties`.
3. Run the app:
   - `./mvnw spring-boot:run`
4. Open the UI:
   - http://localhost:8080

## API at a glance
Base URL: `http://localhost:8080` (not hosted)

Users:
- `POST /users` – create a user
- `GET /users` – list users
- `GET /users/{id}` – get one user
- `DELETE /users/{id}` – delete a user

Activities (per user):
- `POST /users/{userId}/activities` – create activity
- `GET /users/{userId}/activities` – list activities
- `DELETE /users/{userId}/activities/{id}` – delete activity

## Notes
- The app uses JPA auto-update for schema (`spring.jpa.hibernate.ddl-auto=update`), so the tables are created on first run.
- Default Postgres settings are in `application.properties`; change them as needed.
