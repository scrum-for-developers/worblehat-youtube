plugins {
    id("worblehat.java-module")
}

tasks.test {
    jvmArgumentProviders += listOf(CucumberOptions(project.layout.buildDirectory.dir("cucumber")), VncRecordingDirectoryProvider(project.layout.buildDirectory.dir("testcontainers")))
}
