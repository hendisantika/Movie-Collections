# Testcontainers Integration Guide

This document explains how to use Testcontainers in the Movie Collections project for integration testing with real
databases.

## Overview

Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common
databases, message brokers, web browsers, or anything else that can run in a Docker container.

## Dependencies Added

The following Testcontainers dependencies have been added to `pom.xml`:

```xml
<properties>
    <testcontainers.version>1.19.8</testcontainers.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>testcontainers-bom</artifactId>
            <version>${testcontainers.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Testcontainers Core -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Database Containers -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>mysql</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Database Drivers -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Spring Boot Testcontainers Integration -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-testcontainers</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Usage Examples

### 1. Basic Container Usage

```java
@Testcontainers
public class TestcontainersBasicTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Test
    void shouldStartPostgreSQLContainer() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
        
        String jdbcUrl = postgres.getJdbcUrl();
        System.out.println("PostgreSQL JDBC URL: " + jdbcUrl);
    }
}
```

### 2. Spring Data JPA Integration

```java
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true"
})
public class TestcontainersExampleTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldPersistAndFindMovie() {
        // Given
        Movie movie = Movie.builder()
                .name("Inception")
                .category("Sci-Fi")
                .description("Dream within a dream")
                .date(new Date())
                .rating(8.8f)
                .build();

        // When
        Movie savedMovie = entityManager.persistAndFlush(movie);
        entityManager.clear();

        // Then
        Movie foundMovie = entityManager.find(Movie.class, savedMovie.getId());
        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getName()).isEqualTo("Inception");
    }
}
```

### 3. Full Application Integration Testing

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class FullApplicationIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllMoviesFromAPI() throws Exception {
        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
```

## Key Annotations

### @Testcontainers

- Enables Testcontainers support for the test class
- Manages container lifecycle (start/stop)

### @Container

- Marks a field as a Testcontainer
- `static` containers are shared across all test methods
- Non-static containers are created for each test method

### @ServiceConnection (Spring Boot 3.1+)

- Automatically configures Spring Boot datasource from container
- Eliminates need for manual configuration of JDBC URL, username, password

### @AutoConfigureTestDatabase

- `replace = Replace.NONE` prevents Spring from replacing your datasource
- Required when using real databases instead of in-memory H2

## Best Practices

### 1. Container Reuse

```java
@Container
static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withReuse(true); // Reuse container across test runs
```

### 2. Test Configuration

Create `application-test.properties` for test-specific settings:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
logging.level.org.testcontainers=INFO
```

### 3. Shared Container Configuration

```java
@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
    }
}
```

## Available Container Images

### PostgreSQL

```java
@Container
static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
```

### MySQL

```java
@Container
static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
```

### MariaDB

```java
@Container
static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:10.6")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
```

## Running Tests

### Run all tests

```bash
mvn test
```

### Run specific Testcontainers test

```bash
mvn test -Dtest=TestcontainersBasicTest
```

### Run with Docker environment

Make sure Docker is running before executing tests:

```bash
docker --version
mvn test
```

## Prerequisites

1. **Docker**: Must be installed and running
2. **Java 21+**: Required by the project
3. **Maven 3.6+**: For building and running tests
4. **Internet Connection**: To pull Docker images (first time only)

## Troubleshooting

### Docker Connection Issues

If you get Docker connection errors:

```bash
# Check Docker is running
docker ps

# Check Docker daemon
docker info
```

### Port Conflicts

Testcontainers automatically assigns random ports to avoid conflicts:

```java
int mappedPort = postgres.getMappedPort(5432);
String jdbcUrl = postgres.getJdbcUrl();
```

### Image Pull Issues

If Docker images fail to pull:

```bash
# Pre-pull images manually
docker pull postgres:15-alpine
docker pull mysql:8.0
```

### Performance Optimization

- Use `.withReuse(true)` for faster test execution
- Use lightweight Alpine images when possible
- Share containers across test classes when appropriate

## Benefits

1. **Real Database Testing**: Tests run against actual database engines
2. **Environment Consistency**: Same database version in all environments
3. **Isolation**: Each test gets a fresh database instance
4. **CI/CD Friendly**: No external database dependencies
5. **Multi-Database Support**: Easy to test against different database types

## Example Test Results

When running Testcontainers tests, you'll see output like:

```
PostgreSQL JDBC URL: jdbc:postgresql://localhost:55186/testdb?loggerLevel=OFF
Database version: PostgreSQL 15.13 on aarch64-unknown-linux-musl, compiled by gcc (Alpine 14.2.0) 14.2.0, 64-bit
Container is started (JDBC URL: jdbc:postgresql://localhost:55186/testdb?loggerLevel=OFF)
```

This confirms that tests are running against a real PostgreSQL database in a Docker container.