import org.gradle.api.tasks.testing.logging.TestExceptionFormat

// Configures common Java settings

plugins {
    `java-library`
    jacoco
}

repositories {
    mavenCentral()
    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
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
        xml.required.set(true)
    }
}
