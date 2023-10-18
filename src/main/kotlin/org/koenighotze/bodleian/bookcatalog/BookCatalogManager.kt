package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.Manager
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.koenighotze.bodleian.bookcatalog.openlibrary.OpenLibraryBook
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate
import java.util.*

@Manager
class BookCatalogManager(
    private val repository: BookRepository,
    private val resilientOpenLibraryRestTemplate: RestTemplate,
) {
    companion object {
        val logger = LoggerFactory.getLogger(BookCatalogManager::class.java)
    }

    fun allBooks(): Collection<Book> = repository.findAll().toList()
    fun getBookById(bookId: String): Optional<Book> = repository.findById(bookId)
    fun deleteBook(book: Book): Boolean {
        repository.delete(book)
        return true
    }

    fun addExternalBookToCatalog(isbn: ISBN): Book? {
        val x = "9780140328721"

        logger.debug("Calling api on openlib")
        val openLibraryBookResponse = resilientOpenLibraryRestTemplate.getForEntity(
            "/isbn/${x}.json",
            OpenLibraryBook::class.java
        )

        logger.debug("received: $openLibraryBookResponse")

        return null
    }
}