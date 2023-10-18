package org.koenighotze.bodleian.bookcatalog.dto

import org.koenighotze.bodleian.bookcatalog.entity.Book

data class BooksDTO(val books: Collection<BookDTO>) {
    companion object {
        fun from(books: Collection<Book>) = BooksDTO(books = books.map { BookDTO.fromBook(it) })
    }
}
