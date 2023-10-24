package org.koenighotze.bodleian.bookcatalog.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.FetchType.LAZY
import java.util.UUID.randomUUID

typealias AuthorsGroupId = String

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
        cascade = [MERGE, PERSIST],
        fetch = EAGER
    )
    var authors: MutableSet<Author> = mutableSetOf(),
    @Id
    var id: AuthorsGroupId? = null,
) {
    companion object {
        fun randomId() = randomUUID().toString()
    }

    fun withAuthors(authors: Set<Author>): AuthorsGroup {
        this.authors.clear()
        authors.forEach { author -> this.withAuthor(author) }

        return this
    }

    fun withAuthor(author: Author): AuthorsGroup {
        author.authorsGroup.add(this)
        authors.add(author)

        return this
    }

    override fun toString(): String {
        return "AuthorsGroup(authors=$authors, id=$id)"
    }


}
