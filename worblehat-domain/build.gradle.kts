plugins {
  `java-library`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.4.RELEASE"))
  api("org.springframework.boot:spring-boot-starter-data-jpa") {
      because("Public types like BookRepository extend from Spring Data JPA types")
  }
  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("org.liquibase:liquibase-core")

  implementation("com.google.code.findbugs:jsr305:3.0.2")
  implementation("com.google.guava:guava:30.0-jre")

  compileOnly("org.projectlombok:lombok:1.18.16")
  annotationProcessor("org.projectlombok:lombok:1.18.16")

  testImplementation("org.mockito:mockito-core")
  testImplementation("com.github.npathai:hamcrest-optional:2.0.0")
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
  test {
    useJUnitPlatform()
  }
}
