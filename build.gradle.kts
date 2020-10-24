tasks.register("quickCheck") {
    dependsOn(":worblehat-domain:check", ":worblehat-web:check")
}
