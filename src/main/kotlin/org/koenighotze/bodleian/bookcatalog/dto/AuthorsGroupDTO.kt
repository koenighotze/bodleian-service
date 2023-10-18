package org.koenighotze.bodleian.bookcatalog.dto

import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup

data class AuthorsGroupDTO(val authors: Set<AuthorDTO>, val id: String? = null) {
    companion object {
        fun fromAuthorsGroup(authorsGroup: AuthorsGroup?) = AuthorsGroupDTO(
            id = authorsGroup?.id,
            authors = authorsGroup?.authors?.map { AuthorDTO.fromAuthor(it) }?.toMutableSet() ?: mutableSetOf()
        )
    }
}
