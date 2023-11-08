package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.Manager
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.koenighotze.bodleian.bookcatalog.openlibrary.OpenLibraryService
import org.slf4j.LoggerFactory
import java.util.*

@Manager
class BookCatalogManager(
    private val repository: BookRepository,
    private val openLibraryService: OpenLibraryService,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(BookCatalogManager::class.java)
    }

    fun allBooks(): Collection<Book> = repository.findAll().toList()

    fun getBookById(bookId: String): Optional<Book> = repository.findById(bookId)

    fun deleteBook(book: Book): Boolean {
        repository.delete(book)
        return true
    }

    fun addExternalBookToCatalog(isbn: ISBN): Optional<Book> {
        val existingBook: Optional<Book> = repository.findBookByIsbn(isbn)
        if (existingBook.isPresent) {
            logger.debug("Book with isbn {} is already in the collection", isbn)
            return existingBook
        }

        return openLibraryService.fetchBookDefinitionFromOpenBook(isbn)
            .map { repository.save(it) }
    }
}