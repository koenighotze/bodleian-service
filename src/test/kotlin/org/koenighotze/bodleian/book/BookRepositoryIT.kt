package org.koenighotze.bodleian.book

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.IntegrationTest
import org.koenighotze.bodleian.book.DomainTestDataHelper.randomAuthorsGroup
import org.koenighotze.bodleian.book.entity.Book
import org.koenighotze.bodleian.book.entity.Book.Companion.randomId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@IntegrationTest
class BookRepositoryIT(@Autowired var repository: BookRepository) {
    companion object {
        @Container
        @ServiceConnection
        val postgreSQLContainer = PostgreSQLContainer("postgres:16.0-alpine3.18")
    }

    @Test
    fun `The repository returns an empty list of books`() {
        repository.deleteAll()

        assertThat(repository.findAll()).isEmpty()
    }

    fun `a stored book is returned`() {
        repository.save(Book(title = "Some title", authorsGroup = randomAuthorsGroup(), id = randomId()))

        assertThat(repository.findAll()).isNotEmpty
    }
}