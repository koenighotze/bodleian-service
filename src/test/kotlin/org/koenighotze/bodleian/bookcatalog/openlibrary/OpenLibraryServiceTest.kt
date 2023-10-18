package org.koenighotze.bodleian.bookcatalog.openlibrary

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.koenighotze.bodleian.bookcatalog.entity.OpenBookApiId
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.*

class OpenLibraryServiceTest {
    private val isbn = UUID.randomUUID().toString()
    private val expectedOpenLibraryBook = OpenLibraryBook(
        title = UUID.randomUUID().toString(),
        isbn10 = arrayListOf(isbn),
        key = "/books/${UUID.randomUUID()}"
    )
    private val expectedBook = Book(
        title = expectedOpenLibraryBook.title!!,
        isbn = expectedOpenLibraryBook.isbn,
        openBookApiId = OpenBookApiId(expectedOpenLibraryBook.key!!)
    )
    private val mockTemplate = mockk<RestTemplate>()
    private val openLibraryService = OpenLibraryService(mockTemplate)

    @Test
    fun `and the book is found, should add the book to the catalog`() {
        val response = ResponseEntity.ok(expectedOpenLibraryBook)

        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryBook::class.java)
        } returns response

        val book = openLibraryService.fetchBookDefinitionFromOpenBook(ISBN(isbn))

        assertThat(book).isNotEmpty
        with(book.get()) {
            assertThat(this.isbn).isEqualTo(expectedOpenLibraryBook.isbn)
            assertThat(title).isEqualTo(expectedOpenLibraryBook.title)
            assertThat(openBookApiId).isEqualTo(
                OpenBookApiId(expectedOpenLibraryBook.key!!)
            )
        }
    }

    @Test
    fun `and the book is not found, should return empty`() {
        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryBook::class.java)
        } returns ResponseEntity.notFound().build()

        val book = openLibraryService.fetchBookDefinitionFromOpenBook(ISBN(isbn))

        assertThat(book).isEmpty
    }

    @Test
    fun `and the the API throws, should throw`() {
        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryBook::class.java)
        } throws RuntimeException("Bumm")

        val exception = assertThrows<RuntimeException> {
            openLibraryService.fetchBookDefinitionFromOpenBook(ISBN(isbn))
        }
        assertThat(exception.message).isEqualTo("Bumm")
    }
}