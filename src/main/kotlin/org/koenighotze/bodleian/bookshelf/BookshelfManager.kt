package org.koenighotze.bodleian.bookshelf

import org.koenighotze.bodleian.Manager
import org.koenighotze.bodleian.bookcatalog.entity.BookId
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf.Companion.forOwner
import org.koenighotze.bodleian.bookshelf.entity.BookshelfId
import org.koenighotze.bodleian.bookshelf.entity.BookshelfItem
import org.koenighotze.bodleian.user.entity.UserId
import org.springframework.data.repository.findByIdOrNull

@Manager
class BookshelfManager(private val bookshelfRepository: BookshelfRepository) {
    fun addBookToCollection(bookshelfId: BookshelfId, bookId: BookId) {
        val bookshelf = bookshelfRepository.findByIdOrNull(bookshelfId) ?: throw IllegalArgumentException("Bookshelf $bookshelfId not found")

        if (bookshelf.bookshelfItems.any { it.referenceId == bookId }) {
            throw IllegalArgumentException("Book $bookId already exists in Bookshelf $bookshelfId")
        }
        bookshelf.addBookshelfItem(BookshelfItem.of(referenceId = bookId))
    }

    fun removeBookFromCollection(bookshelfId: BookshelfId, bookId: BookId) =
        bookshelfRepository.findByIdOrNull(bookshelfId)
            ?. removeBookshelfItem(BookshelfItem.of(referenceId = bookId))
            ?: throw IllegalArgumentException("Bookshelf $bookshelfId not found")

    fun getBookshelfForOwner(ownerId: UserId): Bookshelf? = bookshelfRepository.findByOwner(ownerId) ?: bookshelfRepository.save(forOwner(ownerId))
}