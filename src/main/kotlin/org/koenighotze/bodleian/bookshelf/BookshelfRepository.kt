package org.koenighotze.bodleian.bookshelf

import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import org.koenighotze.bodleian.bookshelf.entity.BookshelfId
import org.koenighotze.bodleian.user.entity.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookshelfRepository: JpaRepository<Bookshelf, BookshelfId> {
    fun findByOwner(owner: UserId): Optional<Bookshelf>
}