package org.koenighotze.bodleian.bookcatalog.openlibrary

import com.fasterxml.jackson.annotation.JsonProperty
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.Book.Companion.randomId
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import org.koenighotze.bodleian.bookcatalog.entity.OpenBookApiId

data class OpenLibraryBook(
    var title: String? = null,
    var authors: ArrayList<AuthorKey> = arrayListOf(),
    var covers: ArrayList<Int> = arrayListOf(),
    var key: String? = null,
    // var ocaid: String? = null,
    @JsonProperty("isbn_10")
    var isbn10: ArrayList<String> = arrayListOf(),
    @JsonProperty("isbn_13")
    var isbn13: ArrayList<String> = arrayListOf(),

    ) {

    val isbn: ISBN?
        get() = if (isbn10.isNotEmpty()) {
            ISBN(isbn10.first())
        } else if (isbn13.isNotEmpty()) {
            ISBN(isbn13.first())
        } else
            null

    fun toBook(authorsGroup: AuthorsGroup? = null) = Book(
        id = randomId(),
        isbn = isbn,
        title = title ?: "unknown",
        openBookApiId = key?.let { OpenBookApiId(it) }
    ).withAuthorsGroup(authorsGroup)
}