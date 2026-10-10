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
}

val appengineVersion: String by project
val javaxinjectVersion: String by project
val junitVersion: String by project
val mockitoVersion: String by project
val truthVersion: String by project
val servletVersion: String by project

dependencies {
    implementation(project(":endpoints-framework"))
    implementation("com.google.appengine:appengine-api-1.0-sdk:$appengineVersion")
    implementation("javax.inject:javax.inject:$javaxinjectVersion")
    implementation("junit:junit:$junitVersion")
    implementation("org.mockito:mockito-core:$mockitoVersion")
    implementation("com.google.truth:truth:$truthVersion")

    compileOnly("javax.servlet:servlet-api:$servletVersion")
}
