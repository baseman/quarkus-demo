
plugins {
    kotlin("jvm")
    application
    id("io.vertx.vertx-plugin") version "0.8.0"
    id("org.mikeneck.graalvm-native-image") version "0.8.0"
    id("io.quarkus")
}

repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val main by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.graalvm.sdk:graal-sdk:20.2.0")
                implementation("io.quarkus:quarkus-rest-client:1.9.0.Final")
                implementation("io.quarkus:quarkus-resteasy-jsonb:1.9.0.Final")
                implementation("io.quarkus:quarkus-vertx:1.9.0.Final")
                implementation("io.quarkus:quarkus-jdbc-postgresql:1.9.0.Final")
                implementation("io.quarkus:quarkus-reactive-pg-client:1.9.0.Final")
            }
        }
        val test by getting {
            dependencies {
                implementation("io.quarkus:quarkus-junit5:1.9.1.Final")
                implementation("io.rest-assured:rest-assured:4.3.1")

//                implementation(kotlin("test-junit"))
            }
        }
    }
}

application {
    mainClassName = "co.my.app"
}

vertx {
    mainVerticle = application.mainClassName
    vertxVersion = "3.8.0"
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("ctrl-shell")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

nativeImage {
    setExecutableName("ctrl")
    setMainClass(application.mainClassName)
    arguments(
            "--language:js",
            "-H:+TraceClassInitialization",
            "--no-fallback",
            "--enable-all-security-services",
            "--report-unsupported-elements-at-runtime",
            "--initialize-at-build-time=org.jboss.slf4j.JBossLoggerAdapter",
            "--initialize-at-build-time=org.jboss.logging.JDKLogger",
            "--initialize-at-build-time=org.jboss.logging.JDKLevel",
            "--initialize-at-build-time=org.jboss.logging.Logger",
            "--initialize-at-build-time=org.jboss.logging.LoggerProviders",
            "--initialize-at-build-time=org.slf4j.LoggerFactory",
            "--initialize-at-build-time=org.slf4j.impl.StaticLoggerBinder",
            "--initialize-at-build-time=org.jboss.logmanager.ExtHandler"
    )
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}