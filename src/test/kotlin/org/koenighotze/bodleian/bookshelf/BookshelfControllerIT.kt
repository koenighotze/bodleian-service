package org.koenighotze.bodleian.bookshelf

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.IntegrationTest
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import org.koenighotze.bodleian.bookshelf.entity.BookshelfItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.OK
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.util.UUID.randomUUID

@IntegrationTest
class BookshelfControllerIT(
    @Autowired var testTemplate: TestRestTemplate,
    @Autowired var bookshelfRepository: BookshelfRepository,
) {
    companion object {
        @Container
        @ServiceConnection
        @Suppress("unused")
        // TODO extract to support Rule or similar
        val postgreSQLContainer = PostgreSQLContainer("postgres:16.0-alpine3.18")
    }

    val knownBookshelf = Bookshelf.forOwner(randomUUID().toString())

    @BeforeEach
    fun setupBookshelf() {
        bookshelfRepository.deleteAll()
        bookshelfRepository.saveAndFlush(knownBookshelf)
    }

    @Test
    fun `when adding a book to the bookshelf, and the book is not in the bookshelf yet, should return OK`() {
        val bookId = randomUUID().toString()

        val response =
            testTemplate.postForEntity("/bookshelf/${knownBookshelf.id}/books/$bookId", null, Void::class.java)

        assertThat(response.statusCode).isEqualTo(OK)
        // TODO check body
        // TODO check book was inserted
    }

    @Test
    fun `when adding a book to the bookshelf, and the book is in the bookshelf, should return CONFLICT`() {
        val bookId = randomUUID().toString()
        knownBookshelf.addBookshelfItem(BookshelfItem.of(referenceId = bookId))
        bookshelfRepository.save(knownBookshelf)

        val response =
            testTemplate.postForEntity("/bookshelf/${knownBookshelf.id}/books/$bookId", null, Void::class.java)

        assertThat(response.statusCode).isEqualTo(CONFLICT)
    }

    @Test
    fun `and the bookshelf is found, should return OK`() {
        val response = testTemplate.getForEntity("/bookshelf/owner/${knownBookshelf.owner}", Bookshelf::class.java)

        assertThat(response.statusCode).isEqualTo(OK)
        with(response.body!!) {
            assertThat(id).isNotBlank()
            assertThat(bookshelfItems).isEmpty()
        }
    }
}