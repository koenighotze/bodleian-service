package org.koenighotze.bodleian.book.dto

import jakarta.persistence.Id
import org.koenighotze.bodleian.book.entity.Book

data class BookDTO(
    val title: String,
    val authorsGroup: AuthorsGroupDTO,
    val isbn: String? = null,
    @Id val id: String? = null
) {
    companion object {
        fun fromBook(book: Book) =
            BookDTO(
                title = book.title,
                authorsGroup = AuthorsGroupDTO.fromAuthorsGroup(book.authorsGroup),
                isbn = book.isbn,
                id = book.id
            )
    }
}
