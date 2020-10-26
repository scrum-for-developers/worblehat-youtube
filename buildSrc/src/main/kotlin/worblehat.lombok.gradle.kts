plugins {
    `java-library`
}

dependencies {
    compileOnly(platform(project(":worblehat-platform")))
    annotationProcessor(platform(project(":worblehat-platform")))

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}
