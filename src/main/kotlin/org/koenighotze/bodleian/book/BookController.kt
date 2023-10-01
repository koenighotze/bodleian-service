package org.koenighotze.bodleian.book

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookManager: BookManager) {
    @GetMapping
    // TODO paging etc
    fun getAllBooks(): ResponseEntity<BooksDTO> {
        val books = bookManager.allBooks()

        return ResponseEntity.ok(BooksDTO.from(books = books))
    }
}