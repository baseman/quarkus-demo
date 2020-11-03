
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("io.quarkus") version "1.9.0.Final"
    }
}

rootProject.name = "quarkus"

include (
  ":quarkus-api",
  ":quarkus-scenarios"
)