package org.koenighotze.bodleian.book

data class BooksDTO(val books: Collection<BookDTO>) {
    companion object {
        fun from(books: Collection<Book>) = BooksDTO(books = books.map { BookDTO.fromBook(it) })
    }
}
