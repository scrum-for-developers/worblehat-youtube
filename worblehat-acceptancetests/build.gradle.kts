plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

val cucumberVersion = "6.8.1"
val seleniumVersion = "3.141.59"

dependencies {
    testImplementation(project(":worblehat-domain"))
    testImplementation(project(":worblehat-web"))

    testImplementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.4.RELEASE"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.cucumber:cucumber-java:${cucumberVersion}")
    testImplementation("io.cucumber:cucumber-junit:${cucumberVersion}")
    testImplementation("io.cucumber:cucumber-spring:${cucumberVersion}")

    testImplementation("org.seleniumhq.selenium:selenium-leg-rc:${seleniumVersion}")
    testImplementation("org.seleniumhq.selenium:selenium-server:${seleniumVersion}")

    testImplementation(platform("org.testcontainers:testcontainers-bom:1.15.0-rc2"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:selenium")

    testImplementation("com.google.guava:guava:30.0-jre")
    testImplementation("org.apache.commons:commons-lang3:3.11")
}
