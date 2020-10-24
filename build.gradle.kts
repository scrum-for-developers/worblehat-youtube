apply(from = "gradle/build-scan-data.gradle")

tasks.register("quickCheck") {
    dependsOn(":worblehat-domain:check", ":worblehat-web:check")
}
