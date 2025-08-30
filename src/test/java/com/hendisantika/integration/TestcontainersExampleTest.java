package com.hendisantika.integration;

import com.hendisantika.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example of how to use Testcontainers with Spring Data JPA
 * <p>
 * This test demonstrates:
 * 1. Using @DataJpaTest for JPA slice testing
 * 2. Using TestEntityManager for direct database operations
 * 3. Using PostgreSQL Testcontainer for real database testing
 * 4. Using @ServiceConnection for automatic datasource configuration
 */
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
                .image("inception.jpg")
                .build();

        // When - persist using TestEntityManager
        Movie savedMovie = entityManager.persistAndFlush(movie);
        entityManager.clear(); // Clear the persistence context

        // Then - find the movie
        Movie foundMovie = entityManager.find(Movie.class, savedMovie.getId());

        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getName()).isEqualTo("Inception");
        assertThat(foundMovie.getCategory()).isEqualTo("Sci-Fi");
        assertThat(foundMovie.getDescription()).isEqualTo("Dream within a dream");
        assertThat(foundMovie.getRating()).isEqualTo(8.8f);
        assertThat(foundMovie.getImage()).isEqualTo("inception.jpg");
    }

    @Test
    void shouldHandleMovieWithNullValues() {
        // Given
        Movie movie = Movie.builder()
                .name("Test Movie")
                .category("Test")
                .build(); // Other fields will be null

        // When
        Movie savedMovie = entityManager.persistAndFlush(movie);
        entityManager.clear();

        // Then
        Movie foundMovie = entityManager.find(Movie.class, savedMovie.getId());

        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getName()).isEqualTo("Test Movie");
        assertThat(foundMovie.getCategory()).isEqualTo("Test");
        assertThat(foundMovie.getDescription()).isNull();
        assertThat(foundMovie.getImage()).isNull();
        assertThat(foundMovie.getDate()).isNull();
        assertThat(foundMovie.getRating()).isEqualTo(0.0f); // primitive default value
    }

    @Test
    void shouldVerifyContainerIsRunning() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
        assertThat(postgres.getDatabaseName()).isEqualTo("testdb");

        System.out.println("Test is running with PostgreSQL container: " + postgres.getJdbcUrl());
    }

    @Test
    void shouldUseRealPostgreSQLDatabase() {
        // This test verifies we're actually using PostgreSQL and not H2
        String sql = "SELECT version()";
        String version = (String) entityManager.getEntityManager()
                .createNativeQuery(sql)
                .getSingleResult();

        assertThat(version).containsIgnoringCase("PostgreSQL");
        System.out.println("Database version: " + version);
    }
}