package org.koenighotze.bodleian.bookcatalog.entity

import jakarta.persistence.*
import java.util.UUID.randomUUID

typealias AuthorId = String

@Entity
@Table(name = "AUTHORS")
class Author(
    @ManyToMany(mappedBy = "authors")
    var authorsGroup: MutableSet<AuthorsGroup> = mutableSetOf(),
    @Column(nullable = true, length = 100)
    var firstName: String?,
    @Column(nullable = true, length = 100)
    var lastName: String?,
    @Column(nullable = false, length = 36)
    @Id var id: AuthorId? = null,
) {
    companion object {
        fun randomId() = randomUUID().toString()
    }

    override fun toString(): String {
        return "Author(firstName='$firstName', lastName='$lastName', id=$id)"
    }


}
