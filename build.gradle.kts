@file:Suppress("UnstableApiUsage")

import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import java.lang.Runtime.getRuntime

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
    kotlin("plugin.allopen") version "1.9.10"
    jacoco

}

group = "org.koenighotze"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = VERSION_17
}

repositories {
    mavenCentral()
}

configurations.all {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
// 	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
// 	implementation("org.springframework.boot:spring-boot-starter-data-rest")
// 	implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")


    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
// 	implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
// 	implementation("org.springframework.modulith:spring-modulith-starter-core")
// 	implementation("org.springframework.modulith:spring-modulith-starter-jpa")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2:2.2.220")
    runtimeOnly("org.yaml:snakeyaml:2.0")
// 	runtimeOnly("io.r2dbc:r2dbc-h2")
    runtimeOnly("org.postgresql:postgresql")
//    runtimeOnly("org.postgresql:r2dbc-postgresql")
//    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
//    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    testImplementation("io.rest-assured:rest-assured:5.3.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.boot:spring-boot-testcontainers")
//    testImplementation("io.projectreactor:reactor-test")
//    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.mockk:mockk:1.13.8")

//    testImplementation("org.testcontainers:r2dbc")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:1.0.1")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

val test by testing.suites.existing(JvmTestSuite::class)
tasks.named("test", Test::class.java) {
    testClassesDirs = files(test.map { it.sources.output.classesDirs })
    classpath = files(test.map { it.sources.runtimeClasspath })
    useJUnitPlatform {
        excludeTags("slow")

    }
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = FULL
    }
    filter {
        includeTestsMatching("*Test.*")
    }
    testLogging.showStandardStreams = true
    // from docu
    maxParallelForks = (getRuntime().availableProcessors() / 2).coerceAtLeast(1)
}

tasks.register("integrationTests", Test::class.java) {
    testClassesDirs = files(test.map { it.sources.output.classesDirs })
    classpath = files(test.map { it.sources.runtimeClasspath })
    group = "verification"
    useJUnitPlatform {
        includeTags("slow")
    }
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = FULL
    }
    filter {
        excludeTestsMatching("*Test.*")
    }
    testLogging.showStandardStreams = true
}

tasks.named("check") {
    dependsOn("integrationTests")
}

jacoco {
//    toolVersion = "0.8.9"
//    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }
    }
}


tasks.named<BootBuildImage>("bootBuildImage") {
    fun propertyOrEmpty(name: String, default: String = "") = (project.properties[name] ?: default) as String

    environment.set(
        mapOf(
            "BP_OCI_DESCRIPTION" to propertyOrEmpty("OCI_DESCRIPTION"),
            "BP_OCI_AUTHORS" to propertyOrEmpty("OCI_AUTHORS", "Koenighotze"),
            "BP_OCI_DOCUMENTATION" to propertyOrEmpty("OCI_DOCUMENTATION"),
            "BP_OCI_LICENSES" to propertyOrEmpty("OCI_LICENSES", "MIT"),
            "BP_OCI_REVISION" to propertyOrEmpty("OCI_REVISION"),
            "BP_OCI_SOURCE" to propertyOrEmpty("OCI_SOURCE"),
            "BP_OCI_URL" to propertyOrEmpty("OCI_URL")
        )
    )
}


allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}
