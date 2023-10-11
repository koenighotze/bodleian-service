package org.koenighotze.bodleian.local

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
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
    fun postgresContainer(): PostgreSQLContainer<out PostgreSQLContainer<*>> {
        val containerPort = 5432
        val localPort = 5432

        val container = PostgreSQLContainer("postgres:16.0-alpine3.18")
            .withDatabaseName("bodleian")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(true)
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier {
                it.withHostConfig(
                    HostConfig.newHostConfig()
                        .withPortBindings(PortBinding(Ports.Binding.bindPort(localPort), ExposedPort(containerPort)))
                )
            }
        container.start()
        return container
    }
}

fun main(args: Array<String>) {
    fromApplication<Application>().with(LocalApplicationConfiguration::class.java).run(*args)
}

