package org.koenighotze.bodleian.bookcatalog

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.koenighotze.bodleian.bookcatalog.entity.OpenBookApiId
import org.koenighotze.bodleian.bookcatalog.openlibrary.OpenLibraryBook
import org.koenighotze.bodleian.bookcatalog.openlibrary.OpenLibraryService
import java.util.Optional.empty
import java.util.Optional.of
import java.util.UUID.randomUUID

@DisplayName("when adding a book to the catalog")
class BookCatalogManagerAddingBookToLibraryTest {
    private val isbn = randomUUID().toString()
    private val expectedOpenLibraryBook = OpenLibraryBook(
        title = randomUUID().toString(),
        isbn10 = arrayListOf(isbn),
        key = "/books/${randomUUID()}"
    )
    private val expectedBook = Book(
        title = expectedOpenLibraryBook.title!!,
        isbn = expectedOpenLibraryBook.isbn,
        openBookApiId = OpenBookApiId(expectedOpenLibraryBook.key!!)
    )
    private lateinit var mockRepo: BookRepository
    private lateinit var mockOpenLibraryService: OpenLibraryService
    private lateinit var manager: BookCatalogManager

    @BeforeEach
    fun setupMocks() {
        mockRepo = mockk<BookRepository>()
        mockOpenLibraryService = mockk<OpenLibraryService>()
        manager = BookCatalogManager(mockRepo, mockOpenLibraryService)
    }

    @Test
    fun `and the book is found, should add the book to the catalog`() {

        every {
            mockRepo.findBookByIsbn(any())
        } returns empty()
        every {
            mockRepo.save(any<Book>())
        } returns expectedBook
        every {
            mockOpenLibraryService.fetchBookDefinitionFromOpenBook(expectedBook.isbn!!)
        } returns of(expectedBook)

        val book = manager.addExternalBookToCatalog(ISBN(isbn))

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
            mockRepo.findBookByIsbn(any())
        } returns empty()

        every {
            mockOpenLibraryService.fetchBookDefinitionFromOpenBook(any())
        } returns empty()

        val book = manager.addExternalBookToCatalog(ISBN(isbn))

        assertThat(book).isEmpty
    }

    @Test
    fun `and the the API throws, should throw`() {
        every {
            mockRepo.findBookByIsbn(any())
        } returns empty()
        every {
            mockOpenLibraryService.fetchBookDefinitionFromOpenBook(any())
        } throws RuntimeException("Bumm")

        val exception = assertThrows<RuntimeException> {
            manager.addExternalBookToCatalog(ISBN(isbn))
        }
        assertThat(exception.message).isEqualTo("Bumm")
    }


    @Test
    fun `and the book is already in the catalog, should not add the book again`() {
        every {
            mockRepo.findBookByIsbn(expectedBook.isbn!!)
        } returns of(expectedBook)
        val manager = BookCatalogManager(mockRepo, mockk())

        val book = manager.addExternalBookToCatalog(expectedBook.isbn!!)

        assertThat(book).isEqualTo(of(expectedBook))

        verify(exactly = 0) {
            mockRepo.save(any())
        }
    }
}