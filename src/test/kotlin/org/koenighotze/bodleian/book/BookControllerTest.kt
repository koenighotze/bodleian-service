package org.koenighotze.bodleian.book

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.OK

class BookControllerTest {
    @Test
    fun `when no books are found, getting all books returns an empty collection`() {
        val bookManager = mockk<BookManager>()
        every { bookManager.allBooks() } returns emptyList()
        val controller = BookController(bookManager)

        val response = controller.getAllBooks()

        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body!!.books).isEmpty()
    }


    @Test
    fun `when books are found, getting all books returns the books`() {
        val expectedBook = Book(title = "bar", authors = "foo", id = Book.randomId())
        val bookManager = mockk<BookManager>()

        every { bookManager.allBooks() } returns listOf(expectedBook)
        val controller = BookController(bookManager)

        val response = controller.getAllBooks()

        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body!!.books).hasSize(1)
        with(response.body!!.books.first()) {
            assertThat(title).isEqualTo(expectedBook.title)
            assertThat(id).isEqualTo(expectedBook.id)
            assertThat(authors).isEqualTo(expectedBook.authors)
        }
    }

    @Test
    fun `when fetching books results in an error, a status code is sent`() {
        val bookManager = mockk<BookManager>()

        every { bookManager.allBooks() } throws RuntimeException("Bumm")
        val controller = BookController(bookManager)

        val response = controller.getAllBooks()

        assertThat(response.statusCode).isEqualTo(INTERNAL_SERVER_ERROR)
    }
}