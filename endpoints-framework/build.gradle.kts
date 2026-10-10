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
    "DateAndTime.java",
    "SimpleDate.java"
)

// Generates a Javadoc for types meant to be used by API producers
tasks.register<Javadoc>("siteJavadoc") {
    setSource(annotations.map { "src/main/java/com/google/api/server/spi/config/$it" } +
        apiTypes.map { "src/main/java/com/google/api/server/spi/types/$it" })
    destinationDirectory.set(java.docsDir.dir("site-docs"))
}

val copyTestResources by tasks.registering(Copy::class) {
    from("src/test/resources")
    into(layout.buildDirectory.dir("classes/test"))
}
tasks.processTestResources {
    dependsOn(copyTestResources)
}

dependencies {
    api(libs.google.api.services.discovery)
    api(libs.guava)
    api(libs.jackson.databind)
    implementation(libs.appengine.api)
    implementation(libs.flogger)
    runtimeOnly(libs.flogger.system.backend)
    api(libs.google.http.client)
    implementation(libs.google.api.client)
    implementation(libs.google.api.client.appengine)
    implementation(libs.jsr305)
    implementation(libs.commons.fileupload)
    implementation(libs.swagger.models)
    implementation(libs.slf4j.nop)

    compileOnly(libs.servlet.api)
    compileOnly(libs.auto.value.annotations)
    annotationProcessor(libs.auto.value)

    testImplementation(project(":test-utils"))
    testImplementation(libs.junit)
    testImplementation(libs.javax.inject)
    testImplementation(libs.mockito.core)
    testImplementation(libs.truth)
    testImplementation(libs.spring.test)
    testImplementation(libs.guava.testlib)
    testImplementation(libs.swagger.core)
}
