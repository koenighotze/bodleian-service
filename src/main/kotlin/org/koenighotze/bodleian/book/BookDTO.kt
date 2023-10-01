package org.koenighotze.bodleian.book

import jakarta.persistence.Id

data class BookDTO(val title: String, val authors: String, val isbn: String? = null, @Id val id: String? = null) {
    companion object {
        fun fromBook(book: Book) = BookDTO(title = book.title, authors = book.authors, isbn = book.isbn, id = book.id)

        fun toBook() = Book(title = "", authors = "", isbn = "")
    }
}
