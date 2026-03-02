# Backend Development Workflow

## Project Structure

```
backend/
├── apps/                          # Spring Boot applications
│   ├── system/                    # System management app
│   ├── institution/               # Institution app
│   └── learning/                  # Learning app
├── core/
│   ├── domain/                    # Domain models, repository interfaces, errors
│   └── database/                  # JDBC entity mappings and repository implementations
├── libs/
│   ├── common/                    # Base classes (DomainEntity, Repository, UseCaseService, ...)
│   ├── infrastructure/            # Shared infrastructure dependencies (web, security, db drivers, ...)
│   ├── utils/                     # Utility classes
│   └── constant/                  # Shared constants
├── platform/                      # Gateway, config server, discovery
└── services/                      # Supporting services (notification, realtime, worker, ...)
```

## Architecture

Clean architecture with strict layer separation:

```
Web (Api + Resource)  -->  Application (UseCase)  -->  Domain (Model + Repository interface)
                                                            ^
                                                            |
                                            Infrastructure (JdbcRepository + JdbcEntity)
```

Component scanning in each app picks up beans implementing `UseCaseService`, `AdapterService`, and `Repository` across all `dev.ngb.*` packages.

---

## Layer-by-Layer Guide

### 1. Domain Layer (`core/domain`)

Package structure per bounded context:

```
domain/<context>/
├── model/           # Entity classes, status enums
├── repository/      # Repository interfaces
└── error/           # DomainError enums
```

#### Domain Entity

- Extend `AuditableDomainEntity<UUID>` (or `DomainEntity<UUID>` if no audit fields needed).
- Private constructor -- all instantiation through static factory methods.
- `create(...)` -- for creating new entities. Sets initial status, does NOT generate UUID.
- `reconstruct(...)` -- for rebuilding from persistence. Accepts all fields including audit fields.
- Domain behavior methods (e.g. `activate()`, `changeStatus()`) enforce invariants and throw `DomainError`.

```java
@Getter
public class Institution extends AuditableDomainEntity<UUID> {

    private String code;
    private InstitutionStatus status;

    private Institution() {}

    public static Institution create(String code, String name, String timezone) {
        Institution entity = new Institution();
        entity.code = code;
        entity.status = InstitutionStatus.SETUP;
        // ... set fields
        return entity;
    }

    public void activate() {
        if (this.status != InstitutionStatus.SETUP) {
            throw InstitutionError.NOT_IN_SETUP_STATUS.exception();
        }
        this.status = InstitutionStatus.ACTIVE;
    }

    public static Institution reconstruct(UUID id, ..., String createdBy, Instant createdAt, ...) {
        // set all fields including id and audit fields
    }
}
```

#### Domain Error

- Enum implementing `DomainError` in the `error/` package.
- Each error has a human-readable message.
- Throw via `InstitutionError.SOME_ERROR.exception()` or `.exception(detailsMap)`.

```java
@Getter
@RequiredArgsConstructor
public enum InstitutionError implements DomainError {
    INSTITUTION_NOT_FOUND("Institution not found"),
    INSTITUTION_CODE_ALREADY_EXISTS("Institution code already exists");

    private final String message;
}
```

#### Repository Interface

- Extend `Repository<T, UUID>` in the `repository/` package.
- Add domain-specific query methods as needed (e.g. `existsByCode`).

```java
public interface InstitutionRepository extends Repository<Institution, UUID> {
    boolean existsByCode(String code);
}
```

---

### 2. Infrastructure Layer (`core/database`)

Package structure per bounded context:

```
infrastructure/jdbc/<context>/
├── entity/          # JdbcEntity classes (@Table)
└── repository/      # JdbcRepository implementations
```

#### JDBC Entity

- Extend `AuditableEntity<UUID>` (or `SoftDeleteAuditableEntity<UUID>` for soft delete).
- Annotate with `@Table("table_name")`.
- Use `@SuperBuilder`, `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`.
- Store enums as `String` (not the enum type).

```java
@Getter @Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Table("institution")
public class InstitutionEntity extends AuditableEntity<UUID> {
    private String code;
    private String name;
    private String status;   // enum stored as String
    private String timezone;
}
```

#### JDBC Repository

- Extend `JdbcRepository<DomainType, JdbcEntityType, UUID>` and implement the domain repository interface.
- Implement `toDomain(J)` and `toJdbc(D)` mapping methods.
- Use base class helpers for custom queries: `findOneBy()`, `findAllBy()`, `existsBy()`, `countBy()`.

```java
public class InstitutionJdbcRepository
        extends JdbcRepository<Institution, InstitutionEntity, UUID>
        implements InstitutionRepository {

    // constructor with JdbcClient, JdbcTemplate, JdbcAggregateTemplate, JdbcMetadataHelper

    @Override
    public boolean existsByCode(String code) {
        return existsBy("code", code);
    }

    @Override
    protected Institution toDomain(InstitutionEntity entity) {
        return Institution.reconstruct(...);
    }

    @Override
    protected InstitutionEntity toJdbc(Institution domain) {
        return InstitutionEntity.builder()...build();
    }
}
```

---

### 3. Application Layer (in `apps/<app>`)

Package structure -- **one package per use case**:

```
<app>/<context>/application/usecase/<use_case_name>/
├── <UseCaseName>UseCase.java
└── dto/
    ├── <UseCaseName>Request.java
    └── ...other DTOs specific to this use case
```

#### Use Case

- Implement `UseCaseService` marker interface (auto-detected by component scan).
- Use `@RequiredArgsConstructor` for constructor injection.
- Public method(s) named `execute(...)` or `execute<Action>(...)`.
- Return `void` -- no response DTOs.
- Related operations can be grouped in one use case class with multiple methods.
- Validate business rules before performing mutations.

```java
@RequiredArgsConstructor
public class CreateInstitutionUseCase implements UseCaseService {

    private final InstitutionRepository institutionRepository;
    private final FacilityRepository facilityRepository;

    public void execute(CreateInstitutionRequest request) {
        // 1. Validate
        if (institutionRepository.existsByCode(request.code())) {
            throw InstitutionError.INSTITUTION_CODE_ALREADY_EXISTS.exception();
        }
        // 2. Create domain objects
        Institution institution = Institution.create(...);
        // 3. Persist
        institution = institutionRepository.save(institution);
        // 4. Side effects (child entities, events, etc.)
    }
}
```

#### DTOs

- Java `record` types -- plain data, no validation annotations.
- Scoped per use case (each use case has its own DTOs in its own `dto/` package).

```java
public record CreateInstitutionRequest(
        String code,
        String name,
        String timezone,
        List<CreateFacilityRequest> facilities
) {}
```

---

### 4. Web Layer (in `apps/<app>`)

Package structure:

```
<app>/<context>/web/
├── <Entity>Api.java         # Interface with Spring MVC annotations
└── <Entity>Resource.java    # @RestController implementation
```

#### Api Interface

- Annotate with `@RequestMapping("/api/<entities>")`.
- Define method signatures with `@PostMapping`, `@PatchMapping`, `@GetMapping`, etc.
- Include `@RequestBody`, `@PathVariable`, `@ResponseStatus` on method parameters/methods.

```java
@RequestMapping("/api/institutions")
public interface InstitutionApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody CreateInstitutionRequest request);

    @PatchMapping("/{id}/activate")
    void activate(@PathVariable UUID id);
}
```

#### Resource (Controller)

- `@RestController` implementing the Api interface.
- `@RequiredArgsConstructor` for injecting use cases.
- Each method delegates directly to the corresponding use case -- no logic in the controller.

```java
@RestController
@RequiredArgsConstructor
public class InstitutionResource implements InstitutionApi {

    private final CreateInstitutionUseCase createInstitutionUseCase;

    @Override
    public void create(CreateInstitutionRequest request) {
        createInstitutionUseCase.execute(request);
    }
}
```

---

## Adding a New Feature -- Checklist

1. **Domain model** (`core/domain/.../model/`) -- add or update entity with `create()` and behavior methods.
2. **Domain error** (`core/domain/.../error/`) -- add error codes if needed.
3. **Repository interface** (`core/domain/.../repository/`) -- add custom query methods if needed.
4. **JDBC entity** (`core/database/.../entity/`) -- add or update entity mapping.
5. **JDBC repository** (`core/database/.../repository/`) -- implement new repository methods, update `toDomain`/`toJdbc` if model changed.
6. **Use case** (`apps/<app>/.../application/usecase/<name>/`) -- create use case class + DTOs.
7. **Web layer** (`apps/<app>/.../web/`) -- add method to Api interface + implement in Resource.

## Conventions

- **IDs**: `UUID` type. ID generation is handled by infrastructure, not domain `create()`.
- **Enums in DB**: Stored as `String`, converted via `.name()` / `valueOf()`.
- **No `@Transactional` on use cases**: Transaction management is handled at the infrastructure level.
- **No validation annotations on DTOs**: Validation is done in use case business logic.
- **Lombok**: `@Getter`, `@RequiredArgsConstructor`, `@SuperBuilder` -- avoid `@Data`.
- **Base package**: `dev.ngb` (`PackageConstants.BASE_PACKAGE`).
