package org.koenighotze.bodleian

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest(@Autowired var mvc: MockMvc) {
    @Test
    fun root_returns_hello() {
        mvc.get("/")
            .andExpect {
                status { isOk() }
                content { string("Hello world") }
            }
    }
}