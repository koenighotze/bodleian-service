package org.koenighotze.bodleian.bookcatalog.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.FetchType.EAGER
import org.koenighotze.bodleian.bookcatalog.entity.jpa.ISBNStringConverter
import org.koenighotze.bodleian.bookcatalog.entity.jpa.OpenBookApiIdStringConverter
import java.util.UUID.randomUUID

typealias BookId = String

@Entity
@Table(name = "BOOKS")
class Book(
    @Column(nullable = false, length = 100)
    var title: String,
    @JoinColumn
    @ManyToOne(
        cascade = [MERGE, PERSIST],
        fetch = EAGER,
        optional = false
    ) var authorsGroup: AuthorsGroup? = null,
    @Basic
    @Convert(converter = ISBNStringConverter::class)
    @Column(nullable = true, length = 36)
    var isbn: ISBN? = null,
    @Column(nullable = true, length = 36)
    @Basic
    @Convert(converter = OpenBookApiIdStringConverter::class)
    var openBookApiId: OpenBookApiId? = null,
    @Column(nullable = false, length = 36, updatable = false)
    @Id var id: BookId? = null,
) {
    companion object {
        fun randomId() = randomUUID().toString()
    }

    fun withAuthorsGroup(newAuthorsGroup: AuthorsGroup?): Book {
        newAuthorsGroup?.withBook(this)

        return this
    }

    override fun toString(): String {
        return "Book(title='$title', authorsGroup=$authorsGroup, isbn=$isbn, openBookApiId=$openBookApiId id=$id)"
    }


}

// Cover image
// num pages
// price
// ...