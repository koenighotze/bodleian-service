package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, String> {
    fun findBookByIsbn(isbn: ISBN): Optional<Book>
}