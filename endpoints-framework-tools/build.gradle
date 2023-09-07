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
    mavenJava(MavenPublication) {
      artifactId = "endpoints-framework-tools"
      from components.java
      pom {
        name.set("Endpoints Framework Tools")
        description.set("Command line tools for supporting the Endpoints Framework.")
      }
    }
  }
}

// Needed to copy resources into build directories
task copyTestResources(type: Copy) {
  from "${projectDir}/src/test/resources"
  into "${buildDir}/classes/test"
}
processTestResources.dependsOn copyTestResources

apply plugin: 'application'
mainClassName = 'com.google.api.server.spi.tools.EndpointsTool'

dependencies {
  implementation project(':endpoints-framework')
  implementation group: 'com.google.appengine', name: 'appengine-tools-sdk', version: appengineVersion
  implementation group: 'io.swagger', name: 'swagger-core', version: swaggerVersion
  implementation group: 'io.swagger', name: 'swagger-models', version: swaggerVersion

  testImplementation project(':test-utils')
  testImplementation group: 'junit', name: 'junit', version: junitVersion
  testImplementation group: 'javax.inject', name: 'javax.inject', version: javaxinjectVersion
  testImplementation group: 'org.mockito', name: 'mockito-core', version: mockitoVersion
  testImplementation group: 'com.google.truth', name: 'truth', version: truthVersion
  testImplementation group: 'org.springframework', name: 'spring-test', version: springtestVersion
  testImplementation group: 'com.google.appengine', name: 'appengine-api-1.0-sdk', version: appengineVersion

  testCompileOnly group: 'javax.servlet', name: 'servlet-api', version: servletVersion
}
