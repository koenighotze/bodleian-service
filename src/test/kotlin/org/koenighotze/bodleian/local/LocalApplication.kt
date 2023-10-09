package org.koenighotze.bodleian.local

import org.koenighotze.bodleian.Application
import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer


@TestConfiguration(proxyBeanMethods = false)
internal class LocalApplicationConfiguration {
    @Bean
    @ServiceConnection
    fun postgresContainer() =
        PostgreSQLContainer("postgres:16.0-alpine3.18")
}

fun main(args: Array<String>) {
    fromApplication<Application>().with(LocalApplicationConfiguration::class.java).run(*args)
}

