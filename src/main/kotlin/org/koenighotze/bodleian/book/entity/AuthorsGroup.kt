package org.koenighotze.bodleian.book.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.FetchType.LAZY
import java.util.UUID.randomUUID

@Entity
@Table(name = "AUTHORS_GROUP")
class AuthorsGroup(
    @OneToMany(
        mappedBy = "authorsGroup",
        fetch = LAZY,
        orphanRemoval = false
    )
    var books: MutableSet<Book> = mutableSetOf(),
    @ManyToMany(
        cascade = [MERGE, PERSIST]
    )
    var authors: MutableSet<Author>,
    @Id
    var id: String? = null
) {
    companion object {
        fun randomId() = randomUUID().toString()
    }

    fun join(author: Author): AuthorsGroup {
        author.authorsGroup.add(this)
        authors.add(author)

        return this
    }


    fun withBook(book: Book): AuthorsGroup {
        books.add(book)
        book.authorsGroup = this

        return this
    }

    override fun toString(): String {
        return "AuthorsGroup(authors=$authors, id=$id)"
    }


}