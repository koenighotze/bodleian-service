package org.koenighotze.bodleian

import org.junit.jupiter.api.Tag
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.junit.jupiter.Testcontainers
import java.lang.annotation.Inherited
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@Retention(RUNTIME)
@Inherited
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = ["classpath:application-integration-test.yml"])
@Testcontainers
@Tag("slow")
@ActiveProfiles("integration-test")
annotation class IntegrationTest
