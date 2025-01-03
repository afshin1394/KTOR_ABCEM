val exposed_version: String by project
val h2_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val prometheus_version: String by project
val jedis_version: String by project
val kafka_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.2" // Update to the latest stable version
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}
// Apply Shadow plugin manually after exclusions
plugins.withId("io.ktor.plugin") {
    apply(plugin = "com.github.johnrengelman.shadow")
}

group = "ir.irancell"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("redis.clients:jedis:$jedis_version")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheus_version")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-http-redirect-jvm")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("com.ucasoft.ktor:ktor-simple-cache:0.4.4")
    implementation("com.ucasoft.ktor:ktor-simple-redis-cache-jvm:0.4.4")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-content-negotiation-jvm") // Replace with your Ktor version
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml-jvm")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("com.zaxxer:HikariCP:6.2.1")
    testImplementation("io.mockk:mockk:1.13.5")
    implementation("org.apache.kafka:kafka-clients:3.2.0") // Or the latest Kafka version
    implementation ("io.github.cdimascio:java-dotenv:5.2.2")
    implementation("io.ktor:ktor-client-cio-jvm")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}
tasks {
    // Configure the Shadow JAR task
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("abcem-all")
        archiveClassifier.set("")
        archiveVersion.set("")
        mergeServiceFiles()
    }

    // Ensure the build task depends on the Shadow JAR
    build {
        dependsOn(shadowJar)
    }
}

