plugins {
    java
	id("org.springframework.boot") version "2.5.6"
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
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.2"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.google.guava:guava:30.1.1-jre") // TODO, replace with Set.of
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
