package org.koenighotze.bodleian.bookcatalog

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.DomainTestDataHelper.randomBook

@DisplayName("when getting all books")
class BookCatalogManagerGettingAllBooksTest {
    private val expectedBooks = listOf(randomBook())
    private val repo = mockk<BookRepository>()
    private val manager = BookCatalogManager(repo, mockk())

    @Test
    fun `and books are found, should return the books`() {
        every { repo.findAll() } returns expectedBooks

        val books = manager.allBooks()

        assertThat(books).isEqualTo(expectedBooks)
    }

    @Test
    fun `and books are not found, should return an empty list`() {
        every { repo.findAll() } returns expectedBooks

        val books = manager.allBooks()

        assertThat(books).isEqualTo(expectedBooks)
    }

    @Test
    fun `and an error occurs, should throw`() {
        every { repo.findAll() } throws RuntimeException("Bumm")

        assertThrows<RuntimeException> { manager.allBooks() }
    }
}