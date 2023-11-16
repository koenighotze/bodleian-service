package org.koenighotze.bodleian.bookshelf.entity

import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.koenighotze.bodleian.user.entity.UserId
import java.util.UUID.randomUUID

typealias BookshelfId = String

/**
 * A bookshelf is a collection of books owned by a single user.
 */
@Entity
class Bookshelf(
    @Id var id: BookshelfId,
    @Column(nullable = false, length = 36) var owner: UserId,
    @OneToMany(cascade = [ALL]) var bookshelfItems: MutableSet<BookshelfItem> = mutableSetOf()
) {
    companion object {
        fun randomId(): BookshelfId = randomUUID().toString()

        fun forOwner(owner: UserId): Bookshelf = Bookshelf(randomId(), owner)
    }

    fun addBookshelfItem(bookshelfItem: BookshelfItem) {
        if (!bookshelfItems.contains(bookshelfItem)) {
            bookshelfItems.add(bookshelfItem)
        }
    }

    fun removeBookshelfItem(bookshelfItem: BookshelfItem): Boolean {
        return bookshelfItems.removeIf { it.referenceId == bookshelfItem.referenceId }
    }
}