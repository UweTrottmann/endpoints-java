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

dependencies {
  implementation project(':endpoints-framework')
  implementation group: 'com.google.appengine', name: 'appengine-api-1.0-sdk', version: appengineVersion
  implementation group: 'javax.inject', name: 'javax.inject', version: javaxinjectVersion
  implementation group: 'junit', name: 'junit', version: junitVersion
  implementation group: 'org.mockito', name: 'mockito-core', version: mockitoVersion
  implementation group: 'com.google.truth', name: 'truth', version: truthVersion

  compileOnly group: 'javax.servlet', name: 'servlet-api', version: servletVersion
}
