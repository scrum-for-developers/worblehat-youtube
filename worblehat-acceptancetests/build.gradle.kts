plugins {
    id("worblehat.acceptance-test-module")
}

dependencies {
    testImplementation(platform(project(":worblehat-platform")))
    testImplementation(project(":worblehat-domain"))
    testImplementation(project(":worblehat-web"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit")
    testImplementation("io.cucumber:cucumber-spring")

    testImplementation("org.seleniumhq.selenium:selenium-leg-rc")
    testImplementation("org.seleniumhq.selenium:selenium-server")

    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:selenium")

    testImplementation("com.google.guava:guava")
    testImplementation("org.apache.commons:commons-lang3")
}
