package org.koenighotze.bodleian.book

import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookManager: BookManager) {
    @GetMapping
    // TODO paging etc
    fun getAllBooks() =
        try {
            ok(BooksDTO.from(books = bookManager.allBooks()))
        } catch (e: RuntimeException) {
            status(INTERNAL_SERVER_ERROR).build()
        }
}