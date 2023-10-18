package org.koenighotze.bodleian.bookcatalog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.IntegrationTest
import org.koenighotze.bodleian.bookcatalog.DomainTestDataHelper.randomAuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.Book.Companion.randomId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@IntegrationTest
class BookRepositoryIT(@Autowired var repository: BookRepository) {
    private val knownBook = Book(title = "Some title", authorsGroup = randomAuthorsGroup(), id = randomId())

    companion object {
        @Container
        @ServiceConnection
        val postgreSQLContainer = PostgreSQLContainer("postgres:16.0-alpine3.18")
    }

    @BeforeEach
    fun setupBooks() {
        repository.save(knownBook)
    }

    @Test
    fun `The repository returns the book, author group and the authors`() {
        val book = repository.findById(knownBook.id!!)

        assertThat(book.get().authorsGroup!!.authors).isNotEmpty()
    }

    @Test
    fun `The repository returns an empty list of books`() {
        repository.deleteAll()

        assertThat(repository.findAll()).isEmpty()
    }

    @Test
    fun `a stored book is returned`() {
        assertThat(repository.findAll()).isNotEmpty
    }
}