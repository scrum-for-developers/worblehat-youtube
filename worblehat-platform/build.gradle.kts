plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

val cucumberVersion = "6.8.1"
val seleniumVersion = "3.141.59"

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:2.3.4.RELEASE"))
    api(platform("org.testcontainers:testcontainers-bom:1.15.0-rc2"))

    constraints {
        runtime("de.codecentric:chaos-monkey-spring-boot:2.2.0")
        runtime("org.postgresql:postgresql:42.2.18")
        runtime("org.hibernate.validator:hibernate-validator:6.1.6.Final") {
            because("hibernate-validator:7.x requires the use jakarta.validation")
        }

        api("commons-validator:commons-validator:1.7")
        api("org.apache.commons:commons-lang3:3.11")
        api("com.google.guava:guava:30.0-jre")
        api("com.google.code.findbugs:jsr305:3.0.2")
        api("org.projectlombok:lombok:1.18.16")

        api("com.github.npathai:hamcrest-optional:2.0.0")
        api("org.mockito:mockito-core:3.5.15")

        api("io.cucumber:cucumber-java:${cucumberVersion}")
        api("io.cucumber:cucumber-junit:${cucumberVersion}")
        api("io.cucumber:cucumber-spring:${cucumberVersion}")

        api("org.seleniumhq.selenium:selenium-leg-rc:${seleniumVersion}")
        api("org.seleniumhq.selenium:selenium-server:${seleniumVersion}")
    }
}
