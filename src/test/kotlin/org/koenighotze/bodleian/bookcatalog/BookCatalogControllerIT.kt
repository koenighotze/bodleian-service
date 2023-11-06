package org.koenighotze.bodleian.bookcatalog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.IntegrationTest
import org.koenighotze.bodleian.bookcatalog.dto.BookDTO
import org.koenighotze.bodleian.bookcatalog.dto.BooksDTO
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpStatus.OK
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.util.UUID.randomUUID

@IntegrationTest
class BookCatalogControllerIT(
    @Autowired var testTemplate: TestRestTemplate,
    @Autowired var bookRepository: BookRepository,
) {
    companion object {
        @Container
        @ServiceConnection
        val postgreSQLContainer = PostgreSQLContainer("postgres:16.0-alpine3.18")
    }

    val knownBooks = listOf(
        Book(
            isbn = ISBN(randomUUID().toString()),
            title = "Random Title ${randomUUID()}",
            authorsGroup = AuthorsGroup(id = randomUUID().toString(), authors = mutableSetOf()),
            id = Book.randomId()
        ),
        Book(
            isbn = ISBN(randomUUID().toString()),
            title = "Random Title ${randomUUID()}",
            authorsGroup = AuthorsGroup(id = randomUUID().toString(), authors = mutableSetOf()),
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

    @Nested
    @DisplayName("When getting a single book")
    inner class GetBook {
        @Test
        fun `the book is returned`() {
            val response = testTemplate.getForEntity("/books/${knownBooks[0].id}", BookDTO::class.java)
            assertThat(response.statusCode).isEqualTo(OK)
            assertThat(response.body!!).isEqualTo(BookDTO.fromBook(knownBooks[0]))
        }
    }
}