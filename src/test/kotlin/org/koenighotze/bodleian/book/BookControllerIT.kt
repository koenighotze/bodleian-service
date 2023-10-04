package org.koenighotze.bodleian.book

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.IntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.OK
import java.util.UUID.randomUUID

@IntegrationTest
class BookControllerIT(@Autowired var testTemplate: TestRestTemplate, @Autowired var bookRepository: BookRepository) {
    val knownBooks = listOf(
        Book(
            isbn = randomUUID().toString(),
            title = "Random Title ${randomUUID()}",
            authors = "Random authors ${randomUUID()}",
            id = Book.randomId()
        ),
        Book(
            isbn = randomUUID().toString(),
            title = "Random Title ${randomUUID()}",
            authors = "Random authors ${randomUUID()}",
            id = Book.randomId()
        )
    )

    @BeforeEach
    fun setupBooks() {
        bookRepository.deleteAll()

        bookRepository.saveAll(knownBooks)
    }

    @Nested
    @DisplayName("When getting all books")
    inner class GettingAllBooks {
        @Test
        fun `the books are returned`() {
            val response = testTemplate.getForEntity("/books", BooksDTO::class.java)
            assertThat(response.statusCode).isEqualTo(OK)
            assertThat(response.body!!.books).hasSize(knownBooks.size)
        }
    }
}