package org.koenighotze.bodleian.book.dto

import org.koenighotze.bodleian.book.entity.Author

data class AuthorDTO(val firstName: String, val lastName: String, val id: String? = null) {
    companion object {
        fun fromAuthor(author: Author) =
            AuthorDTO(id = author.id, firstName = author.firstName, lastName = author.lastName)
    }
}