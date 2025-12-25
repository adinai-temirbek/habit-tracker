# Goal & Habit Tracker Backend API

A secure Spring Boot REST API for tracking personal goals and habits.

## Project Overview
This backend API allows users to:
- Register and login with JWT authentication
- Create and list personal goals
- Add daily/weekly habits to goals
- Mark habits as complete
- View habits by goal

Extra features:
- User ownership checks (users can only access their own data)
- Strategy Pattern for habit reset logic
- Clean JSON responses (fixed bidirectional serialization)

## Tech Stack
- Java 17
- Spring Boot 3.3.5
- Spring Web, Spring Data JPA, Spring Security
- JJWT for JWT tokens
- H2 Database (local development)
- PostgreSQL (production/deployment)
- Maven

## Architecture & Design
- **Package Structure**:
  - `controller` - REST endpoints
  - `service` & `service.impl` - Business logic (Service Layer Pattern)
  - `repository` - JPA repositories
  - `model` - Entities with JPA relationships
  - `security` - JWT authentication
  - `strategy` - Strategy Pattern for habit frequency
  - `dto` - Request DTOs

- **OOP Principles**:
  - Inheritance: `BaseEntity` extended by User, Goal, Habit
  - Encapsulation: Private fields with getters/setters
  - Abstraction: Service interfaces
  - Polymorphism: Service implementations

- **Design Patterns**:
  - Service Layer Pattern
  - Strategy Pattern (DailyHabitStrategy, WeeklyHabitStrategy)

- **Security**:
  - JWT authentication with BCrypt password hashing
  - Protected endpoints require Bearer token
  - Ownership checks prevent access to others' data

## How to Run Locally
1. Clone the repository
2. Open in IntelliJ IDEA
3. Run `HabitTrackerApplication.java`
4. API available at `http://localhost:8080`
5. Use Postman to test endpoints

## Main Endpoints
- `POST /auth/register` - Register user (returns JWT token)
- `POST /auth/login` - Login (returns JWT token)
- `GET /users` - List users (protected)
- `POST /goals` - Create goal (protected)
- `GET /goals/users/{userId}` - List user's goals
- `POST /habits` - Create habit
- `PUT /habits/{id}/complete` - Mark habit complete
- `GET /habits/goals/{goalId}` - List habits for goal

All protected endpoints require header:  
`Authorization: Bearer <token>`

## Database
- Local: H2 in-memory (view at http://localhost:8080/h2-console, JDBC URL: jdbc:h2:mem:testdb)
- Production: PostgreSQL

## Testing
Tested with Postman - all endpoints functional including authentication flow and ownership checks.

## Deployment
Deployed on Railway.app with PostgreSQL (optional for demo).

Thank you for reviewing my project!
