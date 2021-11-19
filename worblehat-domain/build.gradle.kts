plugins {
  `java-library`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies:2.6.0"))
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("org.liquibase:liquibase-core")

  implementation("com.google.code.findbugs:jsr305:3.0.2")
  implementation("com.google.guava:guava:30.1.1-jre")

  compileOnly("org.projectlombok:lombok:1.18.22")
  annotationProcessor("org.projectlombok:lombok:1.18.22")

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
