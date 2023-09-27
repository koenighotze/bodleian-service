package org.koenighotze.bodleian

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.OK

@SpringBootTest(webEnvironment = RANDOM_PORT)
class HelloControllerIT(@Autowired var testTemplate: TestRestTemplate) {
    @Test
    fun root_returns_hello() {
        val response = testTemplate.getForEntity("/", String::class.java)
        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body).isEqualTo("Hello world")
    }
}