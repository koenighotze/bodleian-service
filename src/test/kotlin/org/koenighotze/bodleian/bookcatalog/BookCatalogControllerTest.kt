package org.koenighotze.bodleian.bookcatalog

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.bookcatalog.DomainTestDataHelper.randomBook
import org.koenighotze.bodleian.bookcatalog.dto.BookDTO
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.springframework.http.HttpStatus.*
import java.util.*

class BookCatalogControllerTest {
    @Nested
    @DisplayName("when updating a single book")
    @Ignore
    inner class UpdateASingleBook {
        @Test
        fun `and the book is found, should update the book`() {

        }

        @Test
        fun `and the book is found, should return the book`() {

        }

        @Test
        fun `and the book is found, should return OK`() {

        }

        @Test
        fun `and the book is not found, should return 404`() {

        }

        @Test
        fun `and an error occurs, should return a internal error`() {

        }
    }

    @Nested
    @DisplayName("when adding a single book")
    @Ignore
    inner class AddingASingleBook {
        @Test
        fun `and the iban does not exist, should return the book`() {

        }

        @Test
        fun `and the iban does not exist, should add the book`() {

        }

        @Test
        fun `and the iban does not exist, should return CREATED`() {

        }

        @Test
        fun `and the iban exist, should return CONFLICT`() {

        }

        @Test
        fun `and an error occurs, should return a internal error`() {

        }
    }

    @Nested
    @DisplayName("when deleting a single book")
    @Ignore
    inner class DeletingASingleBook {
        @Test
        fun `and the book is found, should delete the book`() {
            val expectedBook = randomBook()
            val bookCatalogManager = mockk<BookCatalogManager>()
            every { bookCatalogManager.getBookById(expectedBook.id!!) } returns Optional.of(expectedBook)
            every { bookCatalogManager.deleteBook(expectedBook) } returns true
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.deleteBook(expectedBook.id!!)

            assertThat(response.statusCode).isEqualTo(OK)
            verify(exactly = 1) {
                bookCatalogManager.deleteBook(expectedBook)
            }
        }

        @Test
        fun `and the book is not found, should return 404`() {
            val expectedBook = randomBook()
            val bookCatalogManager = mockk<BookCatalogManager>()
            every { bookCatalogManager.getBookById(expectedBook.id!!) } returns Optional.empty()
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.deleteBook(expectedBook.id!!)

            assertThat(response.statusCode).isEqualTo(NOT_FOUND)
        }

        @Test
        fun `and an error occurs, should return a internal error`() {

        }
    }

    @Nested
    @DisplayName("when getting a single book")
    inner class GettingASingleBook {
        @Test
        fun `and the book is found, should return the book`() {
            val book = Book(title = "foo", isbn = ISBN("12345"), id = Book.randomId())
            val bookCatalogManager = mockk<BookCatalogManager>()
            every { bookCatalogManager.getBookById(book.id!!) } returns Optional.of(book)
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.getBookById(book.id!!)

            assertThat(response.statusCode).isEqualTo(OK)
            assertThat(response.body!!).isEqualTo(BookDTO.fromBook(book))
        }

        @Test
        fun `and the book is not found, should return 404`() {
            val bookCatalogManager = mockk<BookCatalogManager>()
            every { bookCatalogManager.getBookById(any()) } returns Optional.empty()
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.getBookById("someid")

            assertThat(response.statusCode).isEqualTo(NOT_FOUND)
        }

        @Test
        fun `and an error occurs, should return a internal error`() {
            val bookCatalogManager = mockk<BookCatalogManager>()
            every { bookCatalogManager.getBookById(any()) } throws RuntimeException("Bumm")
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.getBookById("someid")

            assertThat(response.statusCode).isEqualTo(INTERNAL_SERVER_ERROR)
        }

    }

    @Nested
    @DisplayName("when getting books")
    inner class GettingBooks {
        @Test
        fun `and no books are found, should return an empty collection`() {
            val bookCatalogManager = mockk<BookCatalogManager>()
            every { bookCatalogManager.allBooks() } returns emptyList()
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.getAllBooks()

            assertThat(response.statusCode).isEqualTo(OK)
            assertThat(response.body!!.books).isEmpty()
        }


        @Test
        fun `and books are found, should return the books`() {
            val expectedBook = Book(
                title = "bar",
                authorsGroup = AuthorsGroup(id = AuthorsGroup.randomId(), authors = mutableSetOf()),
                id = Book.randomId()
            )
            val bookCatalogManager = mockk<BookCatalogManager>()

            every { bookCatalogManager.allBooks() } returns listOf(expectedBook)
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.getAllBooks()

            assertThat(response.statusCode).isEqualTo(OK)
            assertThat(response.body!!.books).hasSize(1)
            with(response.body!!.books.first()) {
                assertThat(title).isEqualTo(expectedBook.title)
                assertThat(id).isEqualTo(expectedBook.id)
                assertThat(authorsGroup.id).isEqualTo(expectedBook.authorsGroup.id)
            }
        }

        @Test
        fun `and an error occurs, should return an internal error`() {
            val bookCatalogManager = mockk<BookCatalogManager>()

            every { bookCatalogManager.allBooks() } throws RuntimeException("Bumm")
            val controller = BookCatalogController(bookCatalogManager)

            val response = controller.getAllBooks()

            assertThat(response.statusCode).isEqualTo(INTERNAL_SERVER_ERROR)
        }
    }


}