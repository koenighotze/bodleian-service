package org.koenighotze.bodleian.bookcatalog

import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koenighotze.bodleian.IntegrationTest
import org.koenighotze.bodleian.bookcatalog.dto.BookDTO
import org.koenighotze.bodleian.bookcatalog.dto.BooksDTO
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
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
        @Suppress("unused")
        val postgreSQLContainer = PostgreSQLContainer("postgres:16.0-alpine3.18")

        @JvmStatic
        @DynamicPropertySource
        @Suppress("unused")
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("bodleian.externalServices.openLibrary.apiBaseUrl") { wireMock.baseUrl() }
        }

        @JvmStatic
        @field:RegisterExtension
        var wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081).usingFilesUnderClasspath("wiremock"))
            .build()
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


    @Nested
    @DisplayName("When adding a new book")
    inner class PostBook {
        @Test
        fun `the book is added to the collection`() {
            val expectedBook = Book(
                isbn = ISBN("0140328726"), // watch out! ISBN10!
                title = "Fantastic Mr. Fox",
                authorsGroup = AuthorsGroup(id = randomUUID().toString(), authors = mutableSetOf())
            )

            val response = testTemplate.postForEntity("/books/9780140328721", null, BookDTO::class.java)

            assertThat(response.statusCode).isEqualTo(CREATED)
            with(response.body!!) {
                assertThat(isbn).isEqualTo(expectedBook.isbn!!.code)
                assertThat(title).isEqualTo(expectedBook.title)
                assertThat(authorsGroup.authors).hasSize(1)
                assertThat(authorsGroup.authors.first().firstName).isEqualTo("Roald")
                assertThat(authorsGroup.authors.first().lastName).isEqualTo("Dahl")
            }
        }
    }
}