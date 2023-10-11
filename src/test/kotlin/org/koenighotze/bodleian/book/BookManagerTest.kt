package org.koenighotze.bodleian.book

import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.book.DomainTestDataHelper.randomBook
import org.koenighotze.bodleian.book.entity.Book
import java.util.*

class BookManagerTest {
    @Nested
    @DisplayName("when getting all books")
    inner class GetAllBooks {
        @Test
        fun `and books are found, should return the books`() {
            val expectedBooks = listOf(randomBook())
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)
            every { repo.findAll() } returns expectedBooks

            val books = manager.allBooks()

            assertThat(books).isEqualTo(expectedBooks)
        }

        @Test
        fun `and books are not found, should return an empty list`() {
            val expectedBooks = emptyList<Book>()
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)
            every { repo.findAll() } returns expectedBooks

            val books = manager.allBooks()

            assertThat(books).isEqualTo(expectedBooks)

        }

        @Test
        fun `and an error occurs, should throw`() {
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)
            every { repo.findAll() } throws RuntimeException("Bumm")

            assertThrows<RuntimeException> { manager.allBooks() }
        }
    }

    @Nested
    @DisplayName("when deleting a book")
    inner class DeleteBook {
        @Test
        fun `and the book is found, should delete the book and return true`() {
            val book = randomBook()
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)

            every { repo.delete(any()) } just Runs

            val result = manager.deleteBook(book)

            assertThat(result).isTrue()
            verify(exactly = 1) {
                repo.delete(book)
            }
        }

        @Test
        fun `and an error occurs, should throw`() {
            val book = randomBook()
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)

            every { repo.delete(any()) } throws RuntimeException("bumm")

            assertThrows<RuntimeException> { manager.deleteBook(book) }
        }
    }

    @Nested
    @DisplayName("when updating a book")
    inner class UpdateBook {
        @Test
        fun `and the book is found, should update the book and return true`() {
        }

        @Test
        fun `and book is not found, should return false`() {
        }

        @Test
        fun `and an error occurs, should throw`() {
        }
    }

    @Nested
    @DisplayName("when getting a book by id")
    inner class GetBookById {
        @Test
        fun `and the book is found, should return an optional of the book`() {
            val expectedBook = randomBook()
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)
            every { repo.findById(expectedBook.id!!) } returns Optional.of(expectedBook)

            val book = manager.getBookById(expectedBook.id!!)

            assertThat(book.get()).isEqualTo(expectedBook)
        }

        @Test
        fun `and book is not found, should return an optional empty`() {
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)
            every { repo.findById(any()) } returns Optional.empty()

            val book = manager.getBookById("someId")

            assertThat(book).isEmpty
        }

        @Test
        fun `and an error occurs, should throw`() {
            val repo = mockk<BookRepository>()
            val manager = BookManager(repo)
            every { repo.findById(any()) } throws RuntimeException("Bumms")

            assertThrows<RuntimeException> { manager.getBookById("someId") }
        }
    }

}