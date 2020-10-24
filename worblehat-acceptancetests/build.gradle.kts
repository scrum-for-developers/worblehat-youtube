plugins {
    id("worblehat.java-module")
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

tasks.test {
    jvmArgumentProviders += listOf(CucumberOptions(project.layout.buildDirectory.dir("cucumber")), VncRecordingDirectoryProvider(project.layout.buildDirectory.dir("testcontainers")))
}

open class CucumberOptions(@OutputDirectory val outputs: Provider<Directory>) : CommandLineArgumentProvider {

    @Internal
    val htmlReport = outputs.map { it.file("cucumber.html") }

    @Internal
    val junitReport = outputs.map { it.file("cucumber.xml") }

    @Internal
    val jsonReport = outputs.map { it.file("cucumber.json") }

    override fun asArguments() =
        listOf("-Dcucumber.plugin=pretty,html:${htmlReport.get().asFile},junit:${junitReport.get().asFile},json:${jsonReport.get().asFile}")
}

open class VncRecordingDirectoryProvider(@OutputDirectory val outputDirectory: Provider<Directory>) : CommandLineArgumentProvider {

    override fun asArguments() = listOf("-Dtestcontainers.vnc.recordingDirectory=${outputDirectory.get().asFile}")
}
