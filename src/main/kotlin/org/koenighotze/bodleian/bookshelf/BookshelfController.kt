package org.koenighotze.bodleian.bookshelf

import logException
import org.koenighotze.bodleian.bookcatalog.entity.BookId
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import org.koenighotze.bodleian.bookshelf.entity.BookshelfId
import org.koenighotze.bodleian.user.entity.UserId
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookshelf")
class BookshelfController(private val bookshelfManager: BookshelfManager) {
    companion object {
        val logger = getLogger(BookshelfController::class.java)
    }

    @PostMapping("/{bookshelfId}/books/{bookId}")
    fun addBookToBookshelf(@PathVariable bookshelfId: BookshelfId, @PathVariable bookId: BookId): ResponseEntity<Unit> =
        try {
            bookshelfManager.addBookToCollection(bookshelfId, bookId)
            ok().build()
        } catch (e: IllegalArgumentException) {
            logger.logException("Book $bookId is already in bookshelf $bookshelfId", e)
            status(CONFLICT).build()
        } catch (e: RuntimeException) {
            logger.logException("Adding book $bookId to the bookshelf $bookshelfId failed", e)
            status(INTERNAL_SERVER_ERROR).build()
        }

    @DeleteMapping("/{bookshelfId}/books/{bookId}")
    fun removeBookFromBookshelf(@PathVariable bookshelfId: BookshelfId, @PathVariable bookId: BookId): ResponseEntity<Unit> =
        try {
            bookshelfManager.removeBookFromCollection(bookshelfId, bookId) // todo 404 if bookshelf not found
            ok().build()
        } catch (e: RuntimeException) {
            logger.logException("Removing book $bookId from the bookshelf $bookshelfId failed", e)
            status(INTERNAL_SERVER_ERROR).build()
        }

    @GetMapping("/owner/{ownerId}")
    fun getBookshelfForOwner(@PathVariable ownerId: UserId): ResponseEntity<Bookshelf> =
        try {
            bookshelfManager.getBookshelfForOwner(ownerId)
                .map {
                    println(it)
                    ok(it)
                }
                .orElse(notFound().build())
        } catch (e: RuntimeException) {
            logger.logException("Get bookshelf for owner $ownerId failed", e)
            status(INTERNAL_SERVER_ERROR).build()
        }
}