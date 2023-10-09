package org.koenighotze.bodleian

import org.junit.jupiter.api.Test
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@IntegrationTest
class ApplicationIT {
    companion object {
        @Container
        @ServiceConnection
        val postgreSQLContainer = PostgreSQLContainer("postgres:16.0-alpine3.18")
    }

    @Test
    fun contextLoads() {
        postgreSQLContainer.start()
    }
}
