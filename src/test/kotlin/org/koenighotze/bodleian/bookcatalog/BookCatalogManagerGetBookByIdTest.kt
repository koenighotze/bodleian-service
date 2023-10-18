package org.koenighotze.bodleian.bookcatalog

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.DomainTestDataHelper.randomBook
import java.util.Optional.empty
import java.util.Optional.of

@DisplayName("when getting a book by id")
class GetBookById {
    private val expectedBook = randomBook()
    private val repo = mockk<BookRepository>()
    private val manager = BookCatalogManager(repo, mockk())

    @Test
    fun `and the book is found, should return an optional of the book`() {
        every { repo.findById(expectedBook.id!!) } returns of(expectedBook)

        val book = manager.getBookById(expectedBook.id!!)

        assertThat(book.get()).isEqualTo(expectedBook)
    }

    @Test
    fun `and book is not found, should return an optional empty`() {
        every { repo.findById(any()) } returns empty()

        val book = manager.getBookById("someId")

        assertThat(book).isEmpty
    }

    @Test
    fun `and an error occurs, should throw`() {
        every { repo.findById(any()) } throws RuntimeException("Bumms")

        assertThrows<RuntimeException> { manager.getBookById("someId") }
    }
}

