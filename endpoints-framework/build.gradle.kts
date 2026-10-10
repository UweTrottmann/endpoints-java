/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("endpoints-java.java-conventions")
    id("endpoints-java.publish-conventions")
}

val guavaVersion: String by project
val jacksonVersion: String by project
val appengineVersion: String by project
val floggerVersion: String by project
val httpclientVersion: String by project
val apiclientVersion: String by project
val findbugsVersion: String by project
val fileUploadVersion: String by project
val swaggerVersion: String by project
val slf4jVersion: String by project
val servletVersion: String by project
val junitVersion: String by project
val javaxinjectVersion: String by project
val mockitoVersion: String by project
val truthVersion: String by project
val springtestVersion: String by project

// Set project-specific publishing properties
publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            artifactId = "endpoints-framework"
            from(components["java"])
            pom {
                name.set("Endpoints Framework")
                description.set("A framework for building RESTful web APIs.")
            }
        }
    }
}

val include by configurations.creating
configurations["compile"].extendsFrom(include)

tasks.jar {
    from({
        include.map { if (it.isDirectory) it else zipTree(it) }
    })
}

val annotations = listOf(
    "AnnotationBoolean.java",
    "Api.java",
    "ApiAuth.java",
    "ApiCacheControl.java",
    "ApiClass.java",
    "ApiFrontendLimitRule.java",
    "ApiFrontendLimits.java",
    "ApiIssuer.java",
    "ApiIssuerAudience.java",
    "ApiMethod.java",
    "ApiMethodCacheControl.java",
    "ApiNamespace.java",
    "ApiReference.java",
    "ApiResourceProperty.java",
    "ApiTransformer.java",
    "Authenticator.java",
    "DefaultValue.java",
    "PeerAuthenticator.java",
    "AuthLevel.java",
    "Transformer.java"
)

val apiTypes = listOf(
    "DataAndTime.java",
    "SimpleData.java"
)

// Generates a Javadoc for types meant to be used by API producers
tasks.register<Javadoc>("siteJavadoc") {
    setSource(annotations.map { "src/main/java/com/google/api/server/spi/config/$it" } +
        apiTypes.map { "src/main/java/com/google/api/server/spi/types" })
    setDestinationDir(file("$buildDir/docs/site-docs"))
}

val copyTestResources by tasks.registering(Copy::class) {
    from("$projectDir/src/test/resources")
    into("$buildDir/classes/test")
}
tasks.processTestResources {
    dependsOn(copyTestResources)
}

dependencies {
    api("com.google.apis:google-api-services-discovery:v1-rev20200806-2.0.0")
    api("com.google.guava:guava:$guavaVersion")
    api("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.google.appengine:appengine-api-1.0-sdk:$appengineVersion")
    implementation("com.google.flogger:flogger:$floggerVersion")
    runtimeOnly("com.google.flogger:flogger-system-backend:$floggerVersion")
    api("com.google.http-client:google-http-client:$httpclientVersion")
    implementation("com.google.api-client:google-api-client:$apiclientVersion")
    implementation("com.google.api-client:google-api-client-appengine:$apiclientVersion")
    implementation("com.google.code.findbugs:jsr305:$findbugsVersion")
    implementation("commons-fileupload:commons-fileupload:$fileUploadVersion")
    implementation("io.swagger:swagger-models:$swaggerVersion")
    implementation("org.slf4j:slf4j-nop:$slf4jVersion")

    compileOnly("javax.servlet:servlet-api:$servletVersion")
    compileOnly("com.google.auto.value:auto-value-annotations:1.11.0")
    annotationProcessor("com.google.auto.value:auto-value:1.11.0")

    testImplementation(project(":test-utils"))
    testImplementation("junit:junit:$junitVersion")
    testImplementation("javax.inject:javax.inject:$javaxinjectVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("com.google.truth:truth:$truthVersion")
    testImplementation("com.google.appengine:appengine-testing:$appengineVersion")
    testImplementation("com.google.appengine:appengine-api-stubs:$appengineVersion")
    testImplementation("org.springframework:spring-test:$springtestVersion")
    testImplementation("com.google.guava:guava-testlib:$guavaVersion")
    testImplementation("io.swagger:swagger-core:$swaggerVersion")
}
