package org.koenighotze.bodleian.book

import org.koenighotze.bodleian.book.dto.BookDTO
import org.koenighotze.bodleian.book.dto.BooksDTO
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookManager: BookManager) {
    @GetMapping
    fun getAllBooks() =
        try {
            ok(BooksDTO.from(books = bookManager.allBooks()))
        } catch (e: RuntimeException) {
            status(INTERNAL_SERVER_ERROR).build()
        }

    @GetMapping("/{bookId}")
    fun getBookById(@PathVariable bookId: String): ResponseEntity<BookDTO> =
        try {
            bookManager.getBookById(bookId)
                .map {
                    ok(BookDTO.fromBook(book = it))
                }
                .orElse(notFound().build())

        } catch (e: RuntimeException) {
            status(INTERNAL_SERVER_ERROR).build()
        }
}