package org.koenighotze.bodleian.bookcatalog

import logException
import org.koenighotze.bodleian.bookcatalog.dto.BookDTO
import org.koenighotze.bodleian.bookcatalog.dto.BooksDTO
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/books")
class BookCatalogController(private val bookCatalogManager: BookCatalogManager) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(BookCatalogController::class.java)
    }

    @PutMapping("/{isbn}")
    fun addExternalBookToCatalog(@PathVariable isbn: ISBN) {
        TODO()
    }

    @GetMapping("/foo")
    fun getBookByISBNTryout(): ResponseEntity<BookDTO> {
        val book = bookCatalogManager.addExternalBookToCatalog(ISBN("foo"))
        logger.info("$book")
        return notFound().build()
    }

    @GetMapping
    fun getAllBooks() =
        try {
            ok(BooksDTO.from(books = bookCatalogManager.allBooks()))
        } catch (e: RuntimeException) {
            logger.logException("Fetching books failed", e)
            status(INTERNAL_SERVER_ERROR).build()
        }

    @GetMapping("/{bookId}")
    fun getBookById(@PathVariable bookId: String): ResponseEntity<BookDTO> =
        try {
            bookCatalogManager.getBookById(bookId)
                .map {
                    ok(BookDTO.fromBook(book = it))
                }
                .orElse(notFound().build())

        } catch (e: RuntimeException) {
            logger.logException("Fetching book failed", e)
            status(INTERNAL_SERVER_ERROR).build()
        }

    @DeleteMapping("/{bookId}")
    fun deleteBook(@PathVariable bookId: String): ResponseEntity<Unit> =
        try {
            bookCatalogManager.getBookById(bookId)
                .map {
                    bookCatalogManager.deleteBook(it)
                }
                .map {
                    ok().build<Unit>()
                }
                .orElse(notFound().build())
        } catch (e: RuntimeException) {
            logger.logException("Deleting books failed", e)
            status(INTERNAL_SERVER_ERROR).build()
        }
}