package org.koenighotze.bodleian

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import java.util.*

class ApplicationConfigurationTest {
    @DisplayName("when creating the resilient rest template")
    @Nested
    class BuildingTheRestTemplate {
        @Test
        fun `the configuration should set timeouts`() {
            val mockBuilder = mockk<RestTemplateBuilder>(relaxed = true)
            every {
                mockBuilder.rootUri(any())
            } returns mockBuilder
            every {
                mockBuilder.setConnectTimeout(any())
            } returns mockBuilder

            ApplicationConfiguration().resilientOpenLibraryRestTemplate(mockBuilder)

            verify {
                mockBuilder.setConnectTimeout(any())
                mockBuilder.setReadTimeout(any())
            }
        }

        @Test
        fun `the configuration should set the root url from its properties`() {
            val expectedRootUrl = "https://expected-${UUID.randomUUID()}"
            val mockBuilder = mockk<RestTemplateBuilder>(relaxed = true)

            val config = ApplicationConfiguration()
            config.openLibraryBookApiBaseUrl = expectedRootUrl
            config.resilientOpenLibraryRestTemplate(mockBuilder)

            verify {
                mockBuilder.rootUri(expectedRootUrl)
            }

        }
    }
}