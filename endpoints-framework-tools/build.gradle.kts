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
    application
}

val appengineVersion: String by project
val swaggerVersion: String by project
val junitVersion: String by project
val javaxinjectVersion: String by project
val mockitoVersion: String by project
val truthVersion: String by project
val springtestVersion: String by project
val servletVersion: String by project

// Set project-specific publishing properties
publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            artifactId = "endpoints-framework-tools"
            from(components["java"])
            pom {
                name.set("Endpoints Framework Tools")
                description.set("Command line tools for supporting the Endpoints Framework.")
            }
        }
    }
}

// Needed to copy resources into build directories
val copyTestResources by tasks.registering(Copy::class) {
    from("src/test/resources")
    into(layout.buildDirectory.dir("classes/test"))
}
tasks.processTestResources {
    dependsOn(copyTestResources)
}

application {
    mainClass.set("com.google.api.server.spi.tools.EndpointsTool")
}

dependencies {
    implementation(project(":endpoints-framework"))
    implementation("com.google.appengine:appengine-tools-sdk:$appengineVersion")
    implementation("io.swagger:swagger-core:$swaggerVersion")
    implementation("io.swagger:swagger-models:$swaggerVersion")

    testImplementation(project(":test-utils"))
    testImplementation("junit:junit:$junitVersion")
    testImplementation("javax.inject:javax.inject:$javaxinjectVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("com.google.truth:truth:$truthVersion")
    testImplementation("org.springframework:spring-test:$springtestVersion")
    testImplementation("com.google.appengine:appengine-api-1.0-sdk:$appengineVersion")

    testCompileOnly("javax.servlet:servlet-api:$servletVersion")
}
