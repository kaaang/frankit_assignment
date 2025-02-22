package kr.co.frankit_assignment.config;

import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
public class TestPostgresContainerConfig {
    @Container
    private static final PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:15")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");
}
