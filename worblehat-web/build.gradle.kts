plugins {
    java
	id("org.springframework.boot") version "2.3.5.RELEASE"
}

apply(plugin = "io.spring.dependency-management")

repositories {
  mavenCentral()
}

dependencies {
    implementation(project(":worblehat-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework:spring-tx")
    implementation("javax.persistence:javax.persistence-api")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.apache.commons:commons-lang3")
    implementation("commons-validator:commons-validator:1.7")
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.12")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.14.3"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.google.guava:guava:30.0-jre") // TODO, replace with Set.of
}

tasks {
  jar {
      enabled = true
  }
  bootJar {
      classifier = "executable"
  }
  test {
    useJUnitPlatform()
  }
  processResources {
    filesMatching("*.properties") {
      expand(project.properties)
    }
  }
}
