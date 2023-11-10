package org.koenighotze.bodleian.bookcatalog.openlibrary

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.koenighotze.bodleian.bookcatalog.entity.OpenBookApiId
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.client.RestTemplate
import java.util.UUID.randomUUID

class OpenLibraryServiceTest {
    private val isbn = randomUUID().toString()
    private val expectedOpenLibraryBook = OpenLibraryBook(
        title = randomUUID().toString(),
        isbn10 = arrayListOf(isbn),
        key = "/books/${randomUUID()}",
        authors = arrayListOf(AuthorKey(key = "/authors/${randomUUID()}"))
    )
    private val expectedOpenLibraryAuthor = OpenLibraryAuthor(name = "Bratislav Metulski")
    private val mockTemplate = mockk<RestTemplate>()
    private val openLibraryService = OpenLibraryService(mockTemplate)

    @Test
    fun `and the book is found, should add the book to the catalog`() {
        val expectedOpenLibraryBookResponse = ok(expectedOpenLibraryBook)
        val expectedOpenLibraryAuthorResponse = ok(expectedOpenLibraryAuthor)

        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryBook::class.java)
        } returns expectedOpenLibraryBookResponse
        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryAuthor::class.java)
        } returns expectedOpenLibraryAuthorResponse

        val book = openLibraryService.fetchBookDefinitionFromOpenBook(ISBN(isbn))

        assertThat(book).isNotEmpty
        with(book.get()) {
            assertThat(this.isbn).isEqualTo(expectedOpenLibraryBook.isbn)
            assertThat(title).isEqualTo(expectedOpenLibraryBook.title)
            assertThat(openBookApiId).isEqualTo(
                OpenBookApiId(expectedOpenLibraryBook.key!!)
            )
            assertThat(this.authorsGroup.authors.first().firstName).isEqualTo("Bratislav")
            assertThat(this.authorsGroup.authors.first().lastName).isEqualTo("Metulski")
        }
    }

    @Test
    fun `and the book is found but the authors are empty, should use an empty author group`() {
        val expectedOpenLibraryBookResponse = ok(expectedOpenLibraryBook)
        val expectedOpenLibraryAuthorResponse = notFound().build<OpenLibraryAuthor>()

        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryBook::class.java)
        } returns expectedOpenLibraryBookResponse
        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryAuthor::class.java)
        } returns expectedOpenLibraryAuthorResponse

        val book = openLibraryService.fetchBookDefinitionFromOpenBook(ISBN(isbn))

        assertThat(book).isNotEmpty
        with(book.get()) {
            assertThat(this.authorsGroup.authors).isEmpty()
        }
    }

    @Test
    fun `and the book is not found, should return empty`() {
        every {
            mockTemplate.getForEntity(any<String>(), OpenLibraryBook::class.java)
        } returns notFound().build()

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