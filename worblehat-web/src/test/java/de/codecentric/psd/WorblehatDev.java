package de.codecentric.psd;

import org.springframework.boot.SpringApplication;
import org.testcontainers.containers.PostgreSQLContainer;

public class WorblehatDev {

  public static final String POSTGRES_STARTED_PROP = "postgresStarted";

  public static void main(String[] args) {

    String postgresStarted = System.getProperty(POSTGRES_STARTED_PROP);
    if (postgresStarted == null) {
      initPostgresContainer();
      System.setProperty("postgresStarted", "true");
    }

    SpringApplication.run(Worblehat.class, args);
  }

  private static void initPostgresContainer() {
    PostgreSQLContainer postgreSQLContainer =
        new PostgreSQLContainer<>().withUsername("foo").withPassword("bar");

    postgreSQLContainer.start();

    System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    System.setProperty("spring.datasource.driver-class-name", "");
  }
}
