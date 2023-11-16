package org.koenighotze.bodleian.bookshelf.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.koenighotze.bodleian.bookcatalog.entity.BookId
import java.util.UUID.randomUUID

typealias BookshelfItemId = String

/**
 * A bookshelf item is a reference to a book in a bookshelf.
 */
@Entity
class BookshelfItem(
    @Id var id: BookshelfItemId?,
    @Column(nullable = false, length = 36)
    var referenceId: BookId,
) {
    companion object {
        fun randomId(): BookshelfItemId = randomUUID().toString()
        fun of(referenceId: BookshelfItemId) = BookshelfItem(id = randomId(), referenceId = referenceId)
    }
}
