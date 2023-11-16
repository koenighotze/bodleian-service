package org.koenighotze.bodleian.bookshelf

import org.koenighotze.bodleian.bookcatalog.entity.BookId
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import org.koenighotze.bodleian.bookshelf.entity.BookshelfId
import org.koenighotze.bodleian.bookshelf.entity.BookshelfItem
import org.koenighotze.bodleian.user.entity.UserId
import java.util.*

class BookshelfManager(private val bookshelfRepository: BookshelfRepository) {
    fun addBookToCollection(bookshelfId: BookshelfId, bookId: BookId) {
        val bookshelf = bookshelfRepository.findById(bookshelfId)
            .orElseThrow { IllegalArgumentException("Bookshelf $bookshelfId not found") }

        if (!bookshelf.bookshelfItems.any { it.referenceId == bookId }) {
            bookshelf.addBookshelfItem(BookshelfItem.of(referenceId = bookId))
        } else {
            throw IllegalArgumentException("Book $bookId already exists in Bookshelf $bookshelfId")
        }
    }

    fun removeBookFromCollection(bookshelfId: BookshelfId, bookId: BookId) {
        val bookshelf = bookshelfRepository.findById(bookshelfId)
            .orElseThrow { IllegalArgumentException("Bookshelf $bookshelfId not found") }

        bookshelf.removeBookshelfItem(BookshelfItem.of(referenceId = bookId))
    }

    fun getBookshelfForOwner(ownerId: UserId): Optional<Bookshelf> {
        return bookshelfRepository.findByOwner(ownerId)
            .or {
                Optional.of(bookshelfRepository.save(Bookshelf.forOwner(ownerId)))
            }
    }
}