package org.koenighotze.bodleian.bookcatalog

import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.DomainTestDataHelper.randomBook

@DisplayName("when deleting a book")
class BookCatalogManagerDeleteBookTest {
    private val book = randomBook()
    private val repo = mockk<BookRepository>()
    private val manager = BookCatalogManager(repo, mockk())

    @Test
    fun `and the book is found, should delete the book and return true`() {

        every { repo.delete(any()) } just Runs

        val result = manager.deleteBook(book)

        assertThat(result).isTrue()
        verify(exactly = 1) {
            repo.delete(book)
        }
    }

    @Test
    fun `and an error occurs, should throw`() {
        every { repo.delete(any()) } throws RuntimeException("bumm")

        assertThrows<RuntimeException> { manager.deleteBook(book) }
    }
}





