@file:Suppress("UnstableApiUsage")

import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import java.lang.Runtime.getRuntime

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")


    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.retry:spring-retry")


    implementation(platform("io.arrow-kt:arrow-stack:1.2.3"))
    // no versions on libraries
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("org.yaml:snakeyaml:2.2")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
        exclude(module = "json-path", group = "com.jayway.jsonpath")
    }
    testImplementation(group = "org.wiremock", name = "wiremock-standalone", version = "3.4.2")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.mockk:mockk:1.13.10")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//    testImplementation("org.testcontainers:r2dbc")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:1.1.3")
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
    maxParallelForks = 1
}

tasks.named("check") {
    dependsOn("integrationTests")
}

tasks.jacocoTestCoverageVerification {
    // check this by using ./gradlew --warning-mode all clean test jacocoTestCoverageVerification
    // not sure if I want this in two places. In here and in Codacy. But I guess it's fine.
    violationRules {
        rule {
            limit {
                minimum = BigDecimal("0.8")
            }
        }
    }
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
