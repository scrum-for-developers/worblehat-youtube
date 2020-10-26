plugins {
    id("worblehat.java-module")
    id("worblehat.lombok")
    id("org.springframework.boot") version "2.3.4.RELEASE"
}

dependencies {
    implementation(platform(project(":worblehat-platform")))
    implementation(project(":worblehat-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-tx")
    implementation("javax.persistence:javax.persistence-api")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("de.codecentric:chaos-monkey-spring-boot")

    implementation("org.apache.commons:commons-lang3")
    implementation("commons-validator:commons-validator")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.google.guava:guava") // TODO, replace with Set.of
}

tasks {
  jar {
      enabled = true
  }
  bootJar {
      classifier = "executable"
  }
  processResources {
    filesMatching("*.properties") {
      expand(project.properties)
    }
  }
}
