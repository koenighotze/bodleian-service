package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BookRepository : CrudRepository<Book, String> {
    fun findBookByIsbn(isbn: ISBN): Optional<Book>
}