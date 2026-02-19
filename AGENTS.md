# AGENTS.md - Development Guidelines for OKR Backend

This document provides essential guidelines for agentic coding agents working in this Spring Boot backend repository.

## Project Overview

- **Framework**: Spring Boot 2.7.4
- **Java Version**: 17
- **Build Tool**: Maven
- **Database**: PostgreSQL with JPA/Hibernate
- **Additional Libraries**: Lombok for boilerplate reduction
- **Package Structure**: `com.example.okr_back`

## Build & Development Commands

### Core Maven Commands
```bash
# Clean and compile the project
./mvnw clean compile

# Run the application (dev profile by default)
./mvnw spring-boot:run

# Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
./mvnw spring-boot:run -Dspring-boot.run.profiles=staging

# Build the application
./mvnw clean package

# Build without running tests
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=OkrBackApplicationTests

# Run tests with specific profile
./mvnw test -Dspring.profiles.active=dev
```

### Running the Application
- **Development**: `./mvnw spring-boot:run` (port 8001)
- **Staging**: `./mvnw spring-boot:run -Dspring-boot.run.profiles=staging`
- **Production**: `./mvnw spring-boot:run -Dspring-boot.run.profiles=prod`

## Code Style Guidelines

### Package Organization
- `com.example.okr_back` - Root package
- `com.example.okr_back.entities` - JPA entities
- `com.example.okr_back.controller` - REST controllers
- `com.example.okr_back.persistence` - Repository interfaces
- `com.example.okr_back.service` - Business logic (create if needed)
- `com.example.okr_back.dto` - Data transfer objects (create if needed)

### Entity Standards
- Use JPA annotations (`@Entity`, `@Table`, `@Id`, `@Column`)
- Always use Lombok annotations: `@Data`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`
- Use snake_case for table names in `@Table(name = "table_name")`
- Primary keys should be `Long` with `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- Use `@Column(nullable = false)` for required fields

### Controller Standards
- Use `@RestController` for API endpoints
- Apply `@RequestMapping("/base-path")` at class level
- Use proper HTTP method annotations: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Return `ResponseEntity<T>` for proper HTTP response handling
- Keep controllers thin - delegate business logic to services

### Repository Standards
- Extend `JpaRepository<Entity, Long>`
- Keep interfaces minimal - add custom methods only when needed
- Place in `persistence` package

### Import Organization
- Standard Java imports first
- Third-party imports (Spring, JPA, etc.)
- Local imports last
- No wildcard imports (`import java.util.*`)

### Naming Conventions
- **Classes**: PascalCase (e.g., `User`, `AuthController`)
- **Methods**: camelCase (e.g., `getUser()`, `createProduct()`)
- **Variables**: camelCase (e.g., `userId`, `productList`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_ATTEMPTS`)
- **Database**: snake_case for table/column names

### Error Handling
- Use Spring's `@ControllerAdvice` for global exception handling
- Create custom exception classes when needed
- Return appropriate HTTP status codes via `ResponseEntity`

### Configuration Profiles
- **dev**: PostgreSQL on localhost:5432, DDL create-drop, SQL logging enabled
- **staging**: Configure as needed
- **prod**: Configure as needed
- Default profile is `dev`

## Testing Guidelines

### Test Structure
- Place tests in `src/test/java/com/example/okr_back/`
- Use `@SpringBootTest` for integration tests
- Use `@Test` from JUnit 5 (`org.junit.jupiter.api.Test`)
- Test classes should mirror source package structure

### Database Testing
- Tests use the same database configuration as active profile
- Consider using `@DataJpaTest` for repository-only tests
- Use `@Transactional` to rollback changes after tests

## Development Workflow

1. Always run tests before committing: `./mvnw test`
2. Use the dev profile for local development
3. Follow the existing package structure and naming conventions
4. Add proper JPA annotations and Lombok boilerplate to new entities
5. Keep controllers focused on HTTP concerns, business logic in services

## Important Notes

- The project uses Maven profiles for environment-specific configuration
- Lombok is configured - always use its annotations for getters/setters/constructors
- Database schema is managed by JPA in dev mode (`create-drop`)
- Default server port is 8001 for development
- PostgreSQL is the configured database with HikariCP connection pooling