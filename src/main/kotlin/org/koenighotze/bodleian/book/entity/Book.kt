package org.koenighotze.bodleian.book.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.FetchType.EAGER
import java.util.UUID.randomUUID


@Entity
@Table(name = "BOOKS")
class Book(
    var title: String,
    @JoinColumn
    @ManyToOne(
        cascade = [MERGE, PERSIST],
        fetch = EAGER,
        optional = false
    ) var authorsGroup: AuthorsGroup? = null,
    @Convert(converter = ISBNStringConverter::class)
    @Basic
    var isbn: ISBN? = null,
    @Id var id: String? = null
) {
    companion object {

        fun randomId() = randomUUID().toString()
    }

    fun withAuthorsGroup(newAuthorsGroup: AuthorsGroup): Book {
        newAuthorsGroup.withBook(this)

        return this
    }

    override fun toString(): String {
        return "Book(title='$title', authorsGroup=$authorsGroup, isbn=$isbn, id=$id)"
    }


}

// Cover image
// num pages
// price
// ...