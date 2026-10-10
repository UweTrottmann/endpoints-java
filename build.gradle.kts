val propertySonatypeUsername = providers.gradleProperty("SONATYPE_NEXUS_USERNAME")
val propertySonatypePassword = providers.gradleProperty("SONATYPE_NEXUS_PASSWORD")

plugins {
    alias(libs.plugins.versions)
    alias(libs.plugins.nexus.publish)
}

// Plugin to publish to Central https://github.com/gradle-nexus/publish-plugin/
// This plugin ensures a separate, named staging repo is created for each build when publishing.
nexusPublishing {
    packageGroup.set("com.uwetrottmann")
    this.repositories {
        sonatype {
            // Use the Portal OSSRH Staging API as this plugin does not support the new Portal API
            // https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/#configuring-your-plugin
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))

            if (propertySonatypeUsername.isPresent && propertySonatypePassword.isPresent) {
                println("Publishing: Maven Central credentials supplied")
                username.set(propertySonatypeUsername.get())
                password.set(propertySonatypePassword.get())
            } else {
                println("Publishing: Maven Central credentials NOT supplied, see root build script for required project properties")
            }
        }
    }
}
