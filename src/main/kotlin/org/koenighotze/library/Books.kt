package org.koenighotze.library

import org.koenighotze.library.model.Book
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/books")
class Books(val repo: BooksRepository) {
    @GetMapping
    fun allBooks(): List<Book> = repo.findAll()

    @GetMapping("/{id}")
    fun book(@PathVariable id: String): ResponseEntity<Book> =
        repo.findById(id)
            .map { ResponseEntity(it, OK) }
            .orElseGet { ResponseEntity(NOT_FOUND) }

    @DeleteMapping("/{id}")
    @Suppress("SwallowedException")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> =
        try {
            repo.deleteById(id)
            ResponseEntity(OK)
        } catch (e: EmptyResultDataAccessException) {
            ResponseEntity(NOT_FOUND)
        }

    @PostMapping("/{id}")
    fun add(@PathVariable id: String, @RequestBody book: Book) = repo.save(book.copy(id = id))
}
