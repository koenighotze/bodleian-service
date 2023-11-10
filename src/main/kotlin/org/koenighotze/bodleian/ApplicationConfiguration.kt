package org.koenighotze.bodleian

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration.ofSeconds

@Configuration
class ApplicationConfiguration {

    @Value("\${bodleian.externalServices.openLibrary.apiBaseUrl}")
    var openLibraryBookApiBaseUrl: String? = null

    @Bean
    fun resilientOpenLibraryRestTemplate(builder: RestTemplateBuilder): RestTemplate =
        builder
            .rootUri(openLibraryBookApiBaseUrl)
            .setConnectTimeout(ofSeconds(10))
            .setReadTimeout(ofSeconds(10))
            .build()
}