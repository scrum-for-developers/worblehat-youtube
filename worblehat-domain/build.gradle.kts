plugins {
    id("worblehat.java-module")
    id("worblehat.lombok")
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

    testImplementation("org.mockito:mockito-core")
    testImplementation("com.github.npathai:hamcrest-optional")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
