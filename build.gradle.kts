apply(from = "gradle/build-scan-data.gradle")

tasks.register("quickTest") {
    dependsOn(":worblehat-domain:test", ":worblehat-web:quickTest")
}
