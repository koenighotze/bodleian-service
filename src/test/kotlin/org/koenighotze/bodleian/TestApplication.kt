package org.koenighotze.bodleian

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false)
class TestApplication {

//    @Bean
//    @ServiceConnection
//    fun postgresContainer(): PostgreSQLContainer<*> {
//        return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
//    }
}

fun main(args: Array<String>) {
    fromApplication<Application>().with(TestApplication::class).run(*args)
}
