import org.gradle.api.tasks.testing.logging.TestExceptionFormat

// Configures common Java settings

plugins {
    java
    jacoco
}

repositories {
    mavenCentral()
    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
    }
}
