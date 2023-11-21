package org.koenighotze.bodleian.bookshelf.entity

import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
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
    @Column(nullable = false, length = 36, updatable = false)
    @Id
    var id: BookshelfId,
    @Column(nullable = false, length = 36, updatable = false)
    var owner: UserId,
    @OneToMany(cascade = [ALL], fetch = EAGER) var bookshelfItems: MutableSet<BookshelfItem> = mutableSetOf()
) {
    companion object {
        fun randomId(): BookshelfId = randomUUID().toString()

        fun forOwner(owner: UserId): Bookshelf = Bookshelf(id = randomId(), owner = owner)
    }

    fun addBookshelfItem(bookshelfItem: BookshelfItem) {
        if (!bookshelfItems.contains(bookshelfItem)) {
            bookshelfItems.add(bookshelfItem)
        }
    }

    fun removeBookshelfItem(bookshelfItem: BookshelfItem): Boolean {
        return bookshelfItems.removeIf { it.referenceId == bookshelfItem.referenceId }
    }

    override fun toString(): String {
        return "Bookshelf(id='$id', owner='$owner', bookshelfItems=$bookshelfItems)"
    }


}