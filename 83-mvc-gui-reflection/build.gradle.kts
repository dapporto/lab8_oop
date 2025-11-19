plugins {
    application
    java
    id("org.danilopianini.gradle-java-qa") version "1.155.0"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("it.unibo.mvc.LaunchApp")
}

tasks.javadoc {
    isFailOnError = false
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.4")
}