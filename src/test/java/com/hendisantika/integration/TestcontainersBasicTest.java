package com.hendisantika.integration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic Testcontainers integration test to verify the setup is working correctly
 * <p>
 * This test verifies that:
 * 1. Testcontainers dependencies are correctly configured
 * 2. PostgreSQL and MySQL containers can be started
 * 3. Basic container operations work
 */
@Testcontainers
public class TestcontainersBasicTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Test
    void shouldStartPostgreSQLContainer() {
        // Verify PostgreSQL container is running
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();

        // Verify connection details
        assertThat(postgres.getDatabaseName()).isEqualTo("testdb");
        assertThat(postgres.getUsername()).isEqualTo("test");
        assertThat(postgres.getPassword()).isEqualTo("test");

        // Verify JDBC URL is generated
        assertThat(postgres.getJdbcUrl()).isNotNull();
        assertThat(postgres.getJdbcUrl()).contains("postgresql");

        System.out.println("PostgreSQL JDBC URL: " + postgres.getJdbcUrl());
    }

    @Test
    void shouldStartMySQLContainer() {
        // Verify MySQL container is running
        assertThat(mysql.isCreated()).isTrue();
        assertThat(mysql.isRunning()).isTrue();

        // Verify connection details
        assertThat(mysql.getDatabaseName()).isEqualTo("testdb");
        assertThat(mysql.getUsername()).isEqualTo("test");
        assertThat(mysql.getPassword()).isEqualTo("test");

        // Verify JDBC URL is generated
        assertThat(mysql.getJdbcUrl()).isNotNull();
        assertThat(mysql.getJdbcUrl()).contains("mysql");

        System.out.println("MySQL JDBC URL: " + mysql.getJdbcUrl());
    }

    @Test
    void shouldHaveCorrectContainerImages() {
        // Verify container images
        assertThat(postgres.getDockerImageName()).contains("postgres:15-alpine");
        assertThat(mysql.getDockerImageName()).contains("mysql:8.0");
    }

    @Test
    void shouldExposeCorrectPorts() {
        // Verify ports are exposed
        assertThat(postgres.getExposedPorts()).contains(5432);
        assertThat(mysql.getExposedPorts()).contains(3306);

        // Verify mapped ports are available
        assertThat(postgres.getMappedPort(5432)).isGreaterThan(0);
        assertThat(mysql.getMappedPort(3306)).isGreaterThan(0);
    }
}