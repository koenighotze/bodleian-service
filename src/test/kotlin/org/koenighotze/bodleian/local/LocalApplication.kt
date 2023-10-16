package org.koenighotze.bodleian.local

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.koenighotze.bodleian.Application
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.testcontainers.containers.PostgreSQLContainer


@TestConfiguration(proxyBeanMethods = false)
@PropertySource("classpath:/application-local.yml")
internal class LocalApplicationConfiguration {
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<out PostgreSQLContainer<*>> {
        val containerPort = 5432
        val localPort = 5432

//      TODO: try using wolfi
//        val container = PostgreSQLContainer(
//            DockerImageName.parse("cgr.dev/chainguard/postgres:latest-dev").asCompatibleSubstituteFor("postgres")
//        )
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
    SpringApplicationBuilder()
        .profiles("local")
        .sources(Application::class.java)
        .sources(LocalApplicationConfiguration::class.java)
        .run()
}

