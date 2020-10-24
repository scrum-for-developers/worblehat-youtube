plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(project(":worblehat-platform")))
    api("org.springframework.boot:spring-boot-starter-data-jpa") {
        because("Public types like BookRepository extend from Spring Data JPA types")
    }
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.liquibase:liquibase-core")

    implementation("com.google.code.findbugs:jsr305")
    implementation("com.google.guava:guava")

    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")

    testImplementation("org.mockito:mockito-core")
    testImplementation("com.github.npathai:hamcrest-optional")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
