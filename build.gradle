plugins {
    // https://github.com/ben-manes/gradle-versions-plugin/releases
    id("com.github.ben-manes.versions") version "0.46.0"
    // https://github.com/gradle-nexus/publish-plugin/releases
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

// Plugin to publish to Central https://github.com/gradle-nexus/publish-plugin/
// This plugin ensures a separate, named staging repo is created for each build when publishing.
nexusPublishing {
    packageGroup.set("com.uwetrottmann")
    repositories {
        sonatype {
            if (project.hasProperty("SONATYPE_NEXUS_USERNAME") && project.hasProperty("SONATYPE_NEXUS_PASSWORD")) {
                println("nexusPublishing credentials supplied.")
                username.set(project.property("SONATYPE_NEXUS_USERNAME").toString())
                password.set(project.property("SONATYPE_NEXUS_PASSWORD").toString())
            } else {
                println("nexusPublishing credentials NOT supplied.")
            }
        }
    }
}
